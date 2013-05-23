package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

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
		this.robot = engine;
	}

	@Override
	public void execute() {
		this.robot.requestHelp();
	}
	
	@Override
	public void undo() throws InstructionExecutionException {
		robot.lastInstruction().undo();
	}
	
	private RobotEngine robot;
	
	private static final String HELP = "HELP";
	private static final String AYUDA = "AYUDA";
	
}
