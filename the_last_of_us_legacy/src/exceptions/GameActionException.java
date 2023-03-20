package exceptions;

public class GameActionException extends Exception {

	private GameActionException() {
		super();
	}

	private GameActionException(String s) {
		super(s);
	}

	private class InvalidTargetException extends Exception {
		private InvalidTargetException() {
			super();
		}

		private InvalidTargetException(String s) {
			super(s);
		}
	}

	private class MovementException extends Exception {
		private MovementException() {
			super();
		}

		private MovementException(String s) {
			super(s);
		}
	}

	private class NoAvailableResourcesException extends Exception {
		private NoAvailableResourcesException() {
			super();
		}

		private NoAvailableResourcesException(String s) {
			super(s);
		}
	}

	private class NotEnoughActionsException extends Exception {
		private NotEnoughActionsException() {
			super();
		}

		private NotEnoughActionsException(String s) {
			super(s);
		}
	}
}
