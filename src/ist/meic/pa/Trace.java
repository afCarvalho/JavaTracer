package ist.meic.pa;

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
	public void createInfo(TraceInfo info) {
		traceInfoTable.add(info);
	}

	/**
	 * Adds new information to the last info added to the list.
	 * 
	 * @param args
	 *            the arguments of the method call
	 * @param result
	 *            the result of the method call
	 */
	public void addInfo(Object[] args, Object result) {
		if (traceInfoTable.size() > 0) {
			TraceInfo info = traceInfoTable.get(traceInfoTable.size() - 1);
			info.setArgs(args);
			info.setResult(result);
		}
	}

	/**
	 * Prints the.
	 * 
	 * @param object
	 *            the object
	 */
	public static void print(Object object) {
		System.err.println("-----print-----");
		if (traceInfoTable != null) {
			System.err.println("object: " + object);
			System.err.println("size: " + traceInfoTable.size());
			for (TraceInfo info : traceInfoTable) {
				System.err.println("behaviour: " + info.getBehaviour());
				System.err.println("file: " + info.getFile());
				System.err.println("line: " + info.getLine());
				if (info.getArgs() != null) {
					for (int i = 0; i < info.getArgs().length; i++) {
						System.err
								.println("arg" + i + ": " + info.getArgs()[i]);
					}
				}
				System.err.println("result: " + info.getResult());
			}
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
