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
import javassist.expr.NewArray;
import javassist.expr.NewExpr;

public class TraceTranslator implements Translator {

	public TraceTranslator() {
	}

	public void start(ClassPool pool) throws NotFoundException,
			CannotCompileException {
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

				@Override
				public void edit(NewArray arr) {
					traceArray(arr);
				}

			});
		}
	}

	protected void traceMethodCall(MethodCall m) {
		try {
			if (m.getMethod().getReturnType().equals(CtClass.voidType)) {
				m.replace(beforeInjection(m.getMethod().getLongName(),
						m.getFileName(), m.getLineNumber()));
			} else {
				afterInjection(m.getMethod().getLongName(), m.getFileName(),
						m.getLineNumber());
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
	}

	protected void traceConstructors(NewExpr expr) {
		try {
			expr.replace(afterInjection(expr.getConstructor().getLongName(),
					expr.getFileName(), expr.getLineNumber()));
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
	}

	protected void traceArray(NewArray arr) {
		try {
			arr.replace(afterInjection(arr.where().getLongName(),
					arr.getFileName(), arr.getLineNumber()));
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
	}

	public String afterInjection(String name, String file, int line) {
		return "{$_ = $proceed($$); ist.meic.pa.TraceTranslator.traceMethod("
				+ getInfoArgs(name, file, line) + ",$args,($w) $_);}";
	}

	public String beforeInjection(String name, String file, int line) {
		return "{ist.meic.pa.TraceTranslator.traceMethod("
				+ getInfoArgs(name, file, line)
				+ ",$args); $_ = $proceed($$);}";
	}

	public String getInfoArgs(String name, String file, int line) {
		return "\"" + name + "\",\"" + file + "\"," + line;
	}

	public static void traceMethod(String methodName, String fileName,
			int line, Object[] args, Object result) {
		traceMethod(methodName, fileName, line, args);

		TraceInfo info = new TraceInfo(methodName, fileName, line);
		info.setResult(true);
		Trace.addTraceInfo(info, result);
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
