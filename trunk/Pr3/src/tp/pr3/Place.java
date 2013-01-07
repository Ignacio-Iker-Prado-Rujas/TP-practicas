package tp.pr3;


public class Place {
	//Constructor que inicializa nobre, si esta nave, descripcion y el contenedor de items
	public Place(String name, boolean isSpaceShip, String description) {
		this.name = name;
		this.isSpaceShip = isSpaceShip;
		this.description = description;
		this.itemContainer = new ItemContainer();
	}

	public boolean isSpaceship() {
		return this.isSpaceShip;
	}

	//Une el titulo con la descripcion y con la lista de objetos del container del lugar para mostrar un lugar por pantalla
	public String toString() {
		if (this.itemContainer.numberOfItems() == 0) {
			return this.name + LINE_SEPARATOR + this.description
					+ LINE_SEPARATOR
					+ "The place is empty. There are no objects to pick"
					+ LINE_SEPARATOR;
		} else {
			return this.name + LINE_SEPARATOR + this.description
					+ LINE_SEPARATOR + "The place contains these objects: "
					+ this.itemContainer.toString();
		}
	}
	
	//Elimina un objeto de un lugar.
	public Item pickItem(String id){
		return this.itemContainer.pickItem(id);
	} 
	
	// Añade un objeto a un lugar.
	public boolean addItem(Item item) {
		if (this.itemContainer.addItem(item))
			return true;
		else
			return false;
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	//Consta de un nombre, una descripcion y un booleano que indica si hay una nave en el
	private String name;
	private boolean isSpaceShip;
	private String description;
	private ItemContainer itemContainer;
}