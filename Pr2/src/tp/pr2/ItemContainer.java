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
		return this.arrayItem.length == this.numItems;
	}
	//Crea un nuevo container copiando el anterior pero con el doble de tamaño.
	private Item[] newItemContainer(){
		Item newContainer[] = new Item[2*this.numItems];	//Se crea el nuevo container con el doble de capacidad.
		//Se copian todos los elementos al nuevo.
		for (int i = 0; i < this.numItems; i++ )
			newContainer[i] = this.arrayItem[i];
		return newContainer;
	}
	private boolean estaElItem(String id, int pos){
		
	}
	//Añade un item al container, ordenado por id, siempre que no haya otro con el mismo nombre.
	//Se devuelve true sii se pudo añadir
	public boolean addItem(Item item) {
		if ( itemContainerLleno()) this.arrayItem = newItemContainer();//Si está lleno, crea uno nuevo más grande.
		int pos = 0;
		if( estaElItem(item.id, pos)) return false;	//Si ya existe ese item en el container no se inserta
		else{
			for (int i = this.numItems; i > pos+1 ; i--)
				this.arrayItem[i]=this.arrayItem[i-1];
			this.arrayItem[pos] = item;
			this.numItems++;
			return true;		//Desplaza, inserta y actualiza el número de items.
		}
		
		for(int i = 0; i < this.numItems; i++)
			if(this.arrayItem[i].id.equals(item.id))
				return false;
		this.arrayItem[this.arrayItem.length] = item;
		return true;
	}
	
	//Metodo accedente: devuelve un item concreto dado su id, si esta en el contenedor
	public Item getItem(String id) {
	for(int i = 0; i < this.arrayItem.length; i++)
		if(this.arrayItem[i].id.equals(id))
			return arrayItem[i];	
	return null;
	}
	
	//Devuelve un item del contenedor si esta, borrandolo de este
	/*public Item pickItem(String id) {
		int i = 0;
		boolean encontrado = false;
		while(i != this.arrayItem.length && !encontrado) {
			if(this.arrayItem[i].id.equals(id))
				encontrado = true;
			i++;
		}
		
		if (!encontrado)
			return null;
		else {
			//Item item = arrayItem[i].clone();
			while(i != this.arrayItem.length) {
				arrayItem[i] = arrayItem[i + 1];
				i++;
			}
			//return item;	
		}
	}*/
	
	//
	public String toString() {
		String items = null;
		for (int i = 0; i < numItems; i++){
			items += this.arrayItem[i].id + LINE_SEPARATOR;
		}
		//TODO: generar una cadena en Strings items ORDENADA por id alfabeticamente y una en cada linea (ver ejemplos)
		return items;
	}
	private int numItems;
	private Item[] arrayItem;
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");
}
