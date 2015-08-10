package model;
@SuppressWarnings("serial")
public class InvalidPositionException extends Exception {

	public InvalidPositionException() {
		super();
	}

	public InvalidPositionException(String message) {
		super(message);
	}

	public InvalidPositionException(Throwable throwable) {
		super(throwable);
	}

	public InvalidPositionException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public InvalidPositionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

}
