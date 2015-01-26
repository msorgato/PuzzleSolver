#!/bin/bash
make clean
make
cd bin
java -cp . puzzlesolver.PuzzleSolver ../$1 ../$2
