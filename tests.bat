cd classes
java -cp .;tracer.jar;../lib/javassist.jar ist.meic.pa.TraceVM Test0 > test0.out 2>&1
java -cp .;tracer.jar;../lib/javassist.jar ist.meic.pa.TraceVM Test1 > test1.out 2>&1
java -cp .;tracer.jar;../lib/javassist.jar ist.meic.pa.TraceVM Test2 > test2.out 2>&1
cd ..

FC /A classes\test0.out test0.expected
FC /A classes\test1.out test1.expected
FC /A classes\test2.out test2.expected
