package exceptions;

public class MovementException extends GameActionException {
	private MovementException() {
		super();
	}

	private MovementException(String message) {
		super(message);
	}
}