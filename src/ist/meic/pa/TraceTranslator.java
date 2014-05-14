package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import javassist.Translator;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;
import javassist.expr.NewExpr;

/**
 * 
 * This class represents the translator of methods, constructors and arrays
 * 
 */
public class TraceTranslator implements Translator {

	public TraceTranslator() {
		// Nothing to do here
	}

	public void start(ClassPool pool) throws NotFoundException,
			CannotCompileException {
	}

	public void onLoad(ClassPool pool, String className)
			throws NotFoundException, CannotCompileException {
		CtClass ctClass = pool.get(className);
		try {
			traceMethods(ctClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Traces method and constructor calls
	 * 
	 * @param ctClass
	 *            - class being loaded
	 * @throws NotFoundException
	 * @throws CannotCompileException
	 * @throws ClassNotFoundException
	 */

	void traceMethods(final CtClass ctClass) throws NotFoundException,
			CannotCompileException, ClassNotFoundException {

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

	/**
	 * Traces method calls
	 * 
	 * @param m
	 *            - method call
	 */
	protected void traceMethodCall(MethodCall m) {
		try {
			if (m.getMethod().getReturnType().equals(CtClass.voidType)) {
				m.replace(beforeInjection(m.getMethod().getLongName(),
						m.getFileName(), m.getLineNumber()));
			} else {
				m.replace(afterInjection(m.getMethod().getLongName(),
						m.getFileName(), m.getLineNumber()));
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (CannotCompileException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Traces constructor calls
	 * 
	 * @param expr
	 *            - construtor expression call
	 */
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

	/**
	 * Code to be injected before method is executed
	 * 
	 * @param name
	 *            - method name
	 * @param file
	 *            - file name
	 * @param line
	 *            - line number
	 * @return - string with instructions to be injected
	 */
	public String afterInjection(String name, String file, int line) {
		return "{ist.meic.pa.TraceTranslator.traceMethodArgs("
				+ getInfoArgs(name, file, line)
				+ ",$args);$_ = $proceed($$);ist.meic.pa.TraceTranslator.traceMethodRes("
				+ getInfoArgs(name, file, line) + ",($w) $_);}";
	}

	/**
	 * Code to be injected after method is executed
	 * 
	 * @param name
	 *            - method name
	 * @param file
	 *            - file name
	 * @param line
	 *            - line number
	 * @return - string with instructions to be injected
	 */
	public String beforeInjection(String name, String file, int line) {
		return "{ist.meic.pa.TraceTranslator.traceMethodArgs("
				+ getInfoArgs(name, file, line)
				+ ",$args); $_ = $proceed($$);}";
	}

	/**
	 * Arranges the string of arguments to pass to injected methods
	 * 
	 * @param name
	 *            - method name
	 * @param file
	 *            - file name
	 * @param line
	 *            - line number
	 * @return - composed string
	 */
	public String getInfoArgs(String name, String file, int line) {
		return "\"" + name + "\",\"" + file + "\"," + line;
	}

	/**
	 * Method to be injected to trace arguments
	 * 
	 * @param name
	 *            - method name
	 * @param file
	 *            - file name
	 * @param line
	 *            - line number
	 * @param args
	 *            - call arguments
	 */
	public static void traceMethodArgs(String methodName, String fileName,
			int line, Object[] args) {
		TraceInfo info;

		for (Object arg : args) {
			info = new TraceInfo(methodName, fileName, line, Role.ARGUMENT);
			Trace.addTraceInfo(info, arg);
		}
	}

	/**
	 * Method to be injected to trace the return
	 * 
	 * @param name
	 *            - method name
	 * @param file
	 *            - file name
	 * @param line
	 *            - line number
	 * @param res
	 *            - result object
	 */
	public static void traceMethodRes(String methodName, String fileName,
			int line, Object res) {
		TraceInfo info;

		info = new TraceInfo(methodName, fileName, line, Role.RESULT);
		Trace.addTraceInfo(info, res);
	}
}
