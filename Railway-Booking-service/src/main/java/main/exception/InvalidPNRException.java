package main.exception;

@SuppressWarnings("serial")
public class InvalidPNRException extends Exception {

	public InvalidPNRException() {

	}

	public InvalidPNRException(String s) {
		super(s);
	}

}
