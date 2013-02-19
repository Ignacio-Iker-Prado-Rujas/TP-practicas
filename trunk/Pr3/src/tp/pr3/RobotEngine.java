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
		this.fuel = 50;
		this.itemContainer = new ItemContainer();
		this.recycledMaterial = 0;
		this.quit = false;
	}
	
	public void communicateRobot(Instruction c){
		c.configureContext(this, this.navigation, this.itemContainer);
		try{
			c.execute();
		}catch (InstructionExecutionException i ){
			System.err.println(i.getMessage());
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
		Instruction instruction = null;
		while (!isSpaceship() && !this.quit && haveFuel()) {
			Escribe.prompt();	//Muestra por consola: WALL·E>
			// Lee una instruccion, y se la pasa al interprete que genera la corespondiente instruccion
			try{
				instruction = Interpreter.generateInstruction(sc.nextLine());		
				instruction.configureContext(this, this.navigation, this.itemContainer);
				try {instruction.execute();}
				catch (InstructionExecutionException e){}//TODO Que imprima el mensaje correspondiente .err
				}catch (WrongInstructionFormatException e) {
				System.err.println(e.getMessage());
				}//TODO Escribe.say("I do not understand. Please repeat");
		}
		sc.close();	//Cierra el escaner
		mostrarFinal();
	}
	private void mostrarFinal(){
		if(!haveFuel())
			Escribe.say("I run out of fuel. I cannot move. Shutting down...");
		else if (isSpaceship())	//Si se ha llegado a la nave, se muestra el mensaje correspondiente
			Escribe.say("I am at my space ship. Bye Bye");
		else
			Escribe.say("I have communication problems. Bye Bye");	// Se ha elegido la opción quit, luego se muestra el mensaje de despedida
	}
	private NavigationModule navigation;
	private int fuel;
	private ItemContainer itemContainer;
	private int recycledMaterial;
	private boolean quit;
}