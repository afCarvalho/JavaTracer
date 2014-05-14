package ist.meic.pa;

public enum Role {

	ARGUMENT("  -> "), RESULT("  <- "), CAST("  * Cast: "), HANDLER(
			"  * Handler: "), READ_FIELD("  * Read Field: "), WRITE_FIELD(
			"  * Write Field: ");

	String prefix;

	Role(String prefix) {
		this.prefix = prefix;
	}

	public void printMessage(String traceMessage) {
		System.err.println(prefix + traceMessage);
	}
}
