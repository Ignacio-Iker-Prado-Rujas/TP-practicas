package tp.pr3.instructions;

import tp.pr3.NavigationModule;
import tp.pr3.RobotEngine;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.ItemContainer;

public interface Instruction {
	public Instruction parse(java.lang.String cad) throws WrongInstructionFormatException; 
	
	public String getHelp();
	
	public void configureContext(RobotEngine engine, NavigationModule navigation, ItemContainer robotContainer);
	
	public void execute(); //throws InstructionExecutionException;
            
	/*//Este constructor crea una instruccion desconocida
	public Instruction() {
		this.action = Action.UNKNOWN;
		this.rotation = Rotation.UNKNOWN;
		this.id = null;
	}

	//Este constructor crea una instruccion de la que no se conoce el enumerado rotation ni el identificador
	public Instruction(Action action) {
		this.action = action;
		this.rotation = Rotation.UNKNOWN;
		this.id = null;
	}

	//Este constructor crea una instruccion e inicializa la accion y la rotacion
	public Instruction(Action action, Rotation rotation) {
		this.action = action;
		this.rotation = rotation;
		this.id = null;
	}
	
	//Este constructor crea una instruccion con la accion y el identificador para la instruccion
	//Se corresponde con las instrucciones SCAN, OPERATE y PICK
	public Instruction(Action action, String id) {
		this.action = action;
		this.id = id;
		this.rotation = Rotation.UNKNOWN;
	}
	
	//Devuelve true si la accion recibida es valida, y false si es desconocida o no valida
	public boolean isValid() {
		if (this.action.equals(Action.UNKNOWN))
			return false;
		else if (this.action.equals(Action.TURN) && this.rotation.equals(Rotation.UNKNOWN))
			return false;
		else if ((this.action.equals(Action.OPERATE) || this.action.equals(Action.PICK)) && (id == null))
			//No pongo SCAN porque puede aparecer sin item, para ver los items que lleva el robot en un momento dado
			return false;
		else 
			return true;
	}

	public Action getAction() {
		return this.action;
	}

	public Rotation getRotation() {
		return this.rotation;
	}
	
	public String getId() {
		return this.id;
	}

	private Action action;
	private Rotation rotation;
	private String id;*/
}