package tp.pr4.instructions;

import tp.pr4.Escribe;
import tp.pr4.NavigationModule;
import tp.pr4.RobotEngine;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.Item;
import tp.pr4.items.ItemContainer;

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
		return " OPERATE|OPERAR <id>";
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
		Item item = this.container.getItem(id);
		if (item == null) throw new InstructionExecutionException(Escribe.NOT_HAVE_THE_OBJECT.replace("<id>", this.id));
		else if (item.use(this.robot, this.navigation)){
			if (!item.canBeUsed()){
				Escribe.say(Escribe.NO_MORE_OBJECT.replace("<id>", this.id));
				this.container.pickItem(this.id);
			}
		}
		else throw new InstructionExecutionException(Escribe.PROBLEMS_USING_OBJECT.replace("<id>", this.id));		
	}
	
	private RobotEngine robot;
	private NavigationModule navigation;
	private ItemContainer container;
	private String id;
	
	private static final String OPERATE = "OPERATE";
	private static final String OPERAR = "OPERAR";
}
