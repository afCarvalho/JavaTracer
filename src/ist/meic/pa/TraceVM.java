package ist.meic.pa;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;

public class TraceVM {

	public TraceVM() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Throwable {
		if (args.length < 1) {
			System.err.println("Usage: TraceVM <className>");
		} else {
			/* initializes the table with the info */
			Trace.init();
			Translator translator = new TraceTranslator();
			ClassPool pool = ClassPool.getDefault();
			Loader classLoader = new Loader();
			classLoader.addTranslator(pool, translator);
			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
			classLoader.run(args[0], restArgs);
		}
	}
}
