package tp.pr2;

public enum Direction {
	EAST, NORTH, SOUTH, UNKNOWN, WEST;

	
	// Devuelve la direcci�n opuesta de la recibida.

	public Direction oppositeDirection() {
		switch (this) {
			case NORTH: return Direction.SOUTH; 
			
			case SOUTH: return Direction.NORTH;
			
			case EAST: return Direction.WEST;
			
			case WEST: return Direction.EAST;
				
			default: return Direction.UNKNOWN;
		}
	}

	// Devuelve le direcci�n a la derecha de la que est� mirando.

	public Direction turnRight() {
		switch (this) {
			case NORTH: return Direction.EAST; 
			
			case EAST: return Direction.SOUTH;
			
			case SOUTH: return Direction.WEST;
			
			case WEST: return Direction.NORTH;
				
			default: return Direction.UNKNOWN;
		}
	}
	
	// Devuelve la de la izquierda.

	public Direction turnLeft() {
		switch (this) {
			case NORTH: return Direction.WEST; 
			
			case WEST: return Direction.SOUTH;
			
			case SOUTH: return Direction.EAST;
			
			case EAST: return Direction.NORTH;
				
			default: return Direction.UNKNOWN;
		}
	}

}