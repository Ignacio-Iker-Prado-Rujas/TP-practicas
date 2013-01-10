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
		return " TURN | GIRAR < LEFT|RIGHT >";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		navigation = this.navigation;
	}

	@Override
	public void execute() {
		this.navigation.rotate(rotation);
	}
	private static final String RIGHT = "RIGHT";
	private static final String LEFT = "LEFT";
	private NavigationModule navigation;
	private Rotation rotation;

}
