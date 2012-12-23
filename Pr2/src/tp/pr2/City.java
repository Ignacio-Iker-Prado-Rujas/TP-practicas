package tp.pr2;

public class City {
	
	//Constructor por defecto, necesario para el validador
	public City() {
		
	}
	
	//Construye una ciudad como un array de calles de la clase Street
	public City(Street[] cityMap) {
		this.cityMap = cityMap;
	}
	
	public Street lookForStreet(Place currentPlace, Direction currentHeading) {
		int i = 0;
		while(i < n) {
			if(cityMap[i].equals())
				return cityMap[i];
			i++;
		}
		return null;
	}
	
	private Street[] cityMap;
}
