package ist.meic.pa;

import javassist.ClassPool;
import javassist.Loader;

public class TraceVMExtended extends TraceVM {

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
			delegateClasses(classLoader);
			classLoader.delegateLoadingOf(TRANSLATOREXTENDED);
			runClassLoader(classLoader, args);
		}
	}
}