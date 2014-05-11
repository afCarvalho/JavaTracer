import ist.meic.pa.Trace;

class Test06 {

	Object o = new String("Teste6");

	String os = (String) o;

	public void test() {
		Trace.print(o);
		Trace.print(os);
	}
}

public class Test6 {

	public static void main(String args[]) {
		(new Test06()).test();
	}

}