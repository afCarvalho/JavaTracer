package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import javassist.Translator;

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
			ctMethod.insertBefore("TraceTranslator.traceMethod("
					+ ctMethod.getName() + ")");
		}
	}

	static void traceMethod(CtMethod method) {

		// TODO
		// percorre os metodos da classe e cria TraceInfo que depois mete na
		// hash table
		// fazer metodo e depois a string e' a chamada ao metodo
	}
}
