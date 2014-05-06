package ist.meic.pa;

import ist.meic.pa.traceinfo.TraceInfo;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

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

				@Override
				public void edit(MethodCall m) throws CannotCompileException {
					if (ctClass.getPackageName() == null) {

						try {
							String methodName = "\""
									+ m.getMethod().getLongName() + "\"";
							String fileName = "\"" + m.getFileName() + "\"";

							if (m.getMethod().getReturnType()
									.equals(CtClass.voidType)) {
								m.replace("{ist.meic.pa.TraceTranslator.traceMethod("
										+ "$args,$type,"
										+ methodName
										+ ","
										+ fileName
										+ ","
										+ m.getLineNumber()
										+ "); $_ = $proceed($$);}");
							} else {
								m.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslator.traceMethod("
										+ "$args,($w) $_,"
										+ methodName
										+ ","
										+ fileName
										+ ","
										+ m.getLineNumber()
										+ ");}");
							}
						} catch (NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				@Override
				public void edit(NewExpr expr) throws CannotCompileException {

					if (ctClass.getPackageName() == null) {

						try {

							String methodName = "\""
									+ expr.getConstructor().getLongName()
									+ "\"";
							String fileName = "\"" + expr.getFileName() + "\"";

							expr.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslator.traceMethod("
									+ "$args,($w) $_,"
									+ methodName
									+ ","
									+ fileName
									+ ","
									+ expr.getLineNumber()
									+ ");}");
						} catch (NotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			});
		}
	}

	public static void traceMethod(Object[] args, Object result,
			String methodName, String fileName, int line) {

		TraceInfo info = new TraceInfo(methodName, fileName, line);

		Trace.getTrace().addInfo(args, result, info);
	}
}
