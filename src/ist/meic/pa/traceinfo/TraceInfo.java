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

	/** The number of times a line must be printed. */
	private int counter;
	
	private boolean isArgument;
	
	private boolean isReturn;
	
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
		this.counter = 0;
		this.isArgument = false;
		this.isReturn = false;
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
	public String getInfo() {
		return getBehaviour() + " on " + getFile() + ":" + getLine();
	}

	/**
	 * Gets the counter.
	 *
	 * @return the counter
	 */
	public final int getCounter() {
		return counter;
	}

	/**
	 * Increments the counter.
	 * 
	 * @return the new value of the counter.
	 */
	public int incrementCounter() {
		return counter++;
	}
	
	/**
	 * @return the isArgument
	 */
	public final boolean isArgument() {
		return isArgument;
	}

	/**
	 * @param isArgument the isArgument to set
	 */
	public final void setArgument(boolean isArgument) {
		this.isArgument = isArgument;
	}

	/**
	 * @return the isReturn
	 */
	public final boolean isReturn() {
		return isReturn;
	}

	/**
	 * @param isReturn the isReturn to set
	 */
	public final void setReturn(boolean isReturn) {
		this.isReturn = isReturn;
	}

	public void print() {
		if (isArgument) {
			System.err.println("  -> " + getInfo());
		} 
		
		if (isReturn) {
			System.err.println("  <- " + getInfo());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((behaviour == null) ? 0 : behaviour.hashCode());
		result = prime * result + ((file == null) ? 0 : file.hashCode());
		result = prime * result + line;
		return result;
	}
}
