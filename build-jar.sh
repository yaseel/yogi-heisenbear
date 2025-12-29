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

# Copy resources from src/resources
if [ -d "src/resources" ]; then
    echo "Copying resources..."
    cp -r src/resources build/
fi

# Copy level files if they exist
if [ -d "src/levels" ]; then
    echo "Copying levels..."
    cp -r src/levels build/
fi

# Create JAR
echo "Creating JAR..."
jar cvfm YogiHeisenbear.jar MANIFEST.MF -C build .

echo "✅ Done! Run with: java -jar YogiHeisenbear.jar"
