package tp.pr4.instructions;

import tp.pr4.EscribeConsola;
import tp.pr4.NavigationModule;
import tp.pr4.Place;
import tp.pr4.RobotEngine;
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
		this.place = navigation.getCurrentPlace();
		
	}

	@Override
	public void execute() {
		EscribeConsola.currentPlace(this.place);
	}
	private Place place;
	
	private static final String RADAR = "RADAR";

}
