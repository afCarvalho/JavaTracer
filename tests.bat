javac -cp lib/javassist.jar ./src/ist/meic/pa/*.java ./src/*.java -d ./classes
cd classes 
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVM Test0 > test0.out 2>&1
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVM Test1 > test1.out 2>&1
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVM Test2 > test2.out 2>&1
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVM Test3 > test3.out 2>&1
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVM Test4 10 > test4.out 2>&1
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVM Test5 > test5.out 2>&1
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVMExtended Test6 > test6.out 2>&1
java -cp .;../lib/javassist.jar ist.meic.pa.TraceVMExtended Test7 > test7.out 2>&1

FC /A test0.out ../test0.expected
FC /A test1.out ../test1.expected
FC /A test2.out ../test2.expected
FC /A test3.out ../test3.expected
FC /A test4.out ../test4.expected
FC /A test5.out ../test5.expected
FC /A test6.out ../test6.expected
FC /A test7.out ../test7.expected

cd ..