CWD:=$(abspath $(dir $(lastword $(MAKEFILE_LIST)))/../)
all : main

main : ImageProcessTest

ImageProcessTest : ImageProcess.java ImageProcessTest.java
	javac -cp .:$(CWD)/lib/* ImageProcess.java ImageProcessTest.java

run :
	java -cp .:$(CWD)/lib/* ImageProcessTest valve.png 2.0 2.0 1.5707

clean :
	rm *.class 


