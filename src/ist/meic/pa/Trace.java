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
	private static IdentityHashMap<Object, LinkedList<TraceKey>> objectMap = new IdentityHashMap<Object, LinkedList<TraceKey>>();
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
		if (!traceInfoMap.containsKey(info.getTraceHash())) {
			traceInfoMap.put(info.getTraceHash(), info);
		}

		for (Object object : args) {
			saveTraceInfoInObject(info, object);
			objectMap.get(object).getLast().setArg(true);
		}
	}

	public void addInfo(TraceInfo info, Object[] args, Object result) {
		addInfo(info, args);
		saveTraceInfoInObject(info, result);
		objectMap.get(result).getLast().setResult(true);
	}

	/**
	 * @param info
	 * @param hash
	 * @param object
	 */
	private void saveTraceInfoInObject(TraceInfo info, Object object) {
		LinkedList<TraceKey> list = objectMap.get(object);
		if (list != null) {
			if (list.getLast().getTraceHash() == info.getTraceHash()) {
				info.incrementCounter();
			} else {
				list.addLast(new TraceKey(info.getTraceHash()));
			}
		} else {
			list = new LinkedList<TraceKey>();
			list.add(new TraceKey(info.getTraceHash()));
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
				for (TraceKey key : objectMap.get(object)) {
					traceInfoMap.get(key.getTraceHash()).print(key.isArg(),
							key.isResult());
				}
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
