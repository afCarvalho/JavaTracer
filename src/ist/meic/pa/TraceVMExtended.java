package ist.meic.pa;

import javassist.ClassPool;
import javassist.Loader;

public class TraceVMExtended {

	private static final String TRACE = "ist.meic.pa.Trace";
	private static final String TRANSLATOR = "ist.meic.pa.TraceTranslator";
	private static final String TRANSLATOREXTENDED = "ist.meic.pa.TraceTranslatorExtended";

	public TraceVMExtended() {
		// Nothing to do here
	}

	public static void main(String[] args) throws Throwable {
		if (args.length < 1) {
			System.err.println("Usage: TraceVM <className>");
		} else {
			Loader classLoader = new Loader();
			classLoader.addTranslator(ClassPool.getDefault(),
					new TraceTranslatorExtended());
			classLoader.delegateLoadingOf(TRACE);
			classLoader.delegateLoadingOf(TRANSLATOR);
			classLoader.delegateLoadingOf(TRANSLATOREXTENDED);

			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
			classLoader.run(args[0], restArgs);
		}
	}
}