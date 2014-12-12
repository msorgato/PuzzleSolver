JC = javac
JBIN = -d bin
JCP = -cp bin
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JBIN) $(JFLAGS) $*.java

CLASSES = \
	src/puzzlesolver/piece/Piece.java \
	src/puzzlesolver/parser/IParser.java \
	src/puzzlesolver/parser/PuzzleParser.java \
	src/puzzlesolver/sort/ISort.java \
	src/puzzlesolver/sort/SequentialSort.java \
	src/puzzlesolver/tests/CorrectParserTest.java \
	src/puzzlesolver/tests/CorrectSortTest.java \
	src/puzzlesolver/tests/WrongParserTest.java \
	src/puzzlesolver/PuzzleSolver.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) bin/puzzlesolver/*.class
	$(RM) bin/puzzlesolver/parser/*.class
	$(RM) bin/puzzlesolver/piece/*.class
	$(RM) bin/puzzlesolver/sort/*.class
	$(RM) bin/puzzlesolver/tests/*.class

	
	
