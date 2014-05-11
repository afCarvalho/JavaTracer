import ist.meic.pa.Trace;

class Test05 {

	private boolean teste;

	protected Test05() {
		this.teste = true;
	}

	private Test05Aux aux(Test05Aux arg1, boolean arg2) {
		if (arg2)
			return arg1;

		return aux2(arg1);
	}

	public Test05Aux aux2(Test05Aux arg) {
		
		return Test05Aux.auxOutraClasse(arg);
	}

	public void test() {
		Object o = new String("teste");

		Trace.print(o);

		Trace.print(teste);

		o = o + ":" + "extra";

		Trace.print(o);

		Test05Aux aux = new Test05Aux();

		aux(aux, true);

		Trace.print(aux);
		
		aux(aux, false);

		Trace.print(aux);

	}
}

public class Test5 {
	public static void main(String args[]) {
		(new Test05()).test();
	}
}
