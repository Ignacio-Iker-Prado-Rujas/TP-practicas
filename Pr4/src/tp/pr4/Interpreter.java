package tp.pr4;


import tp.pr4.instructions.*;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;

public class Interpreter {

	/*Genera uan instrucción a partir de un string:
	 * Se recorre el array de instrucciones llamando 
	 * al parse de cada una. Si el parse de una devuelve una
	 * excepción es que no es de ese tipo, o no es válida.
	 * Si no lanza una excepción es que es una instrucción
	 * correcta de ese tipo y se devuelve correctamente inicializada
	 */
	public static Instruction generateInstruction(String line) throws WrongInstructionFormatException{
		for( Instruction  i : arrayInstructions ){
			try{ 
				return i.parse(line);
			} catch( WrongInstructionFormatException w){}
		}
		throw new WrongInstructionFormatException();//Si no ha devuelto ninguna instrucción es que no era válida.
	}

	//Devuelve un string con las instrucciones válidas del robot
	public static String interpreterHelp() {
		String help = "";
		for( Instruction  i : arrayInstructions ){
			help += (i.getHelp() + LINE_SEPARATOR);
		}
		return help;
	}
	
	private static Instruction[] arrayInstructions = { new DropInstruction(),
			new HelpInstruction(), new MoveInstruction(),
			new OperateInstruction(), new PickInstruction(),
			new QuitInstruction(), new RadarInstruction(),
			new ScanInstruction(), new TurnInstruction() };

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
}