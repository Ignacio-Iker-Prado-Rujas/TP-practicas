package tp.pr3.instructions;

import tp.pr3.Escribe;
import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.instructions.exceptions.InstructionExecutionException;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.Item;
import tp.pr3.items.ItemContainer;

public class DropInstruction implements Instruction{
public DropInstruction(){
		
	}
	public DropInstruction(String id){
		this.id = id;
		this.container = null;
		this.navigation = null;
	}
	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(DROP))){
			return new DropInstruction(arrayInstruction[1]);
		}else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " DROP <id>";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.container = robotContainer;
		this.navigation = navigation;	
	}

	@Override
	public void execute() throws InstructionExecutionException {
		Item item = this.container.pickItem(id);
		if(item == null) throw new InstructionExecutionException(Escribe.say(Escribe.NOT_HAVE_THE_OBJECT.replace("<id>", id)));
		
		else if(this.navigation.getCurrentPlace().addItem(item)){
			Escribe.say(Escribe.OBJECT_DROPPED.replace("<place>", this.navigation.getCurrentPlace().toString()));
		}
			
		else{
			throw new InstructionExecutionException(Escribe.say(Escribe.THE_OBJECT_WAS_IN_PLACE.replace("<id>", id)));		
		}
		
	}
	
	private String id;
	private ItemContainer container;
	private NavigationModule navigation;
	
	private static final String DROP = "DROP";
}
