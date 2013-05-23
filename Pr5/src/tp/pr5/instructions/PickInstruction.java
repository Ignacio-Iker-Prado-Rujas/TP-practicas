package tp.pr5.instructions;

import tp.pr5.EscribeConsola;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

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
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(PICK) || arrayInstruction[0].equalsIgnoreCase(COGER))) {
			return new PickInstruction(arrayInstruction[1]);
		} else 
			throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " PICK|COGER <id>";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.engine = engine;
		this.navigation = navigation;
		this.container = robotContainer;	
	}

	@Override
	public void execute() throws InstructionExecutionException {
		item = this.navigation.pickItemFromCurrentPlace(id);
		if (item == null)
			throw new InstructionExecutionException(
					EscribeConsola.PLACE_NOT_OBJECT.replace("<id>", id));

		else if (container.addItem(item))
			engine.saySomething(EscribeConsola.NOW_HAVE.replace("<id>", item.getId()));
		/*
		 * if
		 * (engine.modoConsola())EscribeConsola.say(EscribeConsola.NOW_HAVE.replace
		 * ("<id>", id)); else engine.addItem(id, item.getDescription());
		 */
		else
			throw new InstructionExecutionException(
					EscribeConsola.HAD_OBJECT.replace("<id>", id));
	}

	@Override
	public void undo() throws InstructionExecutionException {
		if (item == null) 
			engine.lastInstruction().undo();
		else {
			this.container.pickItem(id);
			this.navigation.dropItemAtCurrentPlace(item);
			engine.deleteItem(id);
		}
	}
	
	public String toString() {
		return "Pick";
	}
	
	private Item item;
	private String id;
	private RobotEngine engine;
	private ItemContainer container;
	private NavigationModule navigation;
	
	private static final String PICK = "PICK";
	private static final String COGER = "COGER";
	
	
}
