package tp.pr3.instructions.exceptions;

public class WrongInstructionFormatException extends Exception {

	public WrongInstructionFormatException() {
		// TODO Auto-generated constructor stub
	}

	public WrongInstructionFormatException(String message) {
		System.err.println(message);
	}
}