package ist.meic.pa;

public class TraceInfo {

	private boolean isReturn;
	private String behaviour;
	private String file;
	private String line;

	public TraceInfo(boolean isReturn, String behaviour, String file,
			String line) {
		this.isReturn = isReturn;
		this.behaviour = behaviour;
		this.file = file;
		this.line = line;
	}

	public boolean isReturn() {
		return isReturn;
	}

	public String getBehaviour() {
		return behaviour;
	}

	public String getFile() {
		return file;
	}

	public String getLine() {
		return line;
	}
}
