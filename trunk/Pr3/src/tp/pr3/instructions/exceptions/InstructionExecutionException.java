package tp.pr3.instructions.exceptions;

public class InstructionExecutionException extends Exception {

	public InstructionExecutionException() {
		// TODO Auto-generated constructor stub
	}

	public InstructionExecutionException(String message) {
		System.err.println(message);
	}
}
