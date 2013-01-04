package tp.pr2;

public class Interpreter {
	//Genera una instrucción a partir del input del usuario
	public static Instruction generateInstruction(String line) {
		String[] arrayInstruction = line.split(" ");
		Instruction instruction;
		
		if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("TURN")) {
			switch (arrayInstruction[1].toUpperCase()) {
				case "RIGHT": instruction = new Instruction(Action.TURN, Rotation.RIGHT); break;
				
				case "LEFT": instruction = new Instruction(Action.TURN, Rotation.LEFT); break;
				
				default: instruction = new Instruction(Action.TURN);
			}
		}
		
		else if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("PICK"))
				instruction = new Instruction(Action.PICK, arrayInstruction[1]);
		
		else if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("OPERATE"))
			instruction = new Instruction(Action.OPERATE, arrayInstruction[1]);
		
		else if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("SCAN"))
			instruction = new Instruction(Action.SCAN, arrayInstruction[1]);
		
		else if (arrayInstruction.length == 1) {
			switch (arrayInstruction[0].toUpperCase()) {
				case "TURN": instruction = new Instruction(Action.TURN); break;
				
				case "SCAN": instruction = new Instruction(Action.SCAN); break;
				
				case "QUIT": instruction = new Instruction(Action.QUIT); break;
				
				case "MOVE": instruction = new Instruction(Action.MOVE); break;
				
				case "HELP": instruction = new Instruction(Action.HELP); break;
				
				default: instruction = new Instruction();
			}
		}
		
		else
			instruction = new Instruction();
		
		return instruction;	
	}

	//Devuelve un string con las instrucciones válidas del robot
	public static String interpreterHelp() {
		String help = ("The valid instructions for WALL�E are:"
				+ LINE_SEPARATOR + "  MOVE" + LINE_SEPARATOR
				+ "  TURN <LEFT | RIGHT>" + LINE_SEPARATOR + "  PICK <id>"
				+ LINE_SEPARATOR + "  SCAN [ <id> ]" + LINE_SEPARATOR
				+ "  OPERATE <id>" + LINE_SEPARATOR + "  HELP" + LINE_SEPARATOR + "  QUIT");
		return help;
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

}
