#!/bin/bash

echo "ScorePack Build - Network Connectivity Check"
echo "============================================="

# Check DNS resolution
echo "1. Testing DNS resolution for maven.fabricmc.net..."
if nslookup maven.fabricmc.net > /dev/null 2>&1; then
    echo "   ✓ DNS resolution successful"
else
    echo "   ✗ DNS resolution failed"
    echo "   This will prevent fabric-loom plugin download"
fi

# Check HTTP connectivity
echo ""
echo "2. Testing HTTP connectivity to Fabric Maven..."
if curl -s --head https://maven.fabricmc.net/ > /dev/null 2>&1; then
    echo "   ✓ HTTP connectivity successful"
else
    echo "   ✗ HTTP connectivity failed"
    echo "   This will prevent downloading Fabric dependencies"
fi

# Check other required repositories
echo ""
echo "3. Testing connectivity to other required repositories..."

echo "   Maven Central:"
if curl -s --head https://repo.maven.apache.org/maven2/ > /dev/null 2>&1; then
    echo "   ✓ Maven Central accessible"
else
    echo "   ✗ Maven Central not accessible"
fi

echo "   Gradle Plugin Portal:"
if curl -s --head https://plugins.gradle.org/ > /dev/null 2>&1; then
    echo "   ✓ Gradle Plugin Portal accessible"
else
    echo "   ✗ Gradle Plugin Portal not accessible"
fi

echo ""
echo "Network check complete."
echo ""
echo "If any checks failed, you may need to:"
echo "- Configure proxy settings"
echo "- Check firewall rules"
echo "- Use a different network environment"
echo "- Set up a local Maven mirror"