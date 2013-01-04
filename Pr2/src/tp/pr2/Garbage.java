package tp.pr2;

public class Garbage extends Item {
	//Constructor de la basura, que llama a la clase padre para completarse
	public Garbage(String id, String description, int recycledMaterial) {
		super(id, description);
		this.recycledMaterial = recycledMaterial;
		this.used = false; //Indica si ha sido usada o no la basura. Al principio no ha sido usada.
	}
	
	//True si aun puede usarse, false en caso contrario
	//La basura solo puede usarse una vez
	public boolean canBeUsed() {
		return !this.used;
		//TODO: Ni idea de como se implementa, por ahora (eso esta mal)
	}
	
	//Recicla la basura, obteniendo material reciclado
	//Devuelve true si se pudo reciclar
	public boolean use(RobotEngine r, Place p) {
		if(this.canBeUsed()){
			r.addRecycledMaterial(this.recycledMaterial);
			return true;
		}
		else return false;
		//TODO: Ni idea de como se implementa, por ahora (eso esta mal)
	}
	
	public String toString(){
		return super.toString() + "// recycled material = " + this.recycledMaterial;
	}
	public boolean used;
	public int recycledMaterial; 	//RecycledMaterial := Cantidad de material reciclado que el item genera
}
