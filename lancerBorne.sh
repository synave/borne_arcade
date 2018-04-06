#!/bin/bash

setxkbmap bo

./clean.sh
./compilation.sh

java Main
