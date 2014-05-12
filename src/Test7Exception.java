public class Test7Exception extends Exception {

	private static final long serialVersionUID = 1L;
	private Object message;

	public Test7Exception(Object o) {
		this.message = o;
	}

	@Override
	public String getMessage() {
		return message.toString();
	}
}
