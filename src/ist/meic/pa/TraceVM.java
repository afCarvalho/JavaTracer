package ist.meic.pa;

import javassist.ClassPool;
import javassist.Loader;
import javassist.Translator;
import javassist.bytecode.stackmap.Tracer;

public class TraceVM {

	public TraceVM() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) throws Throwable {
		if (args.length < 1) {
			System.err.println("Usage: TraceVM <className>");
		} else {
			Translator translator = new TraceTranslator();
			ClassPool pool = ClassPool.getDefault();
			Loader classLoader = new Loader(pool);
			classLoader.addTranslator(pool, translator);
			classLoader.delegateLoadingOf("ist.meic.pa.Trace");
			String[] restArgs = new String[args.length - 1];
			System.arraycopy(args, 1, restArgs, 0, restArgs.length);
			classLoader.run(args[0], restArgs);
		}
	}
}
