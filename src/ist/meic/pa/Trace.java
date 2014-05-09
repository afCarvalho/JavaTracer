package ist.meic.pa;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedList;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * The Class Trace.
 */
public class Trace {

	/** The trace info table. */
	private static IdentityHashMap<Object, LinkedList<TraceInfo>> objectMap = new IdentityHashMap<Object, LinkedList<TraceInfo>>();
	private static final Trace INSTANCE = new Trace();

	/**
	 * Instantiates a new trace.
	 */
	private Trace() {
	}

	public static Trace getTrace() {
		return INSTANCE;
	}

	public void addArgumentInfo(TraceInfo traceInfo, Object argument) {
		traceInfo.setArg(true);
		saveTraceInfoInObject(traceInfo, argument);

	}

	public void addResultInfo(TraceInfo traceInfo, Object[] args, Object result) {
		traceInfo.setResult(true);
		saveTraceInfoInObject(traceInfo, result);
	}

	/*
	 * public void addInfo(TraceInfo traceInfo, Object[] args, Object result) {
	 * //traceInfo.setResult(true); addInfo(traceInfo, args);
	 * saveTraceInfoInObject(traceInfo, result); }
	 */

	/**
	 * @param traceKey
	 * @param hash
	 * @param object
	 * @return
	 */
	private static void saveTraceInfoInObject(TraceInfo traceKey, Object object) {
		LinkedList<TraceInfo> list = objectMap.get(object);

		if (list == null) {
			list = new LinkedList<TraceInfo>();
			list.add(traceKey);
			objectMap.put(object, list);
		} else if (list.getLast().equals(traceKey)) {
			list.getLast().incrementCounter();
		} else {
			list.addLast(traceKey);
		}
	}

	/**
	 * Prints the.
	 * 
	 * @param object
	 *            the object
	 */
	static public void print(Object object) {
		LinkedList<TraceInfo> list = objectMap.get(object);

		if (list.size() > 0) {
			System.err.println("Tracing for " + object);
			for (TraceInfo key : list) {
				key.printTraceMessage();
			}
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
