#!/usr/bin/env bash
DIR=`pwd`
cd target
scp ./cda2d4m/CDA2D4M.jar gcr@hazoo:~/cda2d4m/             
cd  $DIR