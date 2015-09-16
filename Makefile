include gmsl-1.1.7/gmsl

UnitTestList := BadHorse CaptainHammer Moist GoogolString GCube GCampus GSnake \
                SevenSegmentDisplay \

all: $(call last,$(UnitTestList))

test_all: $(UnitTestList)


JUNIT_MAIN = org.junit.runner.JUnitCore

%.class : %.java
	javac $<

%Test: %.class %Test.class
	java $(JUNIT_MAIN) $@

%: %.class
	java $@


clean:
	rm -rf *.class

