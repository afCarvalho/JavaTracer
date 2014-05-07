package ist.meic.pa;

public class TraceKey {
	private int traceHash;
	private boolean isArg;
	private boolean isResult;

	public TraceKey(int traceHash) {
		this.traceHash = traceHash;
	}

	public boolean isArg() {
		return isArg;
	}

	public void setArg(boolean isArg) {
		this.isArg = isArg;
	}

	public boolean isResult() {
		return isResult;
	}

	public void setResult(boolean isResult) {
		this.isResult = isResult;
	}

	public int getTraceHash() {
		return traceHash;
	}

	public void setTraceHash(int traceHash) {
		this.traceHash = traceHash;
	}

}
