#!/bin/sh
cd bin
java -classpath ./lib Main.class --adduser $1 $2 $3 $4 $5
