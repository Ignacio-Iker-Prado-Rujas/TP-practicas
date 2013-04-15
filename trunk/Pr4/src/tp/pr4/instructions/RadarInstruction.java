package tp.pr4.instructions;

import tp.pr4.EscribeConsola;
import tp.pr4.NavigationModule;
import tp.pr4.Place;
import tp.pr4.RobotEngine;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.ItemContainer;

public class RadarInstruction implements Instruction{

	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(RADAR))){
			return new RadarInstruction();
		}else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " RADAR";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.engine = engine;
		this.place = navigation.getCurrentPlace();
		
	}

	@Override
	public void execute() {
		EscribeConsola.currentPlace(this.place);
	}
	@Override
	public void undo() throws InstructionExecutionException {
		engine.lastInstruction().undo();
	}
	
	private RobotEngine engine;
	private Place place;
	
	private static final String RADAR = "RADAR";

}
