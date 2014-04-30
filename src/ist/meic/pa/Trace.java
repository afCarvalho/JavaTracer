package ist.meic.pa;

import java.util.ArrayList;
import java.util.HashMap;

public class Trace {

	private static HashMap<Object, ArrayList<TraceInfo>> traceInfoTable = new HashMap<Object, ArrayList<TraceInfo>>();

	public Trace() {
		// TODO Auto-generated constructor stub
	}

	static public void print(Object object) {
		// TODO
		// procura na hash table o object e depois percorre
		// o que encontra (de forma ordenada por: a) linhas b) entradas c)
		// saidas (confirmar)) e vai imprimindo
	}
}
