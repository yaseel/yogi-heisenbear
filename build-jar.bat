@echo off
REM Simple JAR builder for Yogi Heisenbear (Windows)

echo Building Yogi Heisenbear JAR...

REM Clean old builds
if exist build rmdir /s /q build
if exist YogiHeisenbear.jar del YogiHeisenbear.jar

REM Create build directory
mkdir build

REM Compile
echo Compiling...
dir /s /B src\*.java > sources.txt
javac -d build @sources.txt
del sources.txt

REM Copy resources if they exist
if exist src\resources (
    echo Copying resources...
    xcopy /E /I src\resources build\resources
)

REM Copy level files if they exist
if exist src\levels (
    echo Copying levels...
    xcopy /E /I src\levels build\levels
)

REM Create JAR
echo Creating JAR...
jar cvfm YogiHeisenbear.jar MANIFEST.MF -C build .

echo Done! Run with: java -jar YogiHeisenbear.jar
pause
