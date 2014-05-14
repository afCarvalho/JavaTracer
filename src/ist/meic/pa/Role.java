package ist.meic.pa;

/**
 * This enumerate maps roles into expected prefix output
 */

public enum Role {

	ARGUMENT("  -> "), RESULT("  <- "), CAST("  * Cast: "), HANDLER(
			"  * Handler: "), READ_FIELD("  * Read Field: "), WRITE_FIELD(
			"  * Write Field: ");

	/**
	 * role prefix to be appended
	 */
	String prefix;

	Role(String prefix) {
		this.prefix = prefix;
	}

	/**
	 * Prints the expected output
	 * 
	 * @param traceMessage
	 *            - base message to be printed with prefix appended
	 */
	public void printMessage(String traceMessage) {
		System.err.println(prefix + traceMessage);
	}
}
