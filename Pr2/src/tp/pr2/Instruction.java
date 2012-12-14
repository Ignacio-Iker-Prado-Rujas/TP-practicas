package tp.pr2;

public class Instruction {
	
	// Este constructor crea una accion desconocida
	public Instruction() {
		this.action = Action.UNKNOWN;
		this.rotation = Rotation.UNKNOWN;
	}

	// Este constructor crea una accion de la que no se conoce el enumerado rotation
	public Instruction(Action action) {
		this.action = action;
		this.rotation = Rotation.UNKNOWN;
	}

	// Este constructor crea una accion e inicializa ambos atributos (!=UNKNOWN)
	public Instruction(Action action, Rotation rotation) {
		this.action = action;
		this.rotation = rotation;
	}
	
	// Devuelve true si la accion recibida es valida, y false si es desconocida
	public boolean isValid() {
		if (this.action.equals(Action.UNKNOWN))
			return false;
		else if (this.action.equals(Action.TURN) && this.rotation.equals(Rotation.UNKNOWN))
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

	private Action action;
	private Rotation rotation;
}
