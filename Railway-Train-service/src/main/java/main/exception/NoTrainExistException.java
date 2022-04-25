package main.exception;

public class NoTrainExistException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoTrainExistException(String s) {
        super(s);
    }
    public NoTrainExistException() {
		// TODO Auto-generated constructor stub
	}
}