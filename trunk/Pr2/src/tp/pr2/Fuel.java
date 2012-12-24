package tp.pr2;

public class Fuel extends Item {
	//Constructor de combustible que llama a la clase padre para completar la inicializacion
	public Fuel(String id, String description, int power, int times) {
		super(id, description);
		this.power = power;
		this.times = times;
	}

	//True si aun puede usarse, false en caso contrario
	public boolean canBeUsed() {
		if(this.times != 0)
			return true;
		else 
			return false;
	}
	
	//Usa el combustible aportando energia al robot
	//Devuelve true si se pudo usar
	public boolean use(RobotEngine r, Place p) {
		return true;
		//TODO: Ni idea de como se implementa, por ahora (eso esta mal)
	}

	private int power; 	//Power := cantidad de energia que aporta ¿cada vez o en total?
	private int times;	//Times := Numero de veces que el robot lo puede usar
}
