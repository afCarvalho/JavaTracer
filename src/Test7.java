import ist.meic.pa.Trace;

import java.util.*;

class Test07 {

	public Object identity(Object o) {
		return o;
	}

	public void test() {
		Object o = new String("MyObj");

		try {
			throw new Test7Exception(o);
		} catch (Test7Exception e) {
			System.err.println(e.getMessage());
		}

		Trace.print(o);

	}
}

public class Test7 {
	public static void main(String args[]) {
		(new Test07()).test();
	}
}
