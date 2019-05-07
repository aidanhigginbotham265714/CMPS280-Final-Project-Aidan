package CMPS280;

public class WrongInputException extends NullPointerException {

	private int wrongInput;

	public static String errorMessage = "Invalid Input Action";// static error message that happens when Wrong Input
																// Exception occurs

	public WrongInputException() {
		super(errorMessage);
	}//end of Default constructor

	public WrongInputException(int newWrongInput) {
		super("Invalid Input: " + newWrongInput);
		wrongInput = newWrongInput;
	}//end of constructor 

	public int getWrongInput() {
		return wrongInput;
	}//end of getWrongInput

	public static String getErrorMessage() {
		return errorMessage;
	}//end of getErrorMessage

}//end of WrongInputException
