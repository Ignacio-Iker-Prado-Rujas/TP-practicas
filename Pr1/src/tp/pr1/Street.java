package tp.pr1;

	// Calle: Consta de un lugar de origen, un lugar destino, y la dirección que une source con target.

public class Street {
	public Street(Place source, Direction direction, Place target) {
		this.source = source;
		this.direction = direction;
		this.target = target;
	}

	// Devuelve true si la calle, dado un lugar y una dirección, conecta con otro lugar. False en caso contrario.

	public boolean comeOutFrom(Place place, Direction whichDirection) {
		if (this.source.equals(place) && this.direction.equals(whichDirection))
			return true;
		else if (this.target.equals(place) && this.direction.equals(whichDirection.oppositeDirection()))
			return true;
		else
			return false;
	}
	
	/* Dado un lugar, devuelve el que está al otro lado de la calle.
	 Si no coincde el lugar con source ni target, devuelve 
	null( Se aconseja combinar con el uso de comeOutFrom para que esto no pase).*/

	public Place nextPlace(Place whereAmI) {

		if (this.source.equals(whereAmI))
			return this.target;
		else if (this.target.equals(whereAmI))
			return this.source;
		else
			return null;
	}

	private Place source;
	private Place target;
	private Direction direction;

}
