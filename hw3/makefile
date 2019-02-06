CWD:=$(abspath $(dir $(lastword $(MAKEFILE_LIST)))/)
all : main

main : CompressionTest

CompressionTest : Compression.java CompressionTest.java
	javac -cp .:$(CWD)/lib/* Compression.java CompressionTest.java

run :
	java -cp .:$(CWD)/lib/* CompressionTest test.jpg 50

clean :
	rm *.class 

