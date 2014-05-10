package ist.meic.pa;

/**
 * 
 * This class represents the trace info correspoding to the occurence of an
 * object in a specific file, method and line
 * 
 */
public class TraceInfo {
	private String traceMessage;
	private boolean isArgument;
	private boolean isResult;
	private int counter;

	public TraceInfo(String methodName, String fileName, int line) {
		setTraceMessage(methodName, fileName, line);
		counter = 1;
	}

	/**
	 * Tells if object has the role of argument
	 * 
	 * @return - predicate
	 */
	public boolean isArg() {
		return isArgument;
	}

	public void setArg(boolean isArgument) {
		this.isArgument = isArgument;
	}

	/**
	 * Tells if object has the role of result
	 * 
	 * @return - predicate
	 */
	public boolean isResult() {
		return isResult;
	}

	public void setResult(boolean isResult) {
		this.isResult = isResult;
	}

	/**
	 * returns the string representing the basic trace message
	 * 
	 * @return - string with message
	 */
	public String getTraceMessage() {
		return traceMessage;
	}

	public void setTraceMessage(String behaviour, String file, int line) {
		traceMessage = behaviour + " on " + file + ":" + line;
	}

	/**
	 * Prints the complete trace message
	 */
	public void printTraceMessage() {
		// System.err.println(traceMessage + " " + isArgument + " " + isResult);
		for (int i = 0; i < counter; i++) {
			if (isArgument) {
				System.err.println("  -> " + traceMessage);
			}

			if (isResult) {
				System.err.println("  <- " + traceMessage);
			}
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
	public boolean hasSameMessage(TraceInfo info) {
		return info.getTraceMessage().equals(traceMessage);
	}

	/**
	 * Tells if two traceInfos have the same role
	 * 
	 * @param info
	 *            - trace info being compared with this one
	 * @return - predicate
	 */
	public boolean hasSameRole(TraceInfo info) {
		return info.isResult == isResult && info.isArgument == isArgument;
	}

	/**
	 * Tells if two traceInfos have opposite role
	 * 
	 * @param info
	 *            - trace info being compared with this one
	 * @return - predicate
	 */
	public boolean hasOppositeRole(TraceInfo info) {
		return info.isArgOnly() && isResultOnly() || info.isResultOnly()
				&& isArgOnly();
	}

	/**
	 * Tells if this traceInfo has argument role only
	 * 
	 * @return - predicate
	 */
	private boolean isArgOnly() {
		return isArgument && !isResult;
	}

	/**
	 * Tells if this traceInfo has result role only
	 * 
	 * @return - predicate
	 */
	private boolean isResultOnly() {
		return !isArgument && isResult;
	}
}
