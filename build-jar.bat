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
if exist resources (
    echo Copying resources...
    xcopy /E /I resources build\resources
)

REM Create JAR
echo Creating JAR...
jar cvfm YogiHeisenbear.jar MANIFEST.MF -C build .

echo Done! Run with: java -jar YogiHeisenbear.jar
pause
