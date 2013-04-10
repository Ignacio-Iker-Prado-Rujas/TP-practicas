package tp.pr4.instructions.exceptions;

public class InstructionExecutionException extends Exception {


	public InstructionExecutionException() {
		// TODO Auto-generated constructor stub
	}

	public InstructionExecutionException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	private String message;
	private static final long serialVersionUID = 1L;	//Daba warning
}
