package exceptions;

public class NoAvailableResourcesException extends GameActionException {
	private NoAvailableResourcesException() {
		super();
	}

	private NoAvailableResourcesException(String message) {
		super(message);
	}
}
