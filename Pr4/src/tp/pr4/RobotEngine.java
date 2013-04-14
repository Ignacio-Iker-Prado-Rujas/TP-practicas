package tp.pr4;

import java.util.Scanner;

import tp.pr4.gui.MainWindow;
import tp.pr4.gui.NavigationPanel;
import tp.pr4.gui.RobotPanel;
import tp.pr4.instructions.Instruction;
import tp.pr4.instructions.exceptions.InstructionExecutionException;
import tp.pr4.instructions.exceptions.WrongInstructionFormatException;
import tp.pr4.items.ItemContainer;

public class RobotEngine {
	// Constructor a partir del mapa de la ciudad, el lugar inicial y la direccion la que mira el robot
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		this.navigation = new NavigationModule(cityMap, initialPlace);
		this.navigation.initHeading(direction);
		this.fuel = 100;
		this.itemContainer = new ItemContainer();
		this.recycledMaterial = 0;
		this.quit = false;
	}
	
	public void communicateRobot(Instruction instruction) {
		instruction.configureContext(this, this.navigation, this.itemContainer);
		try {
			instruction.execute();
		} catch (InstructionExecutionException exception) {
			Escribe.mostrar(exception.getMessage());
		}
	}

	public Street getHeadingStreet() {
		return this.navigation.getHeadingStreet();
	}

	public void requestQuit() {
		this.quit = true;
	}

	// Incrementa o decrementa la cantidad de fuel que tiene WALL·E 
	//Puede ser negativo el fuel.
	public void addFuel(int fuel) {
		this.fuel += fuel;
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);
	}

	// Incrementa la cantidad de material reciclado
	public void addRecycledMaterial(int weight) {
		this.recycledMaterial += weight;
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);
	}

	// Para los tests
	public int getFuel() {
		return this.fuel;
	}

	// Para los tests
	public int getRecycledMaterial() {
		return this.recycledMaterial;
	}

	// Muestra las instrucciones que reconoce WALL·E
	public void requestHelp() {
		Escribe.validInstructions(Interpreter.interpreterHelp());
	}

	// Escribe el estado de WALL·E
	public void printRobotState() {
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);
	}

	// Muestra los mendajes al iniciar el movimiento
	private void mostrarInicio() {
		Escribe.currentPlace(this.navigation.getCurrentPlace());
		Escribe.lookingDirection(this.navigation.getCurrentHeading());
		Escribe.actualizarEstado(this.fuel, this.recycledMaterial);
	}

	// Devuelve true si WALL·E aun tiene combustible
	private boolean haveFuel() {
		return (this.fuel > 0);
	}

	// Devuelve true si es la nave espacial
	private boolean isSpaceship() {
		return this.navigation.atSpaceship();
	}
	
	private void mostrarFinal() {
		if (!haveFuel())
			Escribe.say(Escribe.OUT_OF_FUEL);
		else if (isSpaceship())							//Si se ha llegado a la nave, se muestra el mensaje correspondiente
			Escribe.say(Escribe.IN_SPACESHIP);
		else
			Escribe.say(Escribe.COMUNICATION_PROBLEMS);	// Se ha elegido la opción quit, luego se muestra el mensaje de despedida
	}
	
	public void startEngine() {
		MainWindow window = new MainWindow(this);
		window.setVisible(true);
		mostrarInicio();
		Scanner sc = new Scanner(System.in);
		while (haveFuel() && !isSpaceship() && !quit) {
			Escribe.prompt(); // Muestra por consola: WALL·E>
			try {
				// Genera una instrucion a partir de la cadena leída y se la envía al robot para que la ejecute
				communicateRobot(Interpreter.generateInstruction(sc.nextLine()));	
			} catch (WrongInstructionFormatException e) {
				Escribe.say(Escribe.NOT_UNDERSTAND);
			}
		}
		sc.close(); // Cierra el escaner
		mostrarFinal();
	}

	//Sets a panel to the navigation module in order to show its information in a GUI
	//TODO: 
	public void setNavigationPanel(NavigationPanel navPanel) {}
	
	//Sets a panel in order to show the robot information and the container in a GUI
	//TODO: 
	public void setRobotPanel(RobotPanel robotPanel) {}
	
	//Sets the main window of the GUI in order to inform about some robot events
	//TODO: 
	public void setGUIWindow(MainWindow mainWindow) {}
	
	private NavigationModule navigation;
	private int fuel;
	private ItemContainer itemContainer;
	private int recycledMaterial;
	private boolean quit;
}