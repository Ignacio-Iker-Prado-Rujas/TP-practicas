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
		if (this.canBeUsed()){
			r.addFuel(this.power);
			System.out.println("   * My power is " + r.getFuel() );
			System.out.println("   * My recycled material is: " + r.getRecycledMaterial() );
			this.times--;
			return true;
		}
		else return false;
		//TODO: Ni idea de como se implementa, por ahora (eso esta mal)
	}
	public String toString(){
		return super.toString() + "// power = " + this.power + " , times = " + this.times;
	}
	
	private int power; 	//Power := cantidad de energía que aporta cada vez.
	private int times;	//Times := Numero de veces que el robot lo puede usar
}
