package tp.pr3.instructions;

import tp.pr3.Escribe;
import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.Rotation;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.ItemContainer;

public class TurnInstruction implements Instruction{
	public TurnInstruction(){
		this.rotation = Rotation.UNKNOWN;
		this.navigation = null;
	}
	public TurnInstruction(Rotation rotation){
		this.rotation = rotation;
		this.navigation = null;
	}
	@Override
	public Instruction parse(String cadena)  throws WrongInstructionFormatException{
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(TURN)||arrayInstruction[0].equalsIgnoreCase(GIRAR))) {
			if(arrayInstruction[1].equalsIgnoreCase(RIGHT))return new TurnInstruction(Rotation.RIGHT);
			else if(arrayInstruction[1].equalsIgnoreCase(LEFT))return new TurnInstruction(Rotation.LEFT);
			else throw new WrongInstructionFormatException();
			/*Los switch de string no funcionan en infor, asik con is else*/
		} 
		else /* cadena que no tenía dos palabras */ throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " TURN | GIRAR < LEFT|RIGHT >";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.navigation = navigation;
		this.robot = engine;
	}

	@Override
	public void execute() {
		this.navigation.rotate(rotation);
		Escribe.lookingDirection(this.navigation.getCurrentHeading());
		this.robot.addFuel(-5);
	
	}
	private static final String RIGHT = "RIGHT";
	private static final String LEFT = "LEFT";
	private static final String TURN = "TURN";
	private static final String GIRAR = "GIRAR";
	private RobotEngine robot;
	private NavigationModule navigation;
	private Rotation rotation;

}
