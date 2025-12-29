#!/bin/bash
# Simple JAR builder for Yogi Heisenbear

echo "🐻 Building Yogi Heisenbear JAR..."

# Clean old builds
rm -rf build
rm -f YogiHeisenbear.jar

# Create build directory
mkdir -p build

# Compile
echo "Compiling..."
find src -name "*.java" > sources.txt
javac -d build @sources.txt
rm sources.txt

# Copy resources if they exist
if [ -d "resources" ]; then
    echo "Copying resources..."
    cp -r resources build/
fi

# Create JAR
echo "Creating JAR..."
jar cvfm YogiHeisenbear.jar MANIFEST.MF -C build .

echo "✅ Done! Run with: java -jar YogiHeisenbear.jar"
