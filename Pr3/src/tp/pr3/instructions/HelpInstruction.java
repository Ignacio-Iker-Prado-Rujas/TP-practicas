package tp.pr3.instructions;

import tp.pr3.Interpreter;
import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.ItemContainer;

public class HelpInstruction implements Instruction{

	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException{
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(HELP) || arrayInstruction[0].equalsIgnoreCase(AYUDA))){
			return new HelpInstruction();
		}else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " HELP | AYUDA";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		// No necesita configurar nada;
		
	}

	@Override
	public void execute() {
		System.out.println(Interpreter.interpreterHelp());	
	}
	private static final String HELP = "HELP";
	private static final String AYUDA = "AYUDA";
}
