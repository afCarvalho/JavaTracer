package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

public class TraceTranslator implements Translator {

	public TraceTranslator() {
		// Nothing to do here
	}

	public void start(ClassPool pool) throws NotFoundException,
			CannotCompileException {
		// Nothing to do here
	}

	public void onLoad(ClassPool pool, String className)
			throws NotFoundException, CannotCompileException {
		CtClass ctClass = pool.get(className);
		try {
			traceMethods(ctClass, className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	void traceMethods(final CtClass ctClass, final String className)
			throws NotFoundException, CannotCompileException,
			ClassNotFoundException {
		for (final CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			ctMethod.instrument(new ExprEditor() {
				public void edit(MethodCall m) throws CannotCompileException {
					if (ctClass.getPackageName() == null) {

						TraceInfo info = null;
						try {
							info = new TraceInfo(m.getMethod().getLongName(), m
									.getFileName(), m.getLineNumber());
						} catch (NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						Trace.createInfo(info);

						m.replace("{ist.meic.pa.TraceTranslator.traceMethod($args, $type); $_ = $proceed($$); }");
					}
				}
			});
		}
	}

	public static void traceMethod(Object[] args, Object result) {
		Trace.addInfo(args, result);
	}
}
