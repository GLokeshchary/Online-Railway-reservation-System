package main.exception;

@SuppressWarnings("serial")
public class InvalidCoachNameException extends Exception {

	public InvalidCoachNameException() {

	}

	public InvalidCoachNameException(String s) {
		super(s);
	}
}
