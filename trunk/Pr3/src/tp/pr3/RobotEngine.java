package tp.pr3;

import java.util.Scanner;

import tp.pr3.instructions.Instruction;
import tp.pr3.items.Item;
import tp.pr3.items.ItemContainer;

public class RobotEngine {
	//Constructor a partir del mapa de la ciudad, el lugar inicial y la direccion la que mira el robot
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		this.initialPlace = initialPlace;
		this.direction = direction;
		this.cityMap = cityMap;
		this.fuel = 50;
		this.itemContainer = new ItemContainer();
		this.recycledMaterial = 0;
	}
	
	//Incrementa o decrementa la cantidad de fuel que tiene wall e. Puede ser negativo el fuel.
	public void addFuel(int fuel) {
		this.fuel += fuel;
	}

	//Incrementa la cantidad de material reciclado
	public void addRecycledMaterial(int weight){
		this.recycledMaterial += weight;
	}
	
	//Para los tests
	public int getFuel(){
		return this.fuel;
	}
	
	//Para los tests
	public int getRecycledMaterial(){
		return this.recycledMaterial;
	}
	
	//Duevuelve la calle hacia la que está mirando WALL·E, null si no hay calle
	public Street getHeadingStreet(){
		return this.cityMap.lookForStreet(this.initialPlace, this.direction);
	}
	
	//Inicia el movimiento de WALL·E
	public void startEngine() {
		System.out.println(initialPlace.toString());
		myFuelIs();
		myRecicledIs();
		lookingDirection(this.direction);
		Scanner sc = new Scanner(System.in);
		Instruction instruction = new Instruction();
		boolean quit = false;
		while (!initialPlace.isSpaceship() && !quit && (this.fuel > 0)) {
			prompt();	//Muestra por consola: WALL·E>

			// Lee una instruccion, y se la pasa al interprete que genera la corespondiente instruccion
			instruction = Interpreter.generateInstruction(sc.nextLine());

			if (!instruction.isValid())	// Comprueba si WALL·E reconoce la instrucción introducida, y si no, muestra el mensaje corespondiente
				say("I do not understand. Please repeat");
			else {
				switch(instruction.getAction()) {
				
					case PICK: 
					{
						String id = instruction.getId();
						Item item = this.initialPlace.pickItem(id);
						if(item == null) 
							say("Ooops, this place has not the object <id>".replace("<id>", id));
						else if(!this.itemContainer.addItem(item)) 
							say("I am stupid! I had already the object <id>".replace("<id>", id));
						else 
							say("I am happy! Now I have  <id>".replace("<id>", id));
						break;
					}
					
					case SCAN:
					{
						String id = instruction.getId();
						if(this.itemContainer.numberOfItems() == 0) 
							say("My inventory is empty");
						else if (id == null) 
							say("I am carrying the following items" + this.itemContainer.toString());
						else{
							Item item = this.itemContainer.getItem(id);
							if (item == null)
								say("I have not such object");
							else 
								say(item.toString());
						}
						break;
					}
					
					case OPERATE:
					{
						String id = instruction.getId();
						Item item = this.itemContainer.getItem(id);
						if (!item.use(this, this.initialPlace)) 
							say("I have problems using the object <id>".replace("<id>", id));
						else {
							if (!item.canBeUsed()){
								say("What a pity! I have no more <id> in my inventory".replace("<id>", id));
								this.itemContainer.pickItem(id);
							}	
						}
						break;
					}

					case HELP: System.out.println(Interpreter.interpreterHelp());break;	// Muestra las instrucciones que reconoce WALL·E
					
					case QUIT: quit = true; break;	// Booleano de terminar
					
					case MOVE: 
					{	//Si se mueve, actualiza la informacion. Si no, muestra el mensaje de que no hay calle

						if (moveWalle()) {
							say("Moving in direction " + direction.toString());
							System.out.println(initialPlace.toString());
							this.fuel -= 5; //Actualiza el fuel al moverse.
							myFuelIs();
							myRecicledIs();
							lookingDirection(this.direction);
						}
						break;
					}
					
					default:  //case TURN: Como la instrucción es correcta, o gira a la derecha o a la izquierda
					{	
						this.fuel--;
						myFuelIs();
						myRecicledIs();
						if (instruction.getRotation().equals(Rotation.RIGHT))
							direction = direction.turnRight();
						else
							direction = direction.turnLeft();
						lookingDirection(this.direction);	//Después de haber girado, actualiza la dirección en la que está mirando
					}
				}
			}
		}
		sc.close();	//Cierra el escaner

		if(fuel <= 0)
			say("I run out of fuel. I cannot move. Shutting down...");
		else if (!quit)	//Si no se ha elegido la opción quit, es que se ha llegado a la nave. Se muestra el mensaje correspondiente
			say("I am at my space ship. Bye Bye");
		else
			say("I have communication problems. Bye Bye");	// Se ha elegido la opción quit, luego se muestra el mensaje de despedida
	}
	
	/*
	  Comprueba si desde el lugar en el que está WALL·E, hacia donde está
	  mirando hay una calle. Para ello, recorre el array de calles, comprobando
	  con el método comeOutFrom. En caso de que WALL·E esté mirando hacia una
	  calle, le avanza hasta el lugar que hay al otro lado de la calle con el
	  nextPlace y devuelve true. Si no ha encontrado calle devuelve false.
	 */
	private boolean moveWalle() {
		Street newStreet = cityMap.lookForStreet(this.initialPlace, this.direction);
		if (newStreet != null) {
			if (newStreet.isOpen()) {
				initialPlace = newStreet.nextPlace(initialPlace);
				return true;
			} else {
				say("Arrggg, there is a street but it is closed!");
				return false;
			}
		} else {
			System.out.println(" There is no street in direction " + direction.toString());
			return false;
		}
	}
	
	//METODOS PARA MOSTRAR POR CONSOLA:

	private void say(String message) {
		System.out.println("WALL·E says: " + message);
	}
	
	private void prompt() {
		System.out.print("WALL·E > ");
	}
	
	private void lookingDirection(Direction direction) {
		System.out.println("WALL·E is looking at direction " + direction.toString());
	}
	
	private void myFuelIs() {
		if (this.fuel <= 0) 
			System.out.println("   * My power is 0");
		else 
			System.out.println("   * My power is " + this.fuel );
	}
	
	private void myRecicledIs() {
		System.out.println("   * My recycled material is: " + this.recycledMaterial );
	}
	
	//private static final String LINE_SEPARATOR = System.getProperty("line.separator");

	//RobotEngine: Consta del lugar actual del robot, la dirección en la que mira, y el mapa por el que se mueve (array de calles)
	//Tambien tiene un array con los items que lleva el robot, la cantidad de combustible y el material reciclado hasta el momento
	private Place initialPlace;
	private Direction direction;
	private City cityMap;
	private int fuel;
	private ItemContainer itemContainer;
	private int recycledMaterial;
}