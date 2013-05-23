package tp.pr5.instructions;

import tp.pr5.EscribeConsola;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class ScanInstruction implements Instruction{
	public ScanInstruction(){
		this.id = null;
		this.robotContainer = null;
	}
	public ScanInstruction(String id){
		this.id = id;
		this.robotContainer = null;
	}

	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 1 && (arrayInstruction[0].equalsIgnoreCase(SCAN) || arrayInstruction[0].equalsIgnoreCase(ESCANEAR))){
			return new ScanInstruction();
		}
		else if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(SCAN) || arrayInstruction[0].equalsIgnoreCase(ESCANEAR))){
			return new ScanInstruction(arrayInstruction[1]);
		}else throw new WrongInstructionFormatException();
	}

	public String getHelp() {
		return " SCAN | ESCANEAR [id]";
	}

	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.engine = engine;
		this.robotContainer = robotContainer;
	}

	public void execute() throws InstructionExecutionException{
		if(this.robotContainer.numberOfItems() == 0) 
			throw new InstructionExecutionException(EscribeConsola.SAY + EscribeConsola.INV_EMPTY); //say("My inventory is empty");(necesaria excepcion para tests)
		else if (this.id == null) 
			this.robotContainer.requestScanCollection();
		else {
			Item item = this.robotContainer.getItem(id);
			if (item == null)
				throw new InstructionExecutionException(EscribeConsola.SAY + EscribeConsola.NOT_HAVE_THE_OBJECT.replace("<id>", id));
			else 
				this.robotContainer.requestScanItem(id);
		}
		
	}
	
	@Override
	public void undo() throws InstructionExecutionException {
		engine.lastInstruction().undo();
	}
	
	private RobotEngine engine;
	private String id;
	private ItemContainer robotContainer;
	
	private static final String SCAN = "SCAN";
	private static final String ESCANEAR = "ESCANEAR";
	
}
