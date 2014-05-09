import ist.meic.pa.Trace;

import java.util.*;

class Test03 {

	public Object identity(Object o) {
		return o;
	}

	public void test() {
        Object o = new String("MyObj");

        Trace.print(o);

        for (int i = 0; i < 5; i++) {
            identity(o);
        }

        Trace.print(o);

    }
}

public class Test3 {
	public static void main(String args[]) {
		(new Test03()).test();
	}
}
