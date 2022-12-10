# Arduboy FX Arduino plugging

This Arduino plugin adds an option under the Tools menu that allows easy 
building and uploading of Arduboy FX data using the (Legacy) Arduino IDE.

![demo](https://github.com/MrBlinky/Arduboy-FX-Arduino-plugin/raw/main/fx-upload-using-arduino-plugin-demo.gif)

## Install

Go to the Arduino sketchbook location and see if there is a folder called tools.
If this folder does not exist, create a new folder and name it tools.

If you're not sure where you can find the sketchbook location, the Arduino
default location are:

    Windows: C:\Users\{username}\Documents\Arduino
    macOS: /Users/{username}/Documents/Arduino
    Linux: /home/{username}/Arduino

You can also find the location in Preferences, under Sketchbook location:

    Windows/Linux: File > Preferences.
    macOS: Arduino > Preferences

If a portable version of Arduino is used the sketchbook folder is located in 
the portable folder and called sketchbook.

Move all the unzipped files and folders into the created tools folder.
The folder structure should look like this:

    _<sketcbook_location>
     |_tools/
       |_fxtool/          Contains Arduino Java plugin
       |_PIL/             Contains the Pillow Python module
       |_python3/         Contains Python 3.8.8 for windows (32-bit)
       |_serial/          Contains the pyserial module
       |_fxdata-build.py  FX data build Python script
       |_fxdata-upload.py FX data upload Python script
       |_LICENSE          Licence file
       |_readme.md        This file

## Dependencies

The Arduboy FX tool depends on Python 3 and the pyserial and PILlow modules. 
The python3 folder contains a copy of  Python 3.8.8 for Windows. If you use a 
different OS then delete all files in  the Python3 folder, download Python3 for
your OS from one of the link below and extract it contents into the Python3 folder.

Windows 32-bit: https://www.python.org/ftp/python/3.8.8/python-3.8.8-embed-win32.zip

Windows 64-bit: https://www.python.org/ftp/python/3.8.8/python-3.8.8-embed-amd64.zip

OSX: https://www.python.org/ftp/python/3.8.8/python-3.8.8-macosx10.9.pkg

Linux: https://www.python.org/ftp/python/3.8.8/Python-3.8.8.tgz

For other version: https://www.python.org/downloads/

The PIL and serial folders contain the PIllow and pyserial python modules for 
the supplied Windows Python version. If you use a different OS you may need to
replace these modules.

The PILLow module can be found at https://pypi.org/project/Pillow/#files

The pyserial module can be found at https://pypi.org/project/pyserial/#files

## Usage
Start the Arduino IDE and go to the tools menu. If installation went well you 
will see a new tools option called **'Build and upload Arduboy FX data'** If the
option is not available. Check above installation steps again.

To succesfully build the FX data, there must be a folder named fxdata in your 
sketch folder and it must contain a fxdata.txt script file. The tool will create
a fxdata.h file in the fxdata folder and a fxdata.bin file containing the actual
fx data. The fxdata.bin file will also be uploaded to your Arduboy FX. So make 
sure Arduboy is connected.
