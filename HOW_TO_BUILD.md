# 🐻 Yogi Heisenbear - Build & Distribution Guide

## Quick Start: Building the JAR

### On macOS/Linux:
```bash
chmod +x build-jar.sh
./build-jar.sh
```

### On Windows:
Double-click `build-jar.bat` or run:
```cmd
build-jar.bat
```

This creates `YogiHeisenbear.jar` - a runnable JAR file that works on any platform with Java installed.

---

## Running the Game

### Option 1: Direct JAR execution
```bash
java -jar YogiHeisenbear.jar
```

### Option 2: Use the launcher scripts

**macOS/Linux:**
```bash
chmod +x run.sh
./run.sh
```

**Windows:**
Double-click `RUN.bat`

---

## Distributing Your Game

### What to give users:
1. **YogiHeisenbear.jar** - The game file
2. **RUN.bat** (for Windows users) or **run.sh** (for Mac/Linux users)
3. Instructions to install Java if they don't have it

### User Requirements:
- Java 8 or higher must be installed
- Download Java from: https://adoptium.net/ (recommended) or https://java.com

### For a double-click experience on Windows:
Users can double-click `RUN.bat` to launch the game without using the command line.

### For a double-click experience on macOS:
You can create an `.app` bundle (see Advanced Options below).

---

## Troubleshooting

### "javac is not recognized" or "command not found"
- Java Development Kit (JDK) is not installed
- Install JDK from: https://adoptium.net/temurin/releases/

### Game doesn't start
- Make sure Java is installed: `java -version`
- Try running from command line to see error messages
- Check that resources folder is in the same directory as the JAR

### Resources not loading
- Resources must be in the JAR file or in a `resources/` folder next to the JAR
- Run the build script again to ensure resources are included

---

## Advanced Options

### Creating a macOS .app Bundle

1. Create the app structure:
```bash
mkdir -p YogiHeisenbear.app/Contents/MacOS
mkdir -p YogiHeisenbear.app/Contents/Resources/Java
```

2. Copy the JAR:
```bash
cp YogiHeisenbear.jar YogiHeisenbear.app/Contents/Resources/Java/
```

3. Create launcher script:
```bash
cat > YogiHeisenbear.app/Contents/MacOS/YogiHeisenbear << 'EOF'
#!/bin/bash
cd "$(dirname "$0")/../Resources/Java"
java -jar YogiHeisenbear.jar
EOF
chmod +x YogiHeisenbear.app/Contents/MacOS/YogiHeisenbear
```

4. Create Info.plist:
```bash
cat > YogiHeisenbear.app/Contents/Info.plist << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE plist PUBLIC "-//Apple//DTD PLIST 1.0//EN" "http://www.apple.com/DTDs/PropertyList-1.0.dtd">
<plist version="1.0">
<dict>
    <key>CFBundleName</key>
    <string>Yogi Heisenbear</string>
    <key>CFBundleExecutable</key>
    <string>YogiHeisenbear</string>
    <key>CFBundleIdentifier</key>
    <string>com.yourdomain.yogiheisenbear</string>
    <key>CFBundleVersion</key>
    <string>1.0</string>
    <key>CFBundlePackageType</key>
    <string>APPL</string>
</dict>
</plist>
EOF
```

Now you can double-click `YogiHeisenbear.app` to run!

### Creating a Windows .exe (using Launch4j)

1. Download Launch4j: https://launch4j.sourceforge.net/
2. Configure it to wrap your JAR into an .exe
3. This creates a native Windows executable

---

## Adding to .gitignore

Add these to your `.gitignore`:
```
build/
*.jar
*.class
YogiHeisenbear.app/
```

Keep the build scripts in version control, but not the compiled outputs.
