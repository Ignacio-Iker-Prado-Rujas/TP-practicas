package tp.pr4;

public enum Rotation {
	LEFT, RIGHT, UNKNOWN;
	
	public Rotation oppositeRotation(){
		if(Rotation.LEFT.equals(this))return Rotation.RIGHT;
		else if(Rotation.RIGHT.equals(this)) return Rotation.LEFT;
		else return Rotation.UNKNOWN;
	}
}
//Giros permitidos de WALL·E, UNKNOWN si es desconocido