package tp.pr3.instructions;

import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.ItemContainer;

public class MoveInstruction implements Instruction{

	@Override
	public Instruction parse(String cadENA) {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(MOVE) || arrayInstruction[0].equalsIgnoreCase(MOVER))){
			return new MoveInstruction();
		}else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " MOVE | MOVER";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		
	}
	private static final String MOVE = "MOVE";
	private static final String MOVER = "MOVER";
}
