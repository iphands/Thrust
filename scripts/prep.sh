#!/bin/bash
set -e
mkdir -p tmp
cd tmp
wget https://iweb.dl.sourceforge.net/project/java-game-lib/Official%20Releases/LWJGL%202.9.3/lwjgl-2.9.3.zip
unzip lwjgl-2.9.3.zip
cd ..

mkdir -p vendor/jars
mv tmp/lwjgl-2.9.3/jar/*jar vendor/jars/
mv tmp/lwjgl-2.9.3/native   vendor/
