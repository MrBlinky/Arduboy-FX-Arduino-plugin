/* Arduboy FX Arduino java plugin v1.01 by Mr.Blinky Jan 2022 - Feb 2022 */

package arduboy.fxtool;

import java.io.File;
import java.io.InputStreamReader;
import processing.app.Editor;
import processing.app.tools.Tool;
import processing.app.PreferencesData;
import processing.app.Editor;
import processing.app.BaseNoGui;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.app.helpers.ProcessUtils;

public class fxtool implements Tool
{
  Editor editor;

  // This function is called when the Arduino IDE starts up
  public void init(Editor editor) 
  {
    this.editor = editor;
  }

  // This function is called to displayed the title in the tools menu
  public String getMenuTitle() 
  {
    return "Build and upload Arduboy FX data";
  }

  // This function is called when the menu option is clicked
  public void run() 
  {
    //get fx data paths
    String sketchPath = fixSeperator(editor.getSketch().getFolder().getAbsolutePath()) + File.separator;
    final String fxdataScript = sketchPath + "fxdata.txt";
    final String fxdataFile   = sketchPath + "fxdata.bin";
    
    // get tool paths
    String sketchbookPath = fixSeperator(BaseNoGui.getDefaultSketchbookFolder().getAbsolutePath());
    final String buildTool = sketchbookPath + "/tools/fxdata-build.py";
    final String uploadTool = sketchbookPath + "/tools/fxdata-upload.py";
    
    // get python location
    String python = null;
    String[] pythonPaths =
    { 
      fixSeperator(sketchbookPath + "/tools/python3/"), //Prefered location for manual or custom install
      fixSeperator(PreferencesData.get("runtime.tools.python3.path")) //location when python is installed through board package
    };
    for (String path : pythonPaths) 
    {
      File file = new File(path, PreferencesData.get("runtime.os").contentEquals("windows") ? "python.exe" : "python");
      if (file.exists() && file.isFile() && file.canExecute()) 
      {
        python = fixSeperator(file.getAbsolutePath());
        break;
      }
    }
    if (python == null) 
    {   
      editor.statusError("Python3 is missing.");
      System.err.print("Make sure Python3 is located at " + pythonPaths[0]);
      return;
    }
    final String pythonExe = python;

    Thread thread = new Thread() 
    {
      public void run() 
      {
        try 
        {
          editor.statusNotice("Building FX data");          
          if( listenOnProcess(new String[]{pythonExe,buildTool,fxdataScript}) != 0) editor.statusError("FX data build failed!");
          else try
          {
            editor.statusNotice("Uploading FX data");                
            if( listenOnProcess(new String[]{pythonExe,uploadTool,fxdataFile}) != 0) editor.statusError("FX data upload failed!");
            else editor.statusNotice("FX data Uploaded");      
          }
          catch (Exception e)
          { 
            editor.statusError("FX data upload failed!"); 
          }
        } 
        catch (Exception e)
        { 
          editor.statusError("FX data build failed!"); 
        }
        
      }
    };
    thread.start();
  }

  private String fixSeperator(String s)
  {
     if (File.separator == "/") return s.replace("\\", "/");       
     else return s.replace("/", "\\");       
  }
  
  private int listenOnProcess(String[] arguments)
  {
    System.out.print("\nExecuting ");
    for (String s : arguments) System.out.print(s + " ");
    System.out.println();
    try 
    {
      final Process process = ProcessUtils.exec(arguments);
      Thread thread = new Thread() 
      {
        public void run() 
        {
          try 
          {
            InputStreamReader reader = new InputStreamReader(process.getInputStream());
            int c;
            while ((c = reader.read()) != -1) System.out.print((char) c);
            reader.close();
            reader = new InputStreamReader(process.getErrorStream());
            while ((c = reader.read()) != -1) System.err.print((char) c);
            reader.close();
          } 
          catch (Exception e) {}
        }
      };
      thread.start();
      int result = process.waitFor();
      thread.join();
      return result;
    } 
    catch (Exception e)
    {
      return -1;
    }
  }
}
