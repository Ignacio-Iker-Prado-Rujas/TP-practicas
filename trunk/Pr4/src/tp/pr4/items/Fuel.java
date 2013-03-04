package tp.pr4.items;

import tp.pr4.NavigationModule;
import tp.pr4.RobotEngine;

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
	public boolean use(RobotEngine r, NavigationModule n) {
		if (this.canBeUsed()){
			r.addFuel(this.power);
			this.times--;
			return true;
		}
		else 
			return false;
	}
	
	//Devuelve una cadena con la energia y el numero de veces que se puede usar un item en concreto
	public String toString() {
		return super.toString() + "// power = " + this.power + ", times = " + this.times;
	}
	
	private int power; 	//Power := cantidad de energía que aporta cada vez
	private int times;	//Times := Numero de veces que el robot lo puede usar
}