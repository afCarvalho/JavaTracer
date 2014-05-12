package ist.meic.pa;

import java.util.LinkedList;
import java.util.Map.Entry;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.expr.Cast;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import javassist.expr.Handler;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;
import javassist.expr.NewExpr;

/**
 * 
 * This class represents the translator of handlers, fields and casts
 * 
 */
public class TraceTranslatorExtended extends TraceTranslator {

	public TraceTranslatorExtended() {
		// TODO Auto-generated constructor stub
	}

	@Override
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

				@Override
				public void edit(FieldAccess field) {
					traceFields(field);
				}

				@Override
				public void edit(Cast cast) {
					traceCasts(cast);
				}

				@Override
				public void edit(Handler handler) {
					traceHandlers(handler);
				}

			});
		}
	}

	/**
	 * Traces exception handlers
	 * 
	 * @param h
	 *            - handler
	 */
	protected void traceHandlers(Handler h) {
		try {
			final String info = "\"" + h.where().getLongName() + "\",\""
					+ h.getFileName() + "\"," + h.getLineNumber();
			h.insertBefore("{ist.meic.pa.TraceTranslatorExtended.traceObj("
					+ info + ",$1);}");
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Traces fields
	 * 
	 * @param f
	 *            - field
	 */
	protected void traceFields(FieldAccess f) {
		try {
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

		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Traces casts
	 * 
	 * @param c
	 *            - cast
	 */
	protected void traceCasts(Cast c) {
		try {
			final String info = "\"" + c.where().getLongName() + "\",\""
					+ c.getFileName() + "\"," + c.getLineNumber();
			c.replace("{$_ = $proceed($$); ist.meic.pa.TraceTranslatorExtended.traceObj("
					+ info + ",$_);}");
		} catch (CannotCompileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void traceObj(String methodName, String fileName, int line,
			Object obj) {
		TraceInfo info = new TraceInfo(methodName, fileName, line);
		Trace.addTraceInfo(info, obj);
	}
}