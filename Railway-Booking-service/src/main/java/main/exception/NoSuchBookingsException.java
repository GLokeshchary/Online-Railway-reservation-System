package main.exception;

@SuppressWarnings("serial")
public class NoSuchBookingsException extends Exception {
	
	public NoSuchBookingsException() {
		
	}
	public NoSuchBookingsException(String s) {
		super(s);
	}

}
