CWD:=$(abspath $(dir $(lastword $(MAKEFILE_LIST)))/../)

all: main

main : HistogramTest

HistogramTest : Histogram.java HistogramTest.java
	javac -cp .:$(CWD)/lib/* Histogram.java HistogramTest.java

run :
	java -cp .:$(CWD)/lib/* HistogramTest valve.png

clean :
	rm *.class 


