package tp.pr3;

import java.util.ArrayList;
import tp.pr3.instructions.*;

import tp.pr3.instructions.Instruction;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;

public class Interpreter {
	//Genera una instrucción a partir del input del usuario 
	private static ArrayList<Instruction> createInstructions() {
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

	public static Instruction generateInstruction(String line) throws WrongInstructionFormatException{
		ArrayList<Instruction> instructions = createInstructions();
		for( Instruction  i : instructions ){
			try{ 
				return i.parse(line);
			} catch( WrongInstructionFormatException w){}
		}
		throw new WrongInstructionFormatException();
		
		
		if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("PICK"))
				instruction = new Instruction(Action.PICK, arrayInstruction[1]);
		
		else if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("OPERATE"))
			instruction = new Instruction(Action.OPERATE, arrayInstruction[1]);
		
		else if (arrayInstruction.length == 2 && arrayInstruction[0].equalsIgnoreCase("SCAN"))
			instruction = new Instruction(Action.SCAN, arrayInstruction[1]);
		
		else if (arrayInstruction.length == 1) {
			switch (arrayInstruction[0].toUpperCase()) {
				
				case "SCAN": instruction = new Instruction(Action.SCAN); break;
				
				case "QUIT": instruction = new Instruction(Action.QUIT); break;
				
				default: instruction = new Instruction();
			}
		}
		
		else
			instruction = new Instruction();
		
		return instruction;	
	}

	//Devuelve un string con las instrucciones válidas del robot
	public static String interpreterHelp() {
		String help = "";
		ArrayList<Instruction> instructions = createInstructions();
		for( Instruction  i : instructions ){
			help += (i.getHelp() + LINE_SEPARATOR);
		}
		return help;
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
}