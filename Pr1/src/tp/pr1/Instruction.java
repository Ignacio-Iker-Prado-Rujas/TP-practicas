package tp.pr1;

public class Instruction {
	public Instruction() {
		// Este constructor crea una accion desconocida
		action = Action.UNKNOWN;
		rotation = Rotation.UNKNOWN;
	}

	public Instruction(Action action) {
		// Este constructor crea una accion de la que no se conoce el enum rotation
		this.action = action;
		this.rotation = Rotation.UNKNOWN;
	}

	public Instruction(Action action, Rotation rotation) {
		// Este constructor crea una accion e inicializa ambos atributos
		this.action = action;
		this.rotation = rotation;
	}

	// Devuelve si la acción recibida es correcta true. False si es desconocida.

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