package tp.pr4.instructions;

import tp.pr4.Escribe;
import tp.pr4.NavigationModule;
import tp.pr4.RobotEngine;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.ItemContainer;

public class MoveInstruction implements Instruction{

	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(MOVE) || arrayInstruction[0].equalsIgnoreCase(MOVER)))
			return new MoveInstruction();
		else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " MOVE | MOVER";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.navigation = navigation;
		this.robot = engine;
	}
	
	@Override
	public void execute() throws InstructionExecutionException {
		this.navigation.move();
		Escribe.say(Escribe.MOVING_DIRECTION + this.navigation.getCurrentHeading().toString());
		Escribe.currentPlace(this.navigation.getCurrentPlace());
		System.out.println();
		this.robot.addFuel(-5); //Actualiza el fuel al moverse.		
	}
	private RobotEngine robot;
	private NavigationModule navigation;
	
	
	private static final String MOVE = "MOVE";
	private static final String MOVER = "MOVER";
}
