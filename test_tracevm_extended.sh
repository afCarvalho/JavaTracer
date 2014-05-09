#! /bin/sh
#
#Prepare
rm -rf classes;
rm -rf bin;
rm *.out;
mkdir classes;
touch test0.out;
touch test1.out;
touch test2.out;
touch teste3.out

#Compile
javac -cp .:tracer.jar:lib/javassist.jar ./src/ist/meic/pa/*.java ./src/*.java -d ./classes;

#Run
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVMExtended Test0 &> test0.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVMExtended Test1 &> test1.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVMExtended Test2 &>  test2.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVMExtended Test3 &>  test3.out;

#Diff
diff test0.out test0.expected;
diff test1.out test1.expected;
diff test2.out test2.expected;
diff test3.out test3.expected;

