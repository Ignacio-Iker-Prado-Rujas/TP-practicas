package tp.pr3.instructions;

import tp.pr3.Escribe;
import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.items.Item;
import tp.pr3.items.ItemContainer;

public class ScanInstruction implements Instruction{

	public Instruction parse(String cad) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHelp() {
		// TODO Auto-generated method stub
		return null;
	}

	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.robotContainer = robotContainer;
		
	}

	public void execute() {
		String id = this.id;
		if(this.robotContainer.numberOfItems() == 0) 
			Escribe.say(Escribe.INV_EMPTY); //say("My inventory is empty");
		else if (id == null) 
			Escribe.say(Escribe.CARRYING_ITEMS + this.robotContainer.toString());// say("I am carrying the following items" + this.robotContainer.toString());
		else{
			Item item = this.robotContainer.getItem(id);
			if (item == null)
				Escribe.say(Escribe.NOT_HAVE_THE_OBJECT); //say("I have not such object");
			else 
				Escribe.say(item.toString()); //say(item.toString());
		}
		
	}
	private ItemContainer robotContainer;
	private String id;
}
