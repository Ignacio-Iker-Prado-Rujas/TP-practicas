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
		throw new WrongInstructionFormatException();//Si no ha devuelto ninguna instrucción es que no era válida.
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