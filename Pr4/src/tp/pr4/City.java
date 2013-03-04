package tp.pr4;

public class City {
	//Constructor por defecto, necesario para el validador
	public City() {
		this.cityMap = new Street[10];
		this.numCalles = 0;
	}
	
	//Construye una ciudad como un array de calles de la clase Street
	public City(Street[] cityMap) {
		this.cityMap = cityMap;
		this.numCalles = cityMap.length;
	}
	
	//Busca una calle en la ciudad que empiece por el lugar dado y que tenga la direccion dada
	public Street lookForStreet(Place currentPlace, Direction currentHeading) {
		for(int i = 0; i < this.numCalles; i++){
			if(this.cityMap[i].comeOutFrom(currentPlace, currentHeading)){
				return cityMap[i];
			}
		}
		return null;
	}
	private boolean arrayCityLleno(){
		return (this.cityMap.length == this.numCalles);
	}
	
	//Crea un nuevo container copiando el anterior pero con el doble de tamaño
	private Street[] newCity(){
		Street[] newCity= new Street[2 * this.numCalles + 1];	//Se crea el nuevo container con el doble de capacidad
		for (int i = 0; i < this.numCalles; i++ ) 				//Se copian todos los elementos al nuevo
			newCity[i] = this.cityMap[i];
		return newCity;
	}
	
	public void addStreet(Street street){
		if  (arrayCityLleno()){
			this.cityMap = newCity();
		}
		this.cityMap[this.numCalles] = street;
		this.numCalles++;
	}
	private Street[] cityMap;
	private int numCalles;
}