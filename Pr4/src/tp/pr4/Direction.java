package tp.pr4;

public enum Direction {
	EAST, NORTH, SOUTH, UNKNOWN, WEST;
	
	// Devuelve la dirección opuesta de la recibida
	public Direction oppositeDirection() {
		switch (this) {
			case NORTH: return Direction.SOUTH; 
			
			case SOUTH: return Direction.NORTH;
			
			case EAST: return Direction.WEST;
			
			case WEST: return Direction.EAST;
				
			default: return Direction.UNKNOWN;
		}
	}

	// Devuelve la dirección a la derecha de la que esté mirando
	public Direction turnRight() {
		switch (this) {
			case NORTH: return Direction.EAST; 
			
			case EAST: return Direction.SOUTH;
			
			case SOUTH: return Direction.WEST;
			
			case WEST: return Direction.NORTH;
				
			default: return Direction.UNKNOWN;
		}
	}
	
	// Devuelve la dirección a la izquierda de la que esté mirando
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