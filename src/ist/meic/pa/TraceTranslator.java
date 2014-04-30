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
			traceMethods(ctClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	void traceMethods(CtClass ctClass) throws NotFoundException,
			CannotCompileException, ClassNotFoundException {
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			/*
			 * ctMethod.insertBefore("{ist.meic.pa.TraceTranslator.traceMethod("
			 * + ctMethod.getLongName() + ");}");
			 */
			ctMethod.instrument(new ExprEditor() {
				public void edit(MethodCall m) throws CannotCompileException {

					m.replace("{ist.meic.pa.TraceTranslator.traceMethod(\"m.getMethod().getLongName():\",\"m.getFileName():\",\"m.getLineNumber():\"); $_ = $proceed($$); }");

					/*
					 * System.err.println("--------");
					 * System.err.println("m.getMethodName(): " +
					 * m.getMethodName() + "; m.getFileName(): " +
					 * m.getFileName() + "; line: " + m.getLineNumber()); try {
					 * System.err.println("longName: " +
					 * m.getMethod().getLongName()); } catch (NotFoundException
					 * e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */
				}
			});
		}
	}

	public static void traceMethod(String methodLongName, String fileName,
			String lineNumber) {

		// FIXME mudar o isReturn para true/false conforme o
		// caso
		TraceInfo info = new TraceInfo(true, methodLongName, fileName,
				lineNumber);
		Trace.addInfo("ObjectoTeste", info);
		// System.err.println(" methodLongName: " + methodLongName +
		// " fileName: "
		// + fileName + " lineNumber:" + lineNumber);
	}
}
