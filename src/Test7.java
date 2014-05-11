import ist.meic.pa.Trace;

import java.util.*;

class Test07 {

	Map m = new HashMap();

	public Object identity(Object o) {
		return o;
	}

	public void test() {
		Object o = new String("MyObj");

		try {
			m.containsKey(null);
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}

		Trace.print(o);

	}
}

public class Test7 {
	public static void main(String args[]) {
		(new Test07()).test();
	}
}
