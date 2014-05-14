package ist.meic.pa;

import java.lang.reflect.Method;
import java.util.IdentityHashMap;
import java.util.LinkedList;

import javassist.ClassPool;
import javassist.CtClass;

/**
 * The Class Trace.
 */
public class Trace {

	/**
	 * The trace info table. It maps objects with corresponding tracing
	 * information
	 * */
	private static IdentityHashMap<Object, LinkedList<TraceInfo>> objectMap = new IdentityHashMap<Object, LinkedList<TraceInfo>>();

	/**
	 * Adds information to the map containing trace information about each
	 * object
	 * 
	 * @param traceInfo
	 *            - The information to add
	 * @param object
	 *            - the corresponding object
	 * @return
	 */
	public static void addTraceInfo(TraceInfo traceInfo, Object object) {
		LinkedList<TraceInfo> list = objectMap.get(object);

		if (list == null) {
			list = new LinkedList<TraceInfo>();
			list.add(traceInfo);
			objectMap.put(object, list);
		} else if (traceInfo.isSameTrace(list.getLast())) {
			list.getLast().incrementCounter();
		} else {
			list.addLast(traceInfo);
		}
	}

	/**
	 * Prints collected trace information
	 * 
	 * @param object
	 *            the object from which information will be printed
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
