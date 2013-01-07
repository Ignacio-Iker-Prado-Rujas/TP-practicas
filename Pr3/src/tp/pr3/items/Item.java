package tp.pr3.items;

import tp.pr3.Place;
import tp.pr3.RobotEngine;

public abstract class Item {
	//Constructor de un item a partir de un identificador y una descripcion
	public Item(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	//Metodo abstracto que indica si el item se puede usar
	//Al ser abstracto, la clase hija lo implementara
	public abstract boolean canBeUsed();
	
	//Metodo abstracto que intenta utilizar un item con un robot en un lugar dado
	//Devuelve true si se completo la accion y false en caso contrario
	//Al ser abstracto el metodo, estara implementado en la clase hija
	public abstract boolean use(RobotEngine r, Place p);
	
	public String toString(){
		return this.id + ": " + this.description;
	}
	
	//Metodo accedente para obtener el identificador del item
	public String getId() {
		return this.id;
	}
	
	//Atributos protected para que las clases hijas puedan acceder a ellos
	protected String id;
	protected String description;
}