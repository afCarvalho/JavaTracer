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
	 * Gets the info.
	 * 
	 * @param object
	 * @return a string representing the info
	 */
	public String getInfo(Object object) {
		return getBehaviour() + " on " + getFile() + ":" + getLine();
	}
}
