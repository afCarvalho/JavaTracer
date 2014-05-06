package ist.meic.pa;

import ist.meic.pa.traceinfo.TraceInfo;

import java.lang.reflect.Method;
import java.util.ArrayList;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * The Class Trace.
 */
public class Trace {

	/** The trace info table. */
	private static ArrayList<TraceInfo> traceInfoTable = new ArrayList<TraceInfo>();
	private static final Trace INSTANCE = new Trace();

	/**
	 * Instantiates a new trace.
	 */
	private Trace() {
	}

	public static Trace getTrace() {
		return INSTANCE;
	}

	/**
	 * Creates a new info.
	 * 
	 * @param info
	 *            the info
	 */
	public void addInfo(Object[] args, Object result, TraceInfo info) {
		/* percorre os args e o result e adiciona 'a hash */
	}

	/**
	 * Prints the.
	 * 
	 * @param object
	 *            the object
	 */
	static public void print(Object object) {
		if (traceInfoTable.size() > 0) {
			// se o obj for chave
			/*
			 * System.err.println("Tracing for " + object); for (TraceInfo info
			 * : traceInfoTable) { if (info.getArgs() != null) {
			 * info.printInfo(object); } }
			 */
		} else {
			System.err.println("Tracing for object is nonexistent!");
		}
	}

	public static void main(String[] args) throws Throwable {
		if (args.length < 1) {
			System.err.println("Usage: Trace <className>");
		} else {
			ClassPool pool = ClassPool.getDefault();
			CtClass ctClass = pool.get(args[0]);
			Class<?> rtClass = ctClass.toClass();
			Method main = rtClass.getMethod("main", args.getClass());
			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
			main.invoke(null, new Object[] { restArgs });
		}
	}
}
