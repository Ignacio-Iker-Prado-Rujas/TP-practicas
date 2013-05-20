package tp.pr5;

import tp.pr5.items.InventoryObserver;

public abstract class Controller {
	
	// Constructor que inicializa el RobotEngine
	public Controller(RobotEngine game) {
		this.engine = game;
	}

	// Registra un gameObserver en el modelo
	public void registerEngineObserver(RobotEngineObserver gameObserver) {
		this.engine.addEngineObserver(gameObserver);
	}

	// Registra un mapObserver en el modelo
	public void registerItemContainerObserver(InventoryObserver containerObserver) {
		this.engine.addItemContainerObserver(containerObserver);
	}

	// Registra al playerObserver en el modelo
	public void registerRobotObserver(NavigationObserver playerObserver) {
		this.engine.addNavigationObserver(playerObserver);
	}

	// Método abstracto que corre el juego
	public abstract void startController();

	protected RobotEngine engine;
}
