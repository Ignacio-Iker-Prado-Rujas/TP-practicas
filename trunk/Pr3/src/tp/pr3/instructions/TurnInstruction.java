package tp.pr3.instructions;

import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.Rotation;
import tp.pr3.items.ItemContainer;

public class TurnInstruction implements Instruction{

	@Override
	public Instruction parse(String cadena) {
		if(RIGHT.equalsIgnoreCase(cadena)) this.rotation = Rotation.RIGHT;
		else if(LEFT.equalsIgnoreCase(cadena)) this.rotation = Rotation.LEFT;
		else this.rotation = Rotation.UNKNOWN;
		return this;
	}

	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
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
	private static final String RIGHT = "RIGHT";
	private static final String LEFT = "LEFT";
	private Rotation rotation;

}
