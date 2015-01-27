JC = javac
JBIN = -d bin
JCP = -cp bin
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JBIN) $(JCP) $*.java

CLASSES = \
	src/puzzlesolver/piece/Piece.java \
	src/puzzlesolver/parser/IParser.java \
	src/puzzlesolver/parser/PuzzleParser.java \
	src/puzzlesolver/sort/ISort.java \
	src/puzzlesolver/sort/SequentialSort.java \
	src/puzzlesolver/sort/ConcurrentSort.java \
	src/puzzlesolver/remote/RemoteSolver.java \
	src/puzzlesolver/remote/server/ConcreteRemoteSolver.java \
	src/puzzlesolver/remote/server/PuzzleSolverServer.java \
	src/puzzlesolver/remote/client/PuzzleSolverClient.java \
	src/puzzlesolver/tests/CorrectParserTest.java \
	src/puzzlesolver/tests/CorrectSortTest.java \
	src/puzzlesolver/tests/WrongParserTest.java \
	src/puzzlesolver/tests/FirstColumnTest.java \
	src/puzzlesolver/tests/ConcurrentSortTest.java \
	src/puzzlesolver/PuzzleSolver.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) bin/puzzlesolver/*.class
	$(RM) bin/puzzlesolver/parser/*.class
	$(RM) bin/puzzlesolver/piece/*.class
	$(RM) bin/puzzlesolver/remote/*.class
	$(RM) bin/puzzlesolver/remote/client/*.class
	$(RM) bin/puzzlesolver/remote/server/*.class
	$(RM) bin/puzzlesolver/sort/*.class
	$(RM) bin/puzzlesolver/tests/*.class

start:
	rmiregistry &
	
stop:
	killall -q rmiregistry &
	sleep 1
	killall -q xterm &
	
