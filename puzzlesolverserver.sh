#!/bin/bash
cd bin
rmiregistry
java -cp . puzzlesolver.remote.server.PuzzleSolverServer $1