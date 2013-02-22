package tp.pr3;

public class Escribe {//Interpreter, robotEngine, turnInstruction.

	
	public static void lookingDirection(Direction direction) {
		System.out.println(LOOK_DIRECTION.replace("<DIR>", direction.toString()));
	}
	public static void currentPlace(Place place){
		System.out.println(place.toString());
	}
	
	public static void prompt() {
		System.out.print(PROMPT);
	}
	public static void validInstructions(String validInstructions){
		System.out.println(VALID_INSTRUCTIONS);
		System.out.println(validInstructions);
	}
	public static String say(String message) {
		System.out.println(SAY + message);
		return SAY + message;	// Para utilizarlo en las excepciones.
	}
	
	public static void actualizarEstado(int fuelActual, int recycledActual ){
		if (fuelActual <= 0) System.out.println(POWER + "0");
		else System.out.println(POWER + fuelActual );
		System.out.println(RECYCLED + recycledActual);
	}
	public static void llamadaIncorrecta() {
		System.err.println("Bad params.");
		System.err.println("Usage: java tp.pr3.Main <mapfile>" + LINE_SEPARATOR);
		System.err.println("<mapfile> : file with the description of the city.");
	}
	public static void mapaIncorrecto(String message){
		System.err.println(message);
	}

	private static final String NO_EXISTE_FICHERO = "Error reading the map file:" + 
													" <fichero> (No existe el fichero o el directorio)";
	
	public static void noExisteFichero(String fichero){
		System.err.println(NO_EXISTE_FICHERO.replace("<fichero>", fichero));
	}
	public static void mostrar(String message){
		System.out.println(message);
	}
	public static final String LOOK_DIRECTION = "WALL·E is looking at direction <DIR>";
	public static final String PROMPT = "WALL·E> ";
	public static final String SAY = "WALL·E says: ";
	public static final String POWER = "      * My power is ";
	public static final String RECYCLED = "      * My reclycled material is ";
	public static final String NOT_UNDERSTAND = "I do not understand. Please repeat";
	public static final String PLACE_NOT_OBJECT = "Ooops, this place has not the object <id>";
	public static final String HAD_OBJECT = "I am stupid! I had already the object <id>";
	public static final String NOW_HAVE = "I am happy! Now I have <id>";
	public static final String INV_EMPTY = "My inventory is empty";
	public static final String CARRYING_ITEMS = "I am carrying the following items";
	public static final String NOT_HAVE_THE_OBJECT = "You do not have any <id>.";
	public static final String PROBLEMS_USING_OBJECT = "I have problems using the object <id>";
	public static final String NO_MORE_OBJECT = "What a pity! I have no more <id> in my inventory";
	public static final String MOVING_DIRECTION = "Moving in direction ";
	public static final String OUT_OF_FUEL = "I run out of fuel. I cannot move. Shutting down...";
	public static final String IN_SPACESHIP = "I am at my space ship. Bye Bye";
	public static final String COMUNICATION_PROBLEMS = "I have communication problems. Bye Bye";
	public static final String VALID_INSTRUCTIONS = "The valid instructions for WALL-E are:";
	public static final String OBJECT_DROPPED = "Great! I have dropped <id>";
	public static final String THE_OBJECT_WAS_IN_PLACE = "I am stupid! The object <id> was in current place";
	/*Streets*/
	public static final String THERE_IS_NO_STREET = " There is no street in direction <direction>";
	public static final String STREET_CLOSED = "Arrggg, there is a street but it is closed!";
	/*********/
	/*Place*/
	public static final String PLACE_EMPTY = "The place is empty. There are no objects to pick";
	public static final String PLACE_CONTAINS = "The place contains these objects:";
	/*********/
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

}
