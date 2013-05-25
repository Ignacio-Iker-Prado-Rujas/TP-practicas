package tp.pr5.instructions;

import tp.pr5.EscribeConsola;
import tp.pr5.NavigationModule;
import tp.pr5.RobotEngine;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.instructions.exceptions.WrongInstructionFormatException;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class DropInstruction implements Instruction{

	public DropInstruction() {
	}
	
	public DropInstruction(String id){
		this.id = id;
		this.container = null;
		this.navigation = null;
	}
	
	/**
	 * Comprueba que la cadena tenga dos palabras, y que la primera sea drop o soltar 
	 * Si no cumple las condiciones devuelve una excepción. Si cumple las condiciones
	 * devuelve una nueva instrucción con la id del objeto que se quiere soltar 
	 */
	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(DROP) || arrayInstruction[0].equalsIgnoreCase(SOLTAR)))
			return new DropInstruction(arrayInstruction[1]);
		else
			throw new WrongInstructionFormatException();
	}
	
	@Override
	public String getHelp() {
		return " DROP | SOLTAR <id>";
	}
	
	/* Guarda como atributos lo que vaya a necesitar la instrucción para ejecutarse */
	@Override
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer) {
		this.engine = engine;
		this.container = robotContainer;
		this.navigation = navigation;	
	}
	
	/** 
	 * Ejecuta la instrucción. Previamente debe haberse llamado al configure context. Si algo falla devuelve
	 * una excepción. Utiliza los atributos previamente configurados.  
	 */
	@Override
	public void execute() throws InstructionExecutionException {
		item = container.pickItem(id);
		if (item == null)
			throw new InstructionExecutionException(EscribeConsola.SAY + EscribeConsola.NOT_HAVE_THE_OBJECT.replace("<id>", id));
		if (navigation.findItemAtCurrentPlace(id))
			throw new InstructionExecutionException(EscribeConsola.SAY + EscribeConsola.THE_OBJECT_WAS_IN_PLACE.replace("<id>", id));
		else {
			this.navigation.dropItemAtCurrentPlace(item);
			this.engine.saySomething(EscribeConsola.OBJECT_DROPPED.replace("<id>", item.getId()));
		}
	}
	
	@Override
	public void undo() throws InstructionExecutionException {
		if (item == null) engine.lastInstruction().undo();
		else container.addItem(navigation.pickItemFromCurrentPlace(id));
	}
	
	public String toString() {
		return "Drop";
	}
	
	private Item item; // Para el undo.
	private String id;
	private RobotEngine engine;
	private ItemContainer container;
	private NavigationModule navigation;

	private static final String DROP = "DROP";
	private static final String SOLTAR = "SOLTAR";
	
}
