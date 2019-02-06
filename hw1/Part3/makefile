CWD:=$(abspath $(dir $(lastword $(MAKEFILE_LIST)))/../)
all : main

main : ComponentTest

ComponentTest : Component.java ComponentTest.java
	javac -cp .:$(CWD)/lib/* Component.java ComponentTest.java

run :
	java -cp .:$(CWD)/lib/* ComponentTest abdomen.png 4 

run2 : 
	java -cp .:$(CWD)/lib/* ComponentTest abdomen.png 4 3

run3 :
	java -cp .:$(CWD)/lib/* ComponentTest abdomen.png 8 3

clean :
	rm *.class 


