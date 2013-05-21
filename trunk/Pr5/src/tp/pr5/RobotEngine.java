package tp.pr5;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tp.pr5.gui.NavigationPanel;
import tp.pr5.gui.RobotPanel;
import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class RobotEngine extends Observable<RobotEngineObserver>{
	// Constructor a partir del mapa de la ciudad, el lugar inicial y la direccion la que mira el robot
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		this.pilaInstruction = new Stack<Instruction>(); 
		this.navigation = new NavigationModule(cityMap, initialPlace);
		this.navigation.initHeading(direction);
		this.fuel = 100;
		this.itemContainer = new ItemContainer();
		this.recycledMaterial = 0;
		this.robotPanel = null;
	}
	
	public void communicateRobot(Instruction instruction) {
		instruction.configureContext(this, this.navigation, this.itemContainer);
		try {
			instruction.execute();
			pilaInstruction.add(instruction);
		} catch (InstructionExecutionException exception) {
			if (modoConsola())
				EscribeConsola.mostrar(exception.getMessage());
			else {
				ImageIcon icon = new ImageIcon(this.getClass().getResource("gui/headingIcons/walleError.png"));
				JOptionPane.showMessageDialog(robotPanel, exception.getMessage(), "¡Cuidado!", JOptionPane.OK_OPTION, icon);
			}
		}
	}

	public Street getHeadingStreet() {
		return this.navigation.getHeadingStreet();
	}

	public void requestQuit() {
		for( RobotEngineObserver o : arrayEngineObservers){
			o.communicationCompleted();
		}
	}

	// Incrementa o decrementa la cantidad de fuel que tiene WALL·E 
	//Puede ser negativo el fuel.
	public void addFuel(int fuel) {
		this.fuel += fuel;
		for( RobotEngineObserver o : arrayEngineObservers){
			o.robotUpdate(this.fuel, recycledMaterial);
		}
		/*
		if (modoConsola())
			EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
		else{
			robotPanel.actualizarFuel(this.fuel);
			if(!haveFuel()){
				ImageIcon icon = new ImageIcon(this.getClass().getResource("gui/headingIcons/walleQuit.png"));
				JOptionPane.showMessageDialog(robotPanel, "I run out of fuel. I cannot move. Shutting down...", 
						"Bye, bye!", JOptionPane.OK_OPTION, icon);
				System.exit(0);
			}
		}*/
	}

	// Incrementa la cantidad de material reciclado
	public void addRecycledMaterial(int weight) {
		this.recycledMaterial += weight;
		for( RobotEngineObserver o : arrayEngineObservers){
			o.robotUpdate(fuel, this.recycledMaterial);
		}
		/*
		if (modoConsola())
			EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
		else 
			robotPanel.actualizarRecycled(this.recycledMaterial);*/
	}

	// Para los tests
	public int getFuel() {
		return this.fuel;
	}

	// Para los tests
	public int getRecycledMaterial() {
		return this.recycledMaterial;
	}
	
	/* Devuelve el item indicado */
	public Item getItem(String id){
        return itemContainer.getItem(id);
	}

	// Muestra las instrucciones que reconoce WALL·E (Solo funciona en consola)
	public void requestHelp() {
		for( RobotEngineObserver o : arrayEngineObservers){
			o.communicationHelp(Interpreter.interpreterHelp());
		}
	}

	// Escribe el estado de WALL·E
	public void printRobotState(int a) {
		if (modoConsola())EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
	}
	
	/* Indica si el programa se está ejecutando en modo consola */
	public boolean modoConsola(){
		return (this.robotPanel == null);
	}
	
	/* Indica si un item se ha gastado */
	
	public boolean itemGastado(String id) {
		if(itemContainer.containsItem(id))
			return false;
		else
			return true;
	}

	// Muestra los mendajes al iniciar el movimiento
	private void mostrarInicio() {
		EscribeConsola.currentPlace(this.navigation.getCurrentPlace());
		EscribeConsola.lookingDirection(this.navigation.getCurrentHeading());
		EscribeConsola.actualizarEstado(this.fuel, this.recycledMaterial);
	}

	// Devuelve true si WALL·E aun tiene combustible
	private boolean haveFuel() {
		return (this.fuel > 0);
	}

	// Devuelve true si es la nave espacial
	private boolean isSpaceship() {
		return this.navigation.atSpaceship();
	}
	
	//Sets a panel to the navigation module in order to show its information in a GUI
	public void setNavigationPanel(NavigationPanel navPanel) {
		navigation.setNavigationPanel(navPanel);
	}
	
	//Sets a panel in order to show the robot information and the container in a GUI
	public void setRobotPanel(RobotPanel robotPanel) {
		this.robotPanel = robotPanel;
	}
	
	//Sets the main window of the GUI in order to inform about some robot events
	//public void setGUIWindow(MainWindow mainWindow) {} por ahora no se usa
	
	/** Devuelve la ultima instrucción apilada **/
	
	public Instruction lastInstruction() throws InstructionExecutionException{
		if (this.pilaInstruction.isEmpty()) 
			throw new InstructionExecutionException(EscribeConsola.NOT_MORE_INSTRUCTIONS);
		else 
			return  this.pilaInstruction.pop();
			// Devuelve la cima de la pila, eliminando la instrucción.
	}
	
	/* La ventana muestra el mensaje que se le pasa */
	public void darAvisoVentana(String mensaje){
		ImageIcon icon = new ImageIcon(this.getClass().getResource("gui/headingIcons/walleError.png"));
		JOptionPane.showMessageDialog(robotPanel, mensaje, "", JOptionPane.OK_OPTION, icon);
	}

	/****************Operaciones de la tabla de ventana*************/
	
	public void	addItem(String id, String description) {
		if (!modoConsola())robotPanel.addItem(id, description);
	}
	
	public void deleteItem(String id) {
		if (!modoConsola())robotPanel.deleteItem(id);
	}
	
	public void deleteSelectedItem() { 
		if (!modoConsola())robotPanel.deleteSelectedItem();
	}
	
		/************ pruebas autoengine *****************************************/
	
	/*public void autoEngine(){
		ArrayList<String> arraySolucion = new ArrayList<String>();
		autoEngine(arraySolucion);
	}
	private static Instruction[] arrayInstructions = { 
		new MoveInstruction(), new PickInstruction(),
		new OperateInstruction(), new TurnInstruction() };
	
	private ArrayList<String> autoEngine(ArrayList<String> arraySolucion){
		for(Instruction i : arrayInstructions){
			communicateRobot(i);
			arraySolucion.add(i.toString());
			if(!isSpaceship()){
				autoEngine(arraySolucion);
				
			}else return arraySolucion;		
		}
		return arraySolucion;
	}*/
	
	// Registers an EngineObserver to the model
	public void addEngineObserver(RobotEngineObserver observer) {
		arrayEngineObservers.add(observer);
	}

	// Registers an ItemContainerObserver to the model
	public void addItemContainerObserver(InventoryObserver observer) {
		arrayInventoryObservers.add(observer);
	}

	// Register a NavigationObserver to the model
	public void addNavigationObserver(NavigationObserver robotObserver) {
		arrayNavigationObservers.add(robotObserver);
	}

	// Checks if the simulation is finished
	public boolean isOver() {
		return (!haveFuel() || isSpaceship());
	}
	
	// Requests the engine to inform that an error has been raised
	public void requestError(String msg) {
		for (RobotEngineObserver o : arrayEngineObservers) {
			o.raiseError(msg);
		}
	}
	
	// Requests the engine to inform the observers that the simulation starts
	public void requestStart() {
		for (NavigationObserver o : arrayNavigationObservers) {
			o.initNavigationModule(navigation.getCurrentPlace(),
					navigation.getCurrentHeading());
		}
	}

	// Request the engine to say something
	public void saySomething(String message) {
		for (RobotEngineObserver o : arrayEngineObservers) {
			o.robotSays(message);
		}
	}

	private ArrayList<RobotEngineObserver> arrayEngineObservers;
	private ArrayList<InventoryObserver> arrayInventoryObservers;
	private ArrayList<NavigationObserver> arrayNavigationObservers;
	private RobotPanel robotPanel;
	private Stack<Instruction> pilaInstruction;
	private NavigationModule navigation;
	private int fuel;
	private ItemContainer itemContainer;
	private int recycledMaterial;

}