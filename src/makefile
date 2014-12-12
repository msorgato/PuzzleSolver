JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $*.java

CLASSES = \
	PuzzleSolver.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
