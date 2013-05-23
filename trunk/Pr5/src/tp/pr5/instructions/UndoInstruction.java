package tp.pr5.instructions;

import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.ItemContainer;

public class UndoInstruction implements Instruction { 
	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(UNDO)))
			return new UndoInstruction();
		else
			throw new WrongInstructionFormatException();
	}
	
	@Override
	public String getHelp() {
		return " UNDO";
	}
	
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation,
			ItemContainer robotContainer) {
		this.engine = engine;
		
	}
	
	@Override
	public void execute() throws InstructionExecutionException {
		engine.lastInstruction().undo();
	}
	
	@Override
	public void undo() throws InstructionExecutionException {
		engine.lastInstruction().undo();
	}
	
	
	private RobotEngine engine;
	private static final String UNDO = "UNDO";
}
