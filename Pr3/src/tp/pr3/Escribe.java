package tp.pr3;

public class Escribe {

	public static final String LOOK_DIRECTION = "WALL·E is looking at direction <DIR>";
	public static final String PROMPT = "WALL·E > ";
	public static final String SAY = "WALL·E says: ";
	public static final String POWER = "   * My power is ";
	public static final String RECYCLED = "   * My recycled material is: ";
	public static final String NOT_UNDERSTAND = "I do not understand. Please repeat";
	public static final String PLACE_NOT_OBJECT = "Ooops, this place has not the object <id>";
	public static final String HAD_OBJECT = "I am stupid! I had already the object <id>";
	public static final String NOW_HAVE = "I am happy! Now I have  <id>";
	public static final String INV_EMPTY = "My inventory is empty";
	public static final String CARRYING_ITEMS = "I am carrying the following items";
	public static final String NOT_HAVE_THE_OBJECT = "I have not such object";
	public static final String PROBLEMS_USING_OBJECT = "I have problems using the object <id>";
	public static final String NO_MORE_OBJECT = "What a pity! I have no more <id> in my inventory";
	public static final String MOVING_DIRECTION = "Moving in direction ";
	public static final String OUT_OF_FUEL = "I run out of fuel. I cannot move. Shutting down...";
	public static final String IN_SPACESHIP = "I am at my space ship. Bye Bye";
	public static final String COMUNICATION_PROBLEMS = "I have communication problems. Bye Bye";
	
	public void lookingDirection(Direction direction) {
		System.out.println(LOOK_DIRECTION.replace("<DIR>", direction.toString()));
	}
	public void currentPlace(Place place){
		System.out.println(place.toString());
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
