package tp.pr4.items;

import tp.pr4.NavigationModule;
import tp.pr4.RobotEngine;

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
	public boolean use(RobotEngine r, NavigationModule n) {
		if (this.canBeUsed()) {
			r.addRecycledMaterial(this.recycledMaterial);
			this.canBeUsed = false;
			return true;
		} else
			return false;
	}
	
	public void desUse(RobotEngine r, NavigationModule n){
		r.addRecycledMaterial(-this.recycledMaterial);
		this.canBeUsed = true;
	}
	
	public String toString(){
		return super.toString() + "// recycled material = " + this.recycledMaterial;
	}
	
	private boolean canBeUsed;		//Indica si ha sido usada o no la basura, inicialmente true
	private int recycledMaterial; 	//RecycledMaterial := Cantidad de material reciclado que el item genera
}