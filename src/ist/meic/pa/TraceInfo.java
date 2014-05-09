package ist.meic.pa;

public class TraceInfo {
	private String traceMessage;
	private boolean isArgument;
	private boolean isResult;
	private int counter;

	public TraceInfo(String methodName, String fileName, int line) {
		setTraceMessage(methodName, fileName, line);
		counter = 1;
	}

	public boolean isArg() {
		return isArgument;
	}

	public void setArg(boolean isArgument) {
		this.isArgument = isArgument;
	}

	public boolean isResult() {
		return isResult;
	}

	public void setResult(boolean isResult) {
		this.isResult = isResult;
	}

	public String getTraceMessage() {
		return traceMessage;
	}

	public void setTraceMessage(String behaviour, String file, int line) {
		traceMessage = behaviour + " on " + file + ":" + line;
	}

	public void printTraceMessage() {

		 System.err.println(traceMessage + " " + isArgument + " " + isResult);

		for (int i = 0; i < counter; i++) {
			if (isArgument) {
				System.err.println("  -> " + traceMessage);
			}

			if (isResult) {
				System.err.println("  <- " + traceMessage);
			}
		}
	}

	public void incrementCounter() {
		counter++;
	}

	public boolean hasSameMessage(TraceInfo info) {
		return info.getTraceMessage().equals(traceMessage);
	}

	public boolean hasSameRole(TraceInfo info) {
		return info.isResult == isResult && info.isArgument == isArgument;
	}

	public boolean hasOppositeRole(TraceInfo info) {
		return info.isArgOnly() && isResultOnly() || info.isResultOnly()
				&& isArgOnly();
	}

	public boolean isArgOnly() {
		return isArgument && !isResult;
	}

	public boolean isResultOnly() {
		return !isArgument && isResult;
	}
}
