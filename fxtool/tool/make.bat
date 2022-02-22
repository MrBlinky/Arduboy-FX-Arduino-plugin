@echo off

rem Download and install Java SE Development Kit 8u251
rem locate jdk\bin directory and copy it's path below
rem Locate Arduino IDE directory and copy it's path below too

set JDK_VERSION=1.8
set JDK_DIR=C:\Program Files\Java\jdk1.8.0_251\bin
set ARDUINO_DIR=C:\arduino-portable\arduino-1.8.19

set CLASS_NAME=fxtool
set PACKAGE_DIR=arduboy\fxtool
set LIB="%ARDUINO_DIR%\lib\arduino-core.jar;%ARDUINO_DIR%\lib\pde.jar"

path=%JDK_DIR%;%PATH%
pushd "%~dp0"
echo compiling...
javac -target %JDK_VERSION% -cp %LIB% %CLASS_NAME%.java
if %ERRORLEVEL% == 0 goto makejar
echo tool creation failed
goto :end

:makejar
echo creating jar file...
mkdir %PACKAGE_DIR%
move  *.class %PACKAGE_DIR%
jar -cf %CLASS_NAME%.jar .\%PACKAGE_DIR%\*.class
for /F "tokens=1 delims=\" %%a in ("%PACKAGE_DIR%") do rd %%a /s /q
echo tool "%CLASS_NAME%.jar" created successfully
:end
popd
pause > nul