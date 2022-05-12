package main.exception;

@SuppressWarnings("serial")
public class PassengersNotFoundException extends Exception {
	public PassengersNotFoundException() {

	}

	public PassengersNotFoundException(String str) {
		super(str);
	}

}
