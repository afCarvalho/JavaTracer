package ist.meic.pa;

/**
 * 
 * This class represents the trace info corresponding to the occurrence of an
 * object in a specific file, method and line
 * 
 */
public class TraceInfo {

	private String traceMessage;
	private Role role;
	private int counter;

	public TraceInfo(String methodName, String fileName, int line, Role role) {
		setTraceMessage(methodName, fileName, line);
		counter = 1;
		this.role = role;
	}

	private boolean hasRole(Role role) {
		return this.role.compareTo(role) == 0;
	}

	/**
	 * returns the string representing the basic trace message
	 * 
	 * @return - string with message
	 */
	private String getTraceMessage() {
		return traceMessage;
	}

	public void setTraceMessage(String behaviour, String file, int line) {
		traceMessage = behaviour + " on " + file + ":" + line;
	}

	/**
	 * Prints the complete trace message
	 */
	public void printTraceMessage() {
		// System.err.println(traceMessage);
		for (int i = 0; i < counter; i++) {
			role.printMessage(traceMessage);
		}
	}

	/**
	 * Increments the counter representing the number of times an object appears
	 * sequentially
	 */
	public void incrementCounter() {
		counter++;
	}

	/**
	 * Tells if two traceInfos have the same basic message
	 * 
	 * @param info
	 *            - trace info being compared with this one
	 * @return - predicate
	 */
	private boolean hasSameMessage(TraceInfo info) {
		return info.getTraceMessage().equals(traceMessage);
	}

	public boolean isSameTrace(TraceInfo info) {
		return this.hasRole(info.role) && this.hasSameMessage(info);
	}
}
