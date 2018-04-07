#!/bin/bash

echo "Compilation du menu de la borne d'arcade"
echo "Veuillez patienter"
javac -cp .:/home/pi/git/MG2D *.java

cd projet

for i in *
do
    cd $i
    echo "Compilation du jeu "$i
    echo "Veuillez patienter"
    javac -cp .:../..:/home/pi/git/MG2D *.java
    cd ..
done

cd ..
