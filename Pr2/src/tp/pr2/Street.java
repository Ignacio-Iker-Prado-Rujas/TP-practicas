package tp.pr2;

	
public class Street {
	public Street(Place source, Direction direction, Place target) {
		this.source = source;
		this.direction = direction;
		this.target = target;
		this.isOpen = true;
		this.code = null;
		
	}
	public Street(Place source, Direction direction, Place target, boolean isOpen, String code){
		this.source = source;
		this.direction = direction;
		this.target = target;
		this.isOpen = isOpen;
		this.code = code;
	}

	// Devuelve true si la calle, dado un lugar y una direccion, conecta con otro lugar. False en caso contrario.
	public boolean comeOutFrom(Place place, Direction whichDirection) {
		if (this.source.equals(place) && this.direction.equals(whichDirection))
			return true;
		else if (this.target.equals(place) && this.direction.equals(whichDirection.oppositeDirection()))
			return true;
		else
			return false;
	}
	
	/* Dado un lugar, devuelve el que esta al otro lado de la calle.
	 Si el lugar no coincide con source ni target, devuelve 
	null (Se aconseja combinar con el uso de comeOutFrom para que esto no pase).*/
	public Place nextPlace(Place whereAmI) {
		if (this.source.equals(whereAmI))
			return this.target;
		else if (this.target.equals(whereAmI))
			return this.source;
		else
			return null;
	}

	//Método para obtener el lugar de origen de una calle 
	public Place getSource() {
		return this.source;
	}
	
	//Método para obtener la direccion que une source y target en una calle 
	public Direction getDirection() {
			return this.direction;
	}
	
	//Compara el código de la tarjeta con el de la calle.
	public boolean close(CodeCard card){
		if (this.code.equals(card.getCode())) return true;
		else return false;
	}
	
	// Calle: Consta de un lugar de origen, un lugar destino, y la direccion que une source con target.
	private Place source;
	private Place target;
	private Direction direction;
	private boolean isOpen;
	private String code;

}
