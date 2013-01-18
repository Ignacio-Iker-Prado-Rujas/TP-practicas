package tp.pr3;

import java.util.ArrayList;
import tp.pr3.instructions.*;

import tp.pr3.instructions.Instruction;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;

public class Interpreter {
	//Genera una instrucción a partir del input del usuario 
	public static ArrayList<Instruction> createInstructions() {
        ArrayList<Instruction> instructions = new ArrayList<Instruction>();
        instructions.add(new DropInstruction());
        instructions.add(new HelpInstruction());
        instructions.add(new MoveInstruction());
        instructions.add(new OperateInstruction());
        instructions.add(new PickInstruction());
        instructions.add(new QuitInstruction());
        instructions.add(new RadarInstruction());
        instructions.add(new ScanInstruction());
        instructions.add(new TurnInstruction());
        return instructions;
}

	public static Instruction generateInstruction(String line) {
		ArrayList<Instruction> instructions = createInstructions();
		for( Instruction  i : instructions ){
			try{ 
				return i.parse(line);
			} catch( WrongInstructionFormatException w){}
		}
		
		if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("TURN"/*TODO Girar??*/)) {
			instruction = TurnInstruction.parse(arrayInstruction[1]);
			
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
		String help = ("The valid instructions for WALL-E are:"
				+ LINE_SEPARATOR + "     MOVE" + LINE_SEPARATOR
				+ "     TURN <LEFT | RIGHT>" + LINE_SEPARATOR + "     PICK <id>"
				+ LINE_SEPARATOR + "     SCAN [ <id> ]" + LINE_SEPARATOR
				+ "     OPERATE <id>" + LINE_SEPARATOR + "     HELP" + LINE_SEPARATOR + "     QUIT" + LINE_SEPARATOR);
		return help;
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
}