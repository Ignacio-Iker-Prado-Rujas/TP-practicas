package tp.pr2;


public class Place {
	
	public Place(String name, boolean isSpaceShip, String description) {
		this.name = name;
		this.isSpaceShip = isSpaceShip;
		this.description = description;
		this.itemContainer = new ItemContainer();
	}

	public boolean isSpaceship() {
		return this.isSpaceShip;
	}

	//Une el titulo con la descripcion para mostrar un lugar por pantalla.
	public String toString() {
		return this.name + LINE_SEPARATOR + this.description + this.itemContainer.toString(); // TODO falta añadir los objetos del lugar
	}
	
	public Item pickItem(String id){
		return this.itemContainer.pickItem(id);
	}
	
	public boolean addItem(Item item){
		if (this.itemContainer.addItem(item)) return true;
		else return false;
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	//Consta de un nombre, una descripcion y un booleano que indica si hay una nave en el
	private String name;
	private boolean isSpaceShip;
	private String description;
	private ItemContainer itemContainer;	//TODO: No estoy seguro 100% pero creo que si hay que poner este atributo
}
