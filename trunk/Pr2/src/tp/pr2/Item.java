package tp.pr2;

public abstract class Item {
	//Constructor de un item a partir de un identificador y una descripcion
	public Item(String id, String description) {
		this.id = id;
		this.description = description;
	}
	
	//
	public abstract boolean canBeUsed() {
		
	}
	
	//
	public abstract boolean use(RobotEngine r, Place p) {
		
	}
	
	//Metodo accedente para obtener el identificador del item
	public String getId() {
		return this.id;
	}
	
	
	private String id;
	private String description;
}
