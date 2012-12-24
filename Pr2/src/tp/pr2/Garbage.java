package tp.pr2;

public class Garbage extends Item {
	//Constructor de la basura, que llama a la clase padre para completarse
	Garbage(String id, String description, int recycledMaterial) {
		super(id, description);
		this.recycledMaterial = recycledMaterial;
	}
	
	//True si aun puede usarse, false en caso contrario
	//La basura solo puede usarse una vez
	public boolean canBeUsed() {
		return false;
		//TODO: Ni idea de como se implementa, por ahora (eso esta mal)
	}
	
	//Recicla la basura, obteniendo material reciclado
	//Devuelve true si se pudo reciclar
	public boolean use(RobotEngine r, Place p) {
		return true;
		//TODO: Ni idea de como se implementa, por ahora (eso esta mal)
	}
	
	public int recycledMaterial; 	//RecycledMaterial := Cantidad de material reciclado que el item genera
}
