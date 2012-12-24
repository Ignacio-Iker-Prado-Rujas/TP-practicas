package tp.pr2;

public class City {
	
	//Constructor por defecto, necesario para el validador
	public City() {
		
	}
	
	//Construye una ciudad como un array de calles de la clase Street
	public City(Street[] cityMap) {
		this.cityMap = cityMap;
	}
	
	//Busca una calle en la ciudad que empiece por el lugar dado y que tenga la direccion dada
	public Street lookForStreet(Place currentPlace, Direction currentHeading) {
		for(int i = 0; i < this.cityMap.length; i++)
			if(this.cityMap[i].getSource().equals(currentPlace) && this.cityMap[i].getDirection().equals(currentHeading))
				return cityMap[i];
		return null;
	}
	
	private Street[] cityMap;
}
