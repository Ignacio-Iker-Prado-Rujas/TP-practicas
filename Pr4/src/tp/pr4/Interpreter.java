package tp.pr4;

import java.util.ArrayList;

import tp.pr4.instructions.*;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;

public class Interpreter {
	/*Crea un array con las instrucciones que entiende el robot*/
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

	/*Genera uan instrucción a partir de un string:
	 * Se recorre el array de instrucciones llamando 
	 * al parse de cada una. Si el parse de una devuelve una
	 * excepción es que no es de ese tipo, o no es válida.
	 * Si no lanza una excepción es que es una instrucción
	 * correcta de ese tipo y se devuelve correctamente inicializada
	 */
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