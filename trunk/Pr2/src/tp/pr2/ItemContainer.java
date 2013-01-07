package tp.pr2;

public class ItemContainer {
	//Constructor del contenedor de items
	public ItemContainer() {
		this.arrayItem = new Item[10];
		this.numItems = 0;
	}
	
	//Devuelve el numero de items de este contenedor de items
	public int numberOfItems() {
		return this.numItems;
	}
	
	//Devuelve true si el container está lleno.
	private boolean itemContainerLleno(){
		return (this.arrayItem.length == this.numItems);
	}
	//Crea un nuevo container copiando el anterior pero con el doble de tamaño.
	private Item[] newItemContainer(){
		Item newContainer[] = new Item[2*this.numItems+1];	//Se crea el nuevo container con el doble de capacidad.
		//Se copian todos los elementos al nuevo.
		for (int i = 0; i < this.numItems; i++ ){
			newContainer[i] = this.arrayItem[i];
		}
		return newContainer;
	}
	
	//Devuelve true si está el id buscado, y la posción en la que está.
	//Si el id no está, devuelve false y la posición donde habría que insertarlo.
	private int estaElItem(String id){
		boolean encontrado = false;
		int i = 0;
		while ((i < this.numItems)&&!encontrado) {
		    if (id.compareToIgnoreCase(this.arrayItem[i].id) <= 0) encontrado = true;
		    else i++;
		}
		if ((i < this.numItems) && id.equalsIgnoreCase(this.arrayItem[i].id))return i; //Comprueba que pos esté dentro del array, y si está el  id
		else	return -i-1;
	}
	
	/*private boolean estaElItem(String id, int pos){
		int ini = 0, fin = this.numItems - 1, mitad = 0;
		while ((ini <= fin)) {
		    mitad = (ini + fin) / 2; // División entera
		    if ((mitad < this.numItems)&&(id.compareTo(this.arrayItem[mitad].id)<0)) fin = mitad - 1;
		    else ini = mitad + 1;
		}
		pos = mitad;
		if ((pos < this.numItems) && this.arrayItem[pos].id.equals(id))return true; //Comprueba que pos esté dentro del array, y si está el  id
		else	return false;
	}*/
		
	//Añade un item al container, ordenado por id, siempre que no haya otro con el mismo nombre.
	//Se devuelve true sii se pudo añadir
	public boolean addItem(Item item) {
		if ( itemContainerLleno()) this.arrayItem = newItemContainer();//Si está lleno, crea uno nuevo más grande.
		int pos = estaElItem(item.id); 
		if (pos>=0)	return false;//Si ya existe ese item en el container no se inserta
		else{
			pos = -pos-1;
			for (int i = this.numItems; i > pos ; i--)
				this.arrayItem[i]=this.arrayItem[i-1];
			this.arrayItem[pos] = item;
			this.numItems++;
			return true;		//Desplaza, inserta y actualiza el número de items.
		}
	}
	
	//Metodo accedente: devuelve un item concreto dado su id, si esta en el contenedor
	public Item getItem(String id) {
		int pos = estaElItem(id);
		if (pos<0) return null;
		else	return this.arrayItem[pos];
	}
	
	//Devuelve un item del contenedor si esta, borrandolo de este
	public Item pickItem(String id){
		int pos = estaElItem(id);
		if (pos<0) return null;
		else{
			Item eliminado = this.arrayItem[pos];
			for ( int i = pos; i < this.numItems-1; i++){
				this.arrayItem[i] = this.arrayItem[i+1];
			}
			this.numItems--;
			return eliminado;
		}
	}
	

	public String toString() {
		String items = "";
		for (int i = 0; i < numItems; i++){
			items += LINE_SEPARATOR + "   " + this.arrayItem[i].id;
		}
		items += LINE_SEPARATOR;
		//TODO: generar una cadena en Strings items ORDENADA por id alfabeticamente y una en cada linea (ver ejemplos)
		return items;
	}
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	//El itemContainer lleva un array de items y un entero que indica cuantos hay
	private int numItems;
	private Item[] arrayItem;
}