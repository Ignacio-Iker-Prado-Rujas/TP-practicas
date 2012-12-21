package tp.pr2;

public class City {
	
	//Constructor por defecto, necesario para el test
	public City() {
		
	}
	
	//Constructor de una ciudad a partir de un array de Streets
	public City(Street[] cityMap) {
		this.cityMap = cityMap;
	}
	
	//Busca una calle que parta del lugar dado y en la direccion dada
	public Street lookForStreet(Place currentPlace, Direction currentHeading) {
		for (int i = 0; i < cityMap.length; i++) {
			
		}
		return null;
	}
	
	private Street[] cityMap;
}
