package ist.meic.pa.traceinfo;

/**
 * The Class TraceInfo.
 * 
 * This class stores trace information.
 */
public class TraceInfo {

	/** Method's long name. */
	private String behaviour;

	/** File where the method is called. */
	private String file;

	/** Line where the method is called. */
	private int line;

	/** The arguments of the method call. */
	private Object[] args;

	/** The result of the method call. */
	private Object result;

	/**
	 * Instantiates a new trace info.
	 * 
	 * @param behaviour
	 *            the behaviour
	 * @param file
	 *            the file
	 * @param line
	 *            the line
	 */
	public TraceInfo(String behaviour, String file, int line) {
		this.behaviour = behaviour;
		this.file = file;
		this.line = line;
	}

	/**
	 * Gets the method's long name.
	 * 
	 * @return the behaviour
	 */
	public String getBehaviour() {
		return behaviour;
	}

	/**
	 * Gets the file where the method is called.
	 * 
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * Gets the line where the method is called.
	 * 
	 * @return the line
	 */
	public int getLine() {
		return line;
	}

	/**
	 * Gets the arguments of the method call.
	 * 
	 * @return the args
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * Sets the arguments of the method call.
	 * 
	 * @param args
	 *            the new args
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}

	/**
	 * Gets the result of the method call.
	 * 
	 * @return the result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * Sets the result of the method call.
	 * 
	 * @param result
	 *            the new result
	 */
	public void setResult(Object result) {
		this.result = result;
	}

	public void printInfo(Object object) {
		String info = getBehaviour() + " on " + getFile() + ":"
				+ getLine();

		for (Object obj : args) {
			if (obj == object) {
				System.err.println("  -> " + info);
			}
		}

		// System.out.println(object + " vs " + result);
		if (object == result) {
			System.err.println("  <- " + info);
		}
	}
}
