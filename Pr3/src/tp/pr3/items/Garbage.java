package tp.pr3.items;

import tp.pr3.Place;
import tp.pr3.RobotEngine;

public class Garbage extends Item {
	//Constructor de la basura, que llama a la clase padre para completarse
	public Garbage(String id, String description, int recycledMaterial) {
		super(id, description);
		this.recycledMaterial = recycledMaterial;
		this.canBeUsed = true;
	}
	
	//True si aun puede usarse, false en caso contrario
	//La basura solo puede usarse una vez
	public boolean canBeUsed() {
		return this.canBeUsed;
	}
	
	//Recicla la basura, obteniendo material reciclado
	//Devuelve true si se pudo reciclar
	public boolean use(RobotEngine r, Place p) {
		if(this.canBeUsed()){
			r.addRecycledMaterial(this.recycledMaterial);
			System.out.println("   * My power is " + r.getFuel() );
			System.out.println("   * My recycled material is: " + r.getRecycledMaterial() );
			this.canBeUsed = false;
			return true;
		}
		else 
			return false;
	}
	
	public String toString(){
		return super.toString() + "// recycled material = " + this.recycledMaterial;
	}
	
	private boolean canBeUsed;		//Indica si ha sido usada o no la basura, inicialmente true
	private int recycledMaterial; 	//RecycledMaterial := Cantidad de material reciclado que el item genera
}