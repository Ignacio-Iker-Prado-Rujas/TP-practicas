package tp.pr1;

import java.util.Scanner;

	// RobotEngine: Consta del lugar actual del robot, la dirección en la que mira, y el mapa por el que se mueve (array de calles).

public class RobotEngine {

	public RobotEngine(Place initialPlace, Direction direction, Street[] cityMap) {
		this.initialPlace = initialPlace;
		this.direction = direction;
		this.cityMap = cityMap;
	}

	public void startEngine() {
		System.out.println(initialPlace.toString());
		lookingDirection(this.direction);
		Scanner sc = new Scanner(System.in);
		Instruction instruction = new Instruction();
		Interpreter interpreter = new Interpreter();
		boolean quit = false;
		while (!initialPlace.isSpaceship() && !quit) {
			prompt();	// muestra WALL·E>

			// Lee una instrucción, y se la pasa al interprete que genera la corespondiente instruccón.
			instruction = interpreter.generateInstruction(sc.nextLine());	

			if (!instruction.isValid())	// Comprueba si la instrucción introducida la reconoc walle. Si no, muestra el mensaje corespondiente.
				say("I do not understand. Please repeat");
			else {
				switch(instruction.getAction()){

					case HELP: System.out.println(interpreter.interpreterHelp());break;	// Muestra las instrucciones que reconoce walle.
					
					case QUIT: quit = true; break;	// Booleano de terminar
					
					case MOVE: 
					{	//	Si se mueve, actualiza la información. Si no, muestra el mensaje de que no hay calle.

						if (moveWalle()) {
							say("Moving in direction " + direction.toString());
							System.out.println(initialPlace.toString());
							lookingDirection(this.direction);
						} else
							System.out.println(" There is no street in direction "
									+ direction.toString());
						break;
					}
					
					default:  //case TURN: Como la instrucción es correcta, o gira a la derecha o a la izquierda.
					{	
						if (instruction.getRotation().equals(Rotation.RIGHT))
							direction = direction.turnRight();
						else
							direction = direction.turnLeft();
						lookingDirection(this.direction);	// Después de haber girado, actualiza la dirección en la que está mirando.
					}
				}
			}
		}
		sc.close();	// Cierra el escáner

		if (!quit)	// Si no se ha elegido la opción quit, es que se ha llegado a la nave. Se muestra el mensaje correspondiente.
			say("I am at my spaceship. Shutting down... Bye bye");
		else
			say("I have communication problems. Bye bye");	// Se ha elegido la opción quit, luego se m
	}
	/* comprueba si desde el lugar en el que está wall·e, hacia donde está mirando hay una calle. 
	Para ello, recorre el array de calles, comprobando con el método comeOutFrom.
	En caso de que walle esté mirando hacia una calle, le avanza hasta el lugar que hay al otro lado de la calle con el nextPlace y 
	devuelve true. Si no ha encontrado calle devuelve false. */
	
	private boolean moveWalle(){
		int pos = -1;
		for (int i = 0; i < cityMap.length; i++){
			if (cityMap[i].comeOutFrom(initialPlace, direction))
				pos = i;
		}
		if (pos >= 0) {
			initialPlace = cityMap[pos].nextPlace(initialPlace);
			return true;
		}
		else return false;
	}
	
	// Métodos que muestran por consola.

	private void say(String message) {
		System.out.println("WALL·E says: " + message);
	}
	private void prompt() {
		System.out.print(LINE_SEPARATOR + "WALL·E > ");
	}
	private void lookingDirection(Direction direction) {
		System.out.println("WALL·E is looking at direction " + direction.toString());
	}
	
	private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	private Place initialPlace;
	private Direction direction;
	private Street[] cityMap;
}
