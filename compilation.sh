#!/bin/bash

javac *.java

cd projet

for i in *
do
    cd $i
    javac -cp $CLASSPATH:../.. *.java
    cd ..
done

cd ..
