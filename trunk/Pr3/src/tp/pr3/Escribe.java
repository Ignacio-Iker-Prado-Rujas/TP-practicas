package tp.pr3;

public class Escribe {

	public static final String LOOK_DIRECTION = "WALL·E is looking at direction <DIR>";
	public static final String PROMPT = "WALL·E > ";
	public static final String SAY = "WALL·E says: ";
	public static final String POWER = "   * My power is ";
	public static final String RECYCLED = "   * My recycled material is: ";
	
	public void lookingDirection(Direction direction) {
		System.out.println(LOOK_DIRECTION.replace("<DIR>", direction.toString()));
	}
	
	public void prompt() {
		System.out.print(PROMPT);
	}
	
	public void say(String message) {
		System.out.println(SAY + message);
	}
	
	public void actualizarEstado(int fuelActual, int recycledActual ){
		if (fuelActual <= 0) System.out.println(POWER + "0");
		else System.out.println(POWER + fuelActual );
		System.out.println(RECYCLED + recycledActual);
	}
}
