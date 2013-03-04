package tp.pr4.instructions;

import tp.pr4.Escribe;
import tp.pr4.NavigationModule;
import tp.pr4.RobotEngine;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.Item;
import tp.pr4.items.ItemContainer;

public class DropInstruction implements Instruction{
public DropInstruction(){
		
	}
	public DropInstruction(String id){
		this.id = id;
		this.container = null;
		this.navigation = null;
	}
	/* Comprueba que la cadena tenga dos palabras, y que la primera sea drop o soltar 
	 * Si no cumple las condiciones devuelve una excepción. Si cumple las condiciones
	 * devuelve una nueva instrucción con la id del objeto que se quiere soltar */
	@Override
	public Instruction parse(String cadena) throws WrongInstructionFormatException {
		String[] arrayInstruction = cadena.split(" ");
		if (arrayInstruction.length == 2 && (arrayInstruction[0].equalsIgnoreCase(DROP)||arrayInstruction[0].equalsIgnoreCase(SOLTAR))){
			return new DropInstruction(arrayInstruction[1]);
		}else throw new WrongInstructionFormatException();
	}
	
	@Override
	public String getHelp() {
		return " DROP|SOLTAR <id>";
	}
	/* Guarda como atributos lo que vaya a necesitar la instrucción para ejecutarse */
	@Override
	public void configureContext(RobotEngine engine,
			NavigationModule navigation, ItemContainer robotContainer) {
		this.container = robotContainer;
		this.navigation = navigation;	
	}
	/* Ejecuta la instrucción. Previamente debe haberse llamado al configure context. Si algo falla devuelve
	 * una excepción. Utiliza los atributos previamente configurados.  */
	@Override
	public void execute() throws InstructionExecutionException {
		Item item = this.container.pickItem(id);
		if(item == null) throw new InstructionExecutionException(Escribe.NOT_HAVE_THE_OBJECT.replace("<id>", id));
		if (navigation.findItemAtCurrentPlace(id))
			
			throw new InstructionExecutionException(Escribe.THE_OBJECT_WAS_IN_PLACE.replace("<id>", id));
		
		else{
			this.navigation.dropItemAtCurrentPlace(item);
			Escribe.mostrar(Escribe.OBJECT_DROPPED.replace("<id>", id));
		}
	}
	
	private String id;
	private ItemContainer container;
	private NavigationModule navigation;
	
	private static final String DROP = "DROP";
	private static final String SOLTAR = "SOLTAR";
}
