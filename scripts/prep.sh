#!/bin/bash
set -e


mkdir -p tmp
pushd tmp
md5sum ./lwjgl.zip | fgrep 88847a8b89b3edf82c48c8baa33b7e23 || {
  wget https://github.com/LWJGL/lwjgl3/releases/download/3.3.3/lwjgl.zip
}
unzip -o lwjgl.zip
popd


rm -rf vendor
mkdir -p vendor/jars
pushd vendor/jars
find ../../tmp -type f -name '*jar' -exec mv {} . \;
popd
## mv tmp/lwjgl-2.9.3/native   vendor/
