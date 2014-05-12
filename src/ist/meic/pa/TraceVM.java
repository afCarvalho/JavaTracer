package ist.meic.pa;

import javassist.ClassPool;
import javassist.Loader;

public class TraceVM {

	private static final String TRACE = "ist.meic.pa.Trace";
	private static final String TRANSLATOR = "ist.meic.pa.TraceTranslator";

	public TraceVM() {
		// Nothing to do here
	}

	public static void main(String[] args) throws Throwable {
		if (args.length < 1) {
			System.err.println("Usage: TraceVM <className>");
		} else {
			Loader classLoader = new Loader();
			classLoader.addTranslator(ClassPool.getDefault(),
					new TraceTranslator());
			delegateClasses(classLoader);
			runClassLoader(classLoader, args);
		}
	}

	/**
	 * Delegates classes to a different VM
	 * 
	 * @param loader
	 */
	public static void delegateClasses(Loader loader) {
		loader.delegateLoadingOf(TRACE);
		loader.delegateLoadingOf(TRANSLATOR);
	}

	/**
	 * Prepares and runs the class loader
	 * 
	 * @param classLoader
	 *            - class loader
	 * @param args
	 *            - arguments to be passed
	 * @throws Throwable
	 */
	public static void runClassLoader(Loader classLoader, String args[])
			throws Throwable {
		String[] restArgs = new String[args.length - 1];
		System.arraycopy(args, 1, restArgs, 0, restArgs.length);
		classLoader.run(args[0], restArgs);
	}
}
