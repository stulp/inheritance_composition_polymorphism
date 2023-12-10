SOURCES = $(shell ls *.java)
TARGETS += $(subst .java,.class,${SOURCES})

all: ${TARGETS}

clean:
	rm -f *.class

run:
	java ExampleComposition
	
%.class:%.java
	javac $<