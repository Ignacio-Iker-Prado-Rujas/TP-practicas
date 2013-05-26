package tp.pr5.items;


import java.util.Arrays;
import java.util.List;

import tp.pr5.Observable;


public class ItemContainer extends Observable<InventoryObserver>{
	//Constructor del contenedor de items (vacio, sin items)
	public ItemContainer() {
		this.arrayItem = new Item[10];
		this.numItems = 0;
	}
	
	//Devuelve el numero de items de este contenedor de items
	public int numberOfItems() {
		return this.numItems;
	}
	
	//Devuelve true si el container está lleno
	private boolean itemContainerLleno() {
		return (this.arrayItem.length == this.numItems);
	}
	
	//Crea un nuevo container copiando el anterior pero con el doble de tamaño
	private Item[] newItemContainer() {
		Item newContainer[] = new Item[2 * this.numItems + 1];	//Se crea el nuevo container con el doble de capacidad
		for (int i = 0; i < this.numItems; i++ ) 				//Se copian todos los elementos al nuevo
			newContainer[i] = this.arrayItem[i];
		return newContainer;
	}
	
	//True si está el item pedido
	public boolean containsItem(String id){
		return (estaElItem(id) >= 0);
	}
	
	//Devuelve true si está el id buscado, y la posición en la que está
	//Si el id no está, devuelve false y la posición donde habría que insertarlo
	private int estaElItem(String id) {
		boolean encontrado = false;
		int i = 0;
		while ((i < this.numItems) && !encontrado) { //Aqui buscamos la posicion que le corresponderia al item
		    if (id.compareToIgnoreCase(this.arrayItem[i].id) <= 0) 
		    	encontrado = true;
		    else 
		    	i++;
		}
		if ((i < this.numItems) && id.equalsIgnoreCase(this.arrayItem[i].id))
			return i; 								//Aqui comprueba que la posicion esté dentro del array, y si el item no esta ya en el container
		else 
			return -i - 1;
	}
	
	/*	
	 	VERSION CON LA BUSQUEDA BINARIA:
	 	
	 	private boolean estaElItem(String id, int pos) {
		int ini = 0, fin = this.numItems - 1, mitad = 0;
		while (ini <= fin) {
		    mitad = (ini + fin) / 2; // División entera
		    if ((mitad < this.numItems) && (id.compareTo(this.arrayItem[mitad].id) < 0)) 
		    	fin = mitad - 1;
		    else 
		    	ini = mitad + 1;
		}
		if ((mitad < this.numItems) && id.equalsIgnoreCase(this.arrayItem[mitad].id))
			return mitad;
		else	
			return -mitad - 1;
	}
	
	*/
		
	//Añade un item al container, ordenado por id, siempre que no haya otro con el mismo nombre
	//Se devuelve true sii se pudo añadir
	
	public boolean addItem(Item item) {
		if (itemContainerLleno()) 	//Si está lleno, crea uno nuevo más grande
			this.arrayItem = newItemContainer();
		int pos = estaElItem(item.id); 
		if (pos >= 0)				//Si ya existe ese item en el container no se inserta
			return false;
		else {
			pos = -pos - 1;			//Si el item no esta en el container, pos es negativo y vale -i-1 (ver el metodo estaElItem())
			for (int i = this.numItems; i > pos ; i--)
				this.arrayItem[i]=this.arrayItem[i - 1];
			this.arrayItem[pos] = item;
			this.numItems++;
			List<Item> listaItems = Arrays.asList(arrayItem);
			for(InventoryObserver invOb : arrayObservers){
				invOb.inventoryChange(listaItems);
			}
			return true;			//Desplaza, inserta y actualiza el número de items.
		}
	}
	
	//Metodo accedente que devuelve un item concreto dado su id, si esta en el contenedor
	public Item getItem(String id) {
		int pos = estaElItem(id);
		if (pos < 0)
			return null;
		else
			return this.arrayItem[pos];
	}

	//Devuelve un item del contenedor si esta, borrandolo de este
	
	public Item pickItem(String id) {
		int pos = estaElItem(id);
		if (pos < 0)
			return null;
		else {
			Item eliminado = this.arrayItem[pos];
			arrayItem[pos] = null;	//Eliminamos el item del array
			for (int i = pos; i < this.numItems - 1; i++)
				this.arrayItem[i] = this.arrayItem[i + 1];
			arrayItem[numItems-1] = null; //Borramos el último
			this.numItems--;
			List<Item> listaItems = Arrays.asList(arrayItem); 
			for(InventoryObserver invOb : arrayObservers){
				invOb.inventoryChange(listaItems);
			}
			return eliminado;
		}
	}
	
	//Genera una cadena con los id de los item del item container
	//Ya viene ordenada alfabeticamente porque en el array estan ordenados
	public String toString() {
		String items = "";
		for(int i = 0; i < numItems; i++)
			items += LINE_SEPARATOR + "   " + this.arrayItem[i].id;
		items += LINE_SEPARATOR;
		return items;
	}
	
	// Devuelve un array con los nombres de los items en el container;
	public String[] listaItems(){
		String[] arrayItems = new String[numItems];
		for ( int i = 0; i < numItems; i ++){
			arrayItems[i] = arrayItem[i].id;
		}
		return arrayItems;
	}
	public void useItem(Item item){
		if (!item.canBeUsed()){
			for (InventoryObserver invOb: arrayObservers) // notificamos que se ha gastado el item
				invOb.itemEmpty(item.id);
			pickItem(item.id);	
		}
	}

	// Para hacer SCAN del itemContainer
	public void requestScanCollection() { 
		for (InventoryObserver invOb: arrayObservers) 
			invOb.inventoryScanned(this.toString());
	}
 
	// Para hacer SCAN de un item
	public void requestScanItem(String id) {
		for (InventoryObserver invOb: arrayObservers) 
			invOb.itemScanned(getItem(id).toString());
	}

	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	//El itemContainer lleva un array de items y un entero que indica cuantos hay
	private int numItems;
	private Item[] arrayItem;
}