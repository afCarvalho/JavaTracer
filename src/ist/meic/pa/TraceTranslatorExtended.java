package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.expr.Cast;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.Handler;

public class TraceTranslatorExtended extends TraceTranslator {

	public TraceTranslatorExtended() {
		// TODO Auto-generated constructor stub
	}

	@Override
	void traceMethods(final CtClass ctClass, final String className)
			throws NotFoundException, CannotCompileException,
			ClassNotFoundException {
		for (final CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			ctMethod.instrument(traceMethodCall());
			ctMethod.instrument(traceConstructors());
			ctMethod.instrument(traceHandlers());
			ctMethod.instrument(traceFields());
			ctMethod.instrument(traceCasts());
		}
	}

	protected ExprEditor traceHandlers() {
		return new ExprEditor() {

			@Override
			public void edit(Handler h) throws CannotCompileException {
				final String info = "\"" + h.where().getLongName() + "\",\""
						+ h.getFileName() + "\"," + h.getLineNumber();

				h.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslatorExtended.traceObj("
						+ info + ",$1);}");
			}
		};
	}

	protected ExprEditor traceFields() {
		return new ExprEditor() {

			@Override
			public void edit(FieldAccess f) throws CannotCompileException {
				final String info = "\"" + f.where().getLongName() + "\",\""
						+ f.getFileName() + "\"," + f.getLineNumber();

				if (f.isReader()) {
					f.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslatorExtended.traceObj("
							+ info + ",($w)$_);}");
				}

				if (f.isWriter()) {
					f.replace("{ist.meic.pa.TraceTranslatorExtended.traceObj("
							+ info + ",($w)$1); $_ = $proceed($$);}");
				}
			}
		};
	}

	protected ExprEditor traceCasts() {
		return new ExprEditor() {

			@Override
			public void edit(Cast c) throws CannotCompileException {
				final String info = "\"" + c.where().getLongName() + "\",\""
						+ c.getFileName() + "\"," + c.getLineNumber();

				c.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslatorExtended.traceMethod("
						+ info + ",$_);}");
			}
		};
	}

	public static void traceObj(String methodName, String fileName, int line,
			Object obj) {
		// Trace.getTrace().addInfo(new TraceInfo(methodName, fileName, line,
		// obj);
	}
}
