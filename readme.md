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
       |_python3path.txt  text file containing the python install path (optional)
       |_readme.md        This file

## Dependencies

The Arduboy FX tool depends on Python 3 and the pyserial and PILlow modules. 
The python3 folder contains a portable version Python 3.8.8 for Windows so
the plugin can be easily added to a Portable Arduino version.

When using a different OS or a different version of python is preferred then the
PIL, python3 and serial folders can be deleted. Python3 can be downloaded and
installed from:

https://www.python.org/downloads/

After installation, 
on Windows:     press Windows key + R enter cmd and enter python on the commandline.
MacOS or linux: go to terminal and type python3
In the python console enter the following two lines to get the python3 install path:

import sys  
sys.executable

copy the python install path to clipboard. Now edit the python3path.txt file, 
paste the path and save the file.

back in the python console enter exit() to exit the python console. Back on the 
commandline / terminal type the following commands to install the Pillow and 
pySerial modules:

Windows: python -m pip install Pillow
         python -m pip install pySerial
         
MacOS/Linus: sudo python3 -m pip install Pillow
             sudo python3 -m pip install pySerial

## Usage

Start the Arduino IDE and go to the tools menu. If installation went well you 
will see three new tools options called:
**'Build Arduboy FX data'**
**'Build and upload Arduboy FX data'**
**'Upload existing Arduboy FX data'**
If these options are not available. Check above installation steps again.

To succesfully build the FX data, there must be a folder named fxdata in your 
sketch folder and it must contain a fxdata.txt script file. The tool will create
a fxdata.h file in the fxdata folder and a fxdata.bin file containing the actual
fx data. The fxdata.bin file will also be uploaded to your Arduboy FX.

When a savesection is defined in the fxdata.txt file there will be two more
files created. A fxdata-data.bin and fxdata-save.bin file. These files should be
used when the program is added to a flash image file.
