#!/bin/bash

javac -cp .:/home/pi/git/MG2D *.java

cd projet

for i in *
do
    cd $i
    javac -cp .:../..:/home/pi/git/MG2D *.java
    cd ..
done

cd ..
