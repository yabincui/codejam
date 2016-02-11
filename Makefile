UnitTestList := BadHorse CaptainHammer Moist GoogolString GCube GCampus GSnake \
                SevenSegmentDisplay Super2048 Addition CutTiles PasswordAttacker \
                NewYearsEve CardGame ParenthesesOrder MinimumScalarProduct CrazyRows \
                BribeThePrisoners Millionaire \

all: $(lastword $(UnitTestList))

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

