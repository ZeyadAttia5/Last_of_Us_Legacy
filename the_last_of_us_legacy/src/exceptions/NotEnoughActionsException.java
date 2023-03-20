package exceptions;

public class NotEnoughActionsException extends GameActionException {
	private NotEnoughActionsException() {
		super();
	}

	private NotEnoughActionsException(String message) {
		super(message);
	}
}
