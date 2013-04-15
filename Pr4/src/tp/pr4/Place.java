package tp.pr4;

import tp.pr4.items.Item;
import tp.pr4.items.ItemContainer;


public class Place {
	//Constructor que inicializa nobre, si esta nave, descripcion y el contenedor de items
	public Place(String name, boolean isSpaceShip, String description) {
		this.name = name;
		this.isSpaceShip = isSpaceShip;
		this.description = description;
		this.itemContainer = new ItemContainer();
	}

	public boolean dropItem(Item item){
		return this.itemContainer.addItem(item);
	}
	
	public boolean isSpaceship() {
		return this.isSpaceShip;
	}

	//Une el titulo con la descripcion y con la lista de objetos del container del lugar para mostrar un lugar por pantalla
	public String toString() {
		if (this.itemContainer.numberOfItems() == 0) {
			return this.name + LINE_SEPARATOR + this.description
					+ LINE_SEPARATOR + EscribeConsola.PLACE_EMPTY;
		} else {
			return this.name + LINE_SEPARATOR + this.description
					+ LINE_SEPARATOR + EscribeConsola.PLACE_CONTAINS
					+ this.itemContainer.toString();
		}
	}
	
	public boolean existItem(String id){
		return this.itemContainer.containsItem(id);
	}
	
	//Elimina un objeto de un lugar.
	public Item pickItem(String id){
		return this.itemContainer.pickItem(id);
	} 
	
	// Añade un objeto a un lugar.
	public boolean addItem(Item item) {
		return this.itemContainer.addItem(item);
	}

	//Método que devuelve el nombre del lugar
	public String getName() {
		return this.name;
	}
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	//Consta de un nombre, una descripcion y un booleano que indica si hay una nave en el
	private String name;
	private boolean isSpaceShip;
	private String description;
	private ItemContainer itemContainer;
}