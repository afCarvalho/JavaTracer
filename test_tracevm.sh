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
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test0 &> test0.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test1 &> test1.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test2 &>  test2.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test3 &>  test3.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test4 10 &>  test4.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test5 &>  test5.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test6 &>  test6.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test7 &>  test7.out;
java -cp ./classes:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test8 &>  test8.out;

#Diff
diff test0.out test0.expected;
diff test1.out test1.expected;
diff test2.out test2.expected;
diff test3.out test3.expected;
diff test4.out test4.expected;
diff test5.out test5.expected;
diff test6.out test6.expected;
diff test7.out test7.expected;
diff test8.out test8.expected;


