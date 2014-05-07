package ist.meic.pa;

import ist.meic.pa.traceinfo.TraceInfo;

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
	private static IdentityHashMap<Object, LinkedList<Integer>> objectMap = new IdentityHashMap<Object, LinkedList<Integer>>();
	private static HashMap<Integer, TraceInfo> traceInfoMap = new HashMap<Integer, TraceInfo>();
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
	public void addInfo(TraceInfo info, Object[] args) {
		int hash = info.hashCode();

		if (!traceInfoMap.containsKey(hash)) {
			traceInfoMap.put(hash, info);
		}
		
		if (args.length > 0) {
			info.setArgument(true);
		}
		
		System.err.println("Method: " + info.getBehaviour());
		System.err.println("Argument: " + info.isArgument());
		System.err.println("Result: " + info.isReturn());
		System.err.println("---------------------------");

		for (Object object : args) {
			saveTraceInfoInObject(info, hash, object);
		}

	}

	public void addInfo(TraceInfo info, Object[] args, Object result) {
		int hash = info.hashCode();
		addInfo(info, args);
		info.setReturn(true);
		saveTraceInfoInObject(info, hash, result);
	}

	/**
	 * @param info
	 * @param hash
	 * @param object
	 */
	private void saveTraceInfoInObject(TraceInfo info, int hash, Object object) {
		LinkedList<Integer> list;
		if (objectMap.containsKey(object)) {
			list = objectMap.get(object);
			if (list.getLast() == hash) {
				info.incrementCounter();
			} else {
				list.addLast(hash);
			}
		} else {
			list = new LinkedList<Integer>();
			list.add(info.hashCode());
			objectMap.put(object, list);
		}
	}

	/**
	 * Prints the.
	 * 
	 * @param object
	 *            the object
	 */
	static public void print(Object object) {
		if (objectMap.size() > 0) {
			
			System.err.println("Tracing for " + object);
			if (objectMap.containsKey(object)) {
				for (int hash : objectMap.get(object)) {
					traceInfoMap.get(hash).print();
				}
			}
			
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
