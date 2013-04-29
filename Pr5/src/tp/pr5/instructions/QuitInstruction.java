package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class QuitInstruction implements Instruction{
	public QuitInstruction(){
		this.robot = null;
	}
	
	public Instruction parse(String cadena)  throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(QUIT) || arrayInstruction[0].equalsIgnoreCase(SALIR))){
			return new QuitInstruction();
		}else throw new WrongInstructionFormatException();
	}

	public String getHelp() {
		return HELP;
	}

	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
	}
	
	public void execute() {
		this.robot.requestQuit();
	}
	@Override
	public void undo() throws InstructionExecutionException {}
	
	private RobotEngine robot;
	
	private static final String QUIT = "QUIT";
	private static final String SALIR = "SALIR";
	private static final String HELP = " QUIT|SALIR";
}
