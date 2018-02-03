#!/bin/bash

setxkbmap bo

cd /home/pi/git/borne_arcade
./clean.sh
./compilation.sh

java -cp .:/home/pi/git/MG2D Main

