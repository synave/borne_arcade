#!/bin/bash

javac *.java

cd projet

for i in *
do
    cd $i
    javac *.java
    cd ..
done

cd ..
