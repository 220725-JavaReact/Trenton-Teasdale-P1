package exceptions;

@SuppressWarnings("serial")
public class InvalidNumberException extends RuntimeException {

	public InvalidNumberException(String message) {
		super(message);
	}
}
