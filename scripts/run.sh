#!/bin/bash
set -e

javac -d classes -cp ./vendor/jars/lwjgl.jar src/org/ahands/game/*java
java -Djava.library.path=./vendor/native/linux -cp ./vendor/jars/lwjgl.jar:classes org.ahands.game.Thrust
