package tp.pr3;

import java.util.Scanner;
import tp.pr3.instructions.Instruction;
import tp.pr3.instructions.exceptions.InstructionExecutionException;
import tp.pr3.instructions.exceptions.WrongInstructionFormatException;
import tp.pr3.items.ItemContainer;

public class RobotEngine {
	//Constructor a partir del mapa de la ciudad, el lugar inicial y la direccion la que mira el robot
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		this.navigation = new NavigationModule(cityMap, initialPlace);
		this.navigation.initHeading(direction);
		this.fuel = 100;
		this.itemContainer = new ItemContainer();
		this.recycledMaterial = 0;
		this.quit = false;
	}
	
	public void communicateRobot(Instruction instruction){
		instruction.configureContext(this, this.navigation, this.itemContainer);
		try{
			instruction.execute();
		}catch (InstructionExecutionException exception ){
			Escribe.mostrar(exception.getMessage());
		}
	}
	public Street getHeadingStreet(){
		return this.navigation.getHeadingStreet();
	}
	public void requestQuit(){
		this.quit = true;
	}
	
	//Incrementa o decrementa la cantidad de fuel que tiene wall e. Puede ser negativo el fuel.
	public void addFuel(int fuel) {
		this.fuel += fuel;
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);
	}

	//Incrementa la cantidad de material reciclado
	public void addRecycledMaterial(int weight){
		this.recycledMaterial += weight;
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);
	}
	
	//Para los tests
	public int getFuel(){
		return this.fuel;
	}
	
	//Para los tests
	public int getRecycledMaterial(){
		return this.recycledMaterial;
	}
	
	public void requestHelp(){
		Escribe.validInstructions(Interpreter.interpreterHelp());	// Muestra las instrucciones que reconoce WALL·E
	}
	
	public void printRobotState(){
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);	// Escribe el estado de walle.
	}
	
	private void mostrarInicio(){//Muestra los mendajes al iniciar el movimiento.
		Escribe.currentPlace(this.navigation.getCurrentPlace());
		Escribe.lookingDirection(this.navigation.getCurrentHeading());
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);
	}
	//Inicia el movimiento de WALL·E
	private boolean haveFuel(){
		return (this.fuel > 0);//Devuelve true si walle aun tiene combustible.
	}
	private boolean isSpaceship(){
		return this.navigation.atSpaceship();
	}

	public void startEngine() {
		mostrarInicio();
		Scanner sc = new Scanner(System.in);
		while (haveFuel() && !isSpaceship() && !quit) {
			Escribe.prompt();	//Muestra por consola: WALL·E>
			try{		
				communicateRobot(Interpreter.generateInstruction(sc.nextLine()));	/*genera una instrucion a partir de la cadena leída y 
																					 * se la envía al robot para que la ejecute*/
			}catch (WrongInstructionFormatException e) {
				Escribe.say(Escribe.NOT_UNDERSTAND);
			} 
		}
		sc.close();	//Cierra el escaner
		mostrarFinal();
	}
	private void mostrarFinal(){
		if(!haveFuel())
			Escribe.say("I run out of fuel. I cannot move. Shutting down...");
		else if (isSpaceship())	//Si se ha llegado a la nave, se muestra el mensaje correspondiente
			Escribe.say("I am at my spaceship. Bye bye");
		else
			Escribe.say("I have communication problems. Bye Bye");	// Se ha elegido la opción quit, luego se muestra el mensaje de despedida
	}
	private NavigationModule navigation;
	private int fuel;
	private ItemContainer itemContainer;
	private int recycledMaterial;
	private boolean quit;
}