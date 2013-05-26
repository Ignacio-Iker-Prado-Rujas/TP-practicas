package tp.pr5;

import java.util.Stack;

import tp.pr5.instructions.Instruction;
import tp.pr5.instructions.MoveInstruction;
import tp.pr5.instructions.OperateInstruction;
import tp.pr5.instructions.PickInstruction;
import tp.pr5.instructions.TurnInstruction;
import tp.pr5.instructions.exceptions.InstructionExecutionException;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;
import tp.pr5.items.ItemContainer;

public class RobotEngine extends Observable<RobotEngineObserver> {
	
	// Constructor a partir del mapa de la ciudad, el lugar inicial y la direccion la que mira el robot
	public RobotEngine(City cityMap, Place initialPlace, Direction direction) {
		this.pilaInstruction = new Stack<Instruction>(); 
		this.navigation = new NavigationModule(cityMap, initialPlace);
		this.navigation.initHeading(direction);
		this.quit = false;
		this.fuel = 100;
		this.recycledMaterial = 0;
		this.itemContainer = new ItemContainer();
	}
	
	public void communicateRobot(Instruction instruction) {
		instruction.configureContext(this, this.navigation, this.itemContainer);
		try {
			instruction.execute();
			pilaInstruction.add(instruction);
		} catch (InstructionExecutionException exception) {
			for( RobotEngineObserver o : this.arrayObservers){
				o.raiseError(exception.getMessage());
			}
		}
	}

	public Street getHeadingStreet() {
		return this.navigation.getHeadingStreet();
	}

	public void requestQuit() {
		this.quit = true;
		for (RobotEngineObserver o : this.arrayObservers) {
			o.communicationCompleted();
		}
	}

	// Incrementa o decrementa la cantidad de fuel que tiene WALL·E 
	//Puede ser negativo el fuel.
	public void addFuel(int fuel) {
		this.fuel += fuel;
		for (RobotEngineObserver o : this.arrayObservers) {
			o.robotUpdate(this.fuel, recycledMaterial);
		}
		if(!haveFuel()){
			for (RobotEngineObserver o : this.arrayObservers) {
				o.engineOff(false);
			}
		}
	}

	// Incrementa la cantidad de material reciclado
	public void addRecycledMaterial(int weight) {
		this.recycledMaterial += weight;
		for (RobotEngineObserver o : this.arrayObservers) {
			o.robotUpdate(fuel, this.recycledMaterial);
		}
	}

	// Para los tests
	public int getFuel() {
		return this.fuel;
	}

	// Para los tests
	public int getRecycledMaterial() {
		return this.recycledMaterial;
	}
	
	// Devuelve el item indicado
	public Item getItem(String id){
        return itemContainer.getItem(id);
	}

	// Muestra las instrucciones que reconoce WALL·E (Solo funciona en consola)
	public void requestHelp() {
		for( RobotEngineObserver o : this.arrayObservers){
			o.communicationHelp(Interpreter.interpreterHelp());
		}
	}
	
	// Indica si un item se ha gastado
	public boolean itemGastado(String id) {
		if(itemContainer.containsItem(id))
			return false;
		else
			return true;
	}

	// Devuelve true si WALL·E aun tiene combustible
	private boolean haveFuel() {
		return (this.fuel > 0);
	}

	// Devuelve true si es la nave espacial
	private boolean isSpaceship() {
		return this.navigation.atSpaceship();
	}
	
	/** Devuelve la ultima instrucción apilada **/
	
	public Instruction lastInstruction() throws InstructionExecutionException{
		if (this.pilaInstruction.isEmpty()) 
			throw new InstructionExecutionException(EscribeConsola.NOT_MORE_INSTRUCTIONS);
		else 
			return  this.pilaInstruction.pop();
			// Devuelve la cima de la pila, eliminando la instrucción.
	}
	
	// Registers an EngineObserver to the model
	public void addEngineObserver(RobotEngineObserver observer) {
		this.arrayObservers.add(observer);
	}

	// Registers an ItemContainerObserver to the model
	public void addItemContainerObserver(InventoryObserver observer) {
		this.itemContainer.addObserver(observer);
	}

	// Register a NavigationObserver to the model
	public void addNavigationObserver(NavigationObserver robotObserver) {
		navigation.addObserver(robotObserver);
	}

	// Checks if the simulation is finished
	public boolean isOver() {
		return (!haveFuel() || isSpaceship() || quit);
	}
	
	// Requests the engine to inform that an error has been raised
	public void requestError(String msg) {
		for (RobotEngineObserver o : this.arrayObservers) {
			o.raiseError(msg);
		}
	}
	
	// Requests the engine to inform the observers that the simulation starts
	public void requestStart() {
		navigation.initHeading(navigation.getCurrentHeading());
		for (NavigationObserver o : navigation.arrayObservers) {
			o.initNavigationModule(navigation.getCurrentPlace(),
					navigation.getCurrentHeading());
		}
		for (RobotEngineObserver o : this.arrayObservers) {
			o.robotUpdate(fuel, recycledMaterial);
		}
	}

	// Request the engine to say something
	public void saySomething(String message) {
		for (RobotEngineObserver o : this.arrayObservers) {
			o.robotSays(message);
		}
	}
	
	// Indica a los observadores que la partida ha terminado y por qué ha sido
	public void endOfGame() {
		if (!quit)
			for (RobotEngineObserver o : this.arrayObservers)
				o.engineOff(navigation.atSpaceship());
	}
	
	//Notifica que una calle ha sido abierta
	public void calleAbierta(){
		for (RobotEngineObserver o : this.arrayObservers)
			o.robotSays(EscribeConsola.STREET_HAVE_OPENED);
	}
	//Notifica que una calle ha sido cerrada
	public void calleCerrada(){
		for (RobotEngineObserver o : this.arrayObservers)
			o.robotSays(EscribeConsola.STREET_HAVE_CLOSED);
	}

	private Stack<Instruction> pilaInstruction;
	private NavigationModule navigation;
	private boolean quit;
	private int fuel;
	private int recycledMaterial;
	private ItemContainer itemContainer;


	
	/*********************** autoengine *****************************************/


	public Stack<String> autoEngine(int limiteProfundidad) {
		Stack<String> arraySolucion = new Stack<String>();
		boolean encontrada = false;
		for(int i = 1; !encontrada && i <= limiteProfundidad ; i++) {
			encontrada = autoEngine(arraySolucion, 1,  i);
		}
		return arraySolucion;
	}
	private static Instruction[] arrayInstructions = { 
		new MoveInstruction(), new TurnInstruction(Rotation.LEFT),
		new TurnInstruction(Rotation.RIGHT), new PickInstruction(),
		new OperateInstruction()};
	
	//Falta hacer que cuando encuentre la solucion salga correctamente
	
	private boolean autoEngine(Stack<String> arraySolucion, int profunActual,
			int profMaxima) {
		if (profunActual > profMaxima) {
			return false;
		} else {
			for (int i = 0; i < 5; i++) {
				if (haveFuel()) {
					if (isSpaceship()) {
						return true;
					} else {
						if (i == 3) {
							String[] arrayItems = this.navigation
									.getCurrentPlace().getArrayItems();
							for (int it = 0; it < arrayItems.length; it++) {
								try {
									Instruction instruction = new PickInstruction(
											arrayItems[it]);
									instruction
											.configureContext(this,
													this.navigation,
													this.itemContainer);
									instruction.execute();
									this.pilaInstruction.add(instruction);
									arraySolucion.add(arrayInstructions[i]
											.toString() + " " + arrayItems[it]);
									if (autoEngine(arraySolucion,
											profunActual + 1, profMaxima))
										return true;
									else {
										try {
											lastInstruction().undo();
										} catch (InstructionExecutionException e) {
										}
										arraySolucion.pop();
									}
								} catch (InstructionExecutionException e1) {
								}
							}

						} else if (i == 4) {
							String[] arrayItems = itemContainer.listaItems();
							for (int it = 0; it < arrayItems.length; it++) {
								try {
									Instruction instruction = new OperateInstruction(
											arrayItems[it]);
									instruction
											.configureContext(this,
													this.navigation,
													this.itemContainer);
									instruction.execute();
									this.pilaInstruction.add(instruction);
									arraySolucion.add(arrayInstructions[i]
											.toString() + " " + arrayItems[it]);
									if (autoEngine(arraySolucion,
											profunActual + 1, profMaxima))
										return true;
									else {
										try {
											lastInstruction().undo();
										} catch (InstructionExecutionException e) {
										}
										arraySolucion.pop();
									}
								} catch (InstructionExecutionException e1) {
								}
							}
						}

						else {
							try {
								arrayInstructions[i].configureContext(this,
										this.navigation, this.itemContainer);
								arrayInstructions[i].execute();
								this.pilaInstruction.add(arrayInstructions[i]);
								arraySolucion.add(arrayInstructions[i]
										.toString());
								if (autoEngine(arraySolucion, profunActual + 1,
										profMaxima))
									return true;
								else {
									try {
										lastInstruction().undo();
									} catch (InstructionExecutionException e) {
									}
									arraySolucion.pop();
								}
							} catch (InstructionExecutionException e1) {
							}
						}
					}
				}
			}
		}
		return false;
	}
/*******************************************************************************************/

}