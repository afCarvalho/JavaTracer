import ist.meic.pa.Trace;

import java.util.*;

class Test08 {

	public void identity(Object o) {
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

public class Test8 {
	public static void main(String args[]) {
		(new Test08()).test();
	}
}
