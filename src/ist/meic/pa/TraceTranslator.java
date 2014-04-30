package ist.meic.pa;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
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
			memoizeMethods(ctClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	void memoizeMethods(CtClass ctClass) throws NotFoundException,
			CannotCompileException, ClassNotFoundException {
		for (CtMethod ctMethod : ctClass.getDeclaredMethods()) {
			// TODO
			// percorre os metodos da classe e cria TraceInfo que depois mete na
			// hash table
			// fazer metodo e depois a string e' a chamada ao metodo
		}
	}
}
