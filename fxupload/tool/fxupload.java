/* Arduboy FX Arduino java plugin v1.04 by Mr.Blinky Jan 2022 - Jan 2023 */

package arduboy.fxupload;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.io.InputStreamReader;
import processing.app.Editor;
import processing.app.tools.Tool;
import processing.app.PreferencesData;
import processing.app.Editor;
import processing.app.BaseNoGui;
import processing.app.Sketch;
import processing.app.tools.Tool;
import processing.app.helpers.ProcessUtils;

public class fxupload implements Tool
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
    return "Upload existing Arduboy FX data";
  }

  // This function is called when the menu option is clicked
  public void run() 
  {
    System.out.print("Arduboy FX Arduino plugin v1.04 by Mr.Blinky Jan 2022 - Jan 2023\n");
    //get fx data paths
    String fxdataPath = fixSeperator(editor.getSketch().getFolder().getAbsolutePath()) + File.separator + "fxdata" + File.separator;
    final String fxdataScript = fxdataPath + "fxdata.txt";
    final String fxdataFile   = fxdataPath + "fxdata.bin";
    
    // get tool paths
    String sketchbookPath = fixSeperator(BaseNoGui.getDefaultSketchbookFolder().getAbsolutePath());
    final String buildTool = sketchbookPath + "/tools/fxdata-build.py";
    final String uploadTool = sketchbookPath + "/tools/fxdata-upload.py";
    
    //try get installed python3 path from text file
    String python3path = "";
    try {
       python3path = new String (Files.readAllBytes(Paths.get(sketchbookPath + "/tools/python3path.txt")));
    } catch (IOException e) {
      //exception
    }
    
    // get python location
    String python = null;
    String[] pythonPaths =
    { 
      fixSeperator(python3path),
      fixSeperator(sketchbookPath + "/tools/python3/"), //Prefered location for manual or custom install
      fixSeperator(PreferencesData.get("runtime.tools.python3.path")) //location when python is installed through board package
    };
    for (String path : pythonPaths) 
    {
      File file = new File(path, PreferencesData.get("runtime.os").contentEquals("windows") ? "python.exe" : "python3");
      if (file.exists() && file.isFile() && file.canExecute()) 
      {
        python = fixSeperator(file.getAbsolutePath());
        break;
      }
    }
    if (python == null) 
    {   
      editor.statusError("python3 is missing.");
      System.err.print("Make sure python3 is located at " + pythonPaths[1] +"\nor edit the python3 install path in python3path.txt");
      return;
    }
    final String pythonExe = python;

    Thread thread = new Thread() 
    {
      public void run() 
      {
        try 
        {
          //editor.statusNotice("Building FX data");          
          //if( listenOnProcess(new String[]{pythonExe,buildTool,fxdataScript}) != 0) editor.statusError("FX data build failed!");
          //else try
          //{
          editor.statusNotice("Uploading FX data");                
          if( listenOnProcess(new String[]{pythonExe,uploadTool,fxdataFile}) != 0) editor.statusError("FX data upload failed!");
          else editor.statusNotice("FX data Uploaded");      
        }
        catch (Exception e)
        { 
          editor.statusError("FX data upload failed!"); 
        }
        //} 
        //catch (Exception e)
        //{ 
        //  editor.statusError("FX data build failed!"); 
        //}
        
      }
    };
    thread.start();
  }

  private String fixSeperator(String s)
  {
     if (s == null) return null;
     if (System.getProperty("file.separator").equals("/")) return s.replace("\\", "/");       
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