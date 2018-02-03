#!/bin/bash

cd /home/pi/git/borne_arcade
echo "nettoyage des répertoires"
echo "Veuillez patienter"
./clean.sh
./compilation.sh

echo "Lancement du  Menu"
echo "Veuillez patienter"

java -cp .:/home/pi/git/MG2D Main

