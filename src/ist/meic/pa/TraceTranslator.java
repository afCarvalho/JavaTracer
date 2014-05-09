package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
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

		for (final CtBehavior ctBehaviour : ctClass.getDeclaredBehaviors()) {
			ctBehaviour.instrument(new ExprEditor() {

				@Override
				public void edit(MethodCall m) {
					traceMethodCall(m);
				}

				@Override
				public void edit(NewExpr expr) {
					traceConstructors(expr);
				}

			});
		}
	}

	protected void traceMethodCall(MethodCall m) {
		try {
			final String info = "\"" + m.getMethod().getLongName() + "\",\""
					+ m.getFileName() + "\"," + m.getLineNumber();

			if (m.getMethod().getReturnType().equals(CtClass.voidType)) {
				m.replace("{ist.meic.pa.TraceTranslator.traceMethod(" + info
						+ ",$args); $_ = $proceed($$);}");
			} else {
				m.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslator.traceMethod("
						+ info + ",$args,($w) $_);}");
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
	}

	protected void traceConstructors(NewExpr expr) {
		try {
			final String info = "\"" + expr.getConstructor().getLongName()
					+ "\",\"" + expr.getFileName() + "\","
					+ expr.getLineNumber();

			expr.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslator.traceMethod("
					+ info + ",$args,($w) $_);}");
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void traceMethod(String methodName, String fileName,
			int line, Object[] args, Object result) {
		traceMethod(methodName, fileName, line, args);

		TraceInfo info = new TraceInfo(methodName, fileName, line);
		info.setResult(true);
		Trace.addTraceInfo(info, args);
	}

	public static void traceMethod(String methodName, String fileName,
			int line, Object[] args) {
		TraceInfo info;

		for (Object arg : args) {
			info = new TraceInfo(methodName, fileName, line);
			info.setArg(true);
			Trace.addTraceInfo(info, arg);
		}
	}
}
