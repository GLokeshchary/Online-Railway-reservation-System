package main.exception;


@SuppressWarnings("serial")
public class StationNotExistException extends Exception {
    public StationNotExistException(String s) {
        super(s);
    }
    public StationNotExistException() {
		
	}
}
