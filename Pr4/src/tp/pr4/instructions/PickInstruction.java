package tp.pr4.instructions;

import tp.pr4.Escribe;
import tp.pr4.NavigationModule;
import tp.pr4.RobotEngine;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.Item;
import tp.pr4.items.ItemContainer;

public class PickInstruction implements Instruction{

	public PickInstruction(){
		
	}
	public PickInstruction(String id){
		this.id = id;
		this.container = null;
		this.navigation = null;
	}
	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(PICK) || arrayInstruction[0].equalsIgnoreCase(COGER))){
			return new PickInstruction(arrayInstruction[1]);
		}else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " PICK|COGER <id>";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.navigation = navigation;
		this.container = robotContainer;	
	}

	@Override
	public void execute() throws InstructionExecutionException{
		Item item = this.navigation.pickItemFromCurrentPlace(id);
		if(item == null) throw new InstructionExecutionException(Escribe.PLACE_NOT_OBJECT.replace("<id>", id));
		
		else if(this.container.addItem(item)) Escribe.say(Escribe.NOW_HAVE.replace("<id>", id));
			
		else	throw new InstructionExecutionException(Escribe.HAD_OBJECT.replace("<id>", id));		
	}
	
	private String id;
	private ItemContainer container;
	private NavigationModule navigation;
	
	private static final String PICK = "PICK";
	private static final String COGER = "COGER";
	
	
}
