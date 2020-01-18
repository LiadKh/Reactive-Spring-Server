package server;

public class BadReqException extends RuntimeException {

	private static final long serialVersionUID = -7853885751913798806L;

	public BadReqException() {
	}

	public BadReqException(String message) {
		super(message);
	}

	public BadReqException(Throwable cause) {
		super(cause);
	}

	public BadReqException(String message, Throwable cause) {
		super(message, cause);
	}
}