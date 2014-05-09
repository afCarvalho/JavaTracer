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

	@Override
	public boolean equals(Object o) {
		TraceInfo info = (TraceInfo) o;
		return info.getTraceMessage().equals(traceMessage)
				&& info.isResult == isResult && info.isArgument == isArgument;
	}
}
