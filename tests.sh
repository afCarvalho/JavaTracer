#cd classes
java -cp .:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test0 > test0.out 2>&1
java -cp .:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test1 > test1.out 2>&1
java -cp .:tracer.jar:lib/javassist.jar ist.meic.pa.TraceVM Test2 > test2.out 2>&1
#cd ..

diff test0.out test0.expected
diff test1.out test1.expected
diff test2.out test2.expected
