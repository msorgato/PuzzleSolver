#!/bin/bash
make clean
make
cd bin
java -cp . puzzlesolver.PuzzleSolver puzzlesolver/tests/base.txt puzzlesolver/tests/risultato
