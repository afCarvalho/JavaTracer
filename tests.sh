#! /bin/sh
#
#Prepare
rm -rf classes;
rm -rf bin;
mkdir classes;
touch test0.out;
touch test1.out;
touch test2.out;

#Compile
#javac -cp .:tracer.jar:lib/javassist.jar ./src/ist/meic/pa/traceinfo/*.java ./src/ist/meic/pa/*.java ./src/*.java -d ./classes;

#Run
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test0 2> test0.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test1 2> test1.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test2 2> test2.out;

#Diff
diff test0.out test0.expected;
diff test1.out test1.expected;
diff test2.out test2.expected;

# rm *.out;
