package exceptions;
public class InvalidTargetException extends GameActionException {
	private InvalidTargetException() {
		super();
	}

	private InvalidTargetException(String message) {
		super(message);
	}
}