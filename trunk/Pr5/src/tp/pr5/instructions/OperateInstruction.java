package tp.pr5.instructions;

import tp.pr5.EscribeConsola;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class OperateInstruction implements Instruction{
	public OperateInstruction(){
		this.id = "";
		this.container = null;
	}
	public OperateInstruction(String id){
		this.id = id;
		this.container = null;
	}
	
	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(OPERATE) || arrayInstruction[0].equalsIgnoreCase(OPERAR))){
			return new OperateInstruction(arrayInstruction[1]);
		}else throw new WrongInstructionFormatException();
	}

	@Override
	public String getHelp() {
		return " OPERATE | OPERAR <id>";
	}

	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.robot = engine;
		this.container = robotContainer;	
		this.navigation = navigation;
	}

	@Override
	public void execute() throws InstructionExecutionException {
		item = this.container.getItem(id);
		if (item == null) throw new InstructionExecutionException(EscribeConsola.NOT_HAVE_THE_OBJECT.replace("<id>", this.id));
		else if (item.use(this.robot, this.navigation)){
			container.useItem(item);
		}
		else throw new InstructionExecutionException(EscribeConsola.PROBLEMS_USING_OBJECT.replace("<id>", this.id));		
	}
	
	@Override
	public void undo() throws InstructionExecutionException {
			if(item == null) robot.lastInstruction().undo();
			else{
				item.desUse(robot, navigation);
				// TODO: if(this.container.addItem(item)) robot.addItem(id, item.getDescription()); //Si ya estaba no se añade
				
			}
	}
	
	public String toString() {
		return "Operate";
	}
	
	private Item item;
	private RobotEngine robot;
	private NavigationModule navigation;
	private ItemContainer container;
	private String id;
	
	private static final String OPERATE = "OPERATE";
	private static final String OPERAR = "OPERAR";

	
}
