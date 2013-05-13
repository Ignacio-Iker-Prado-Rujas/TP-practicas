package tp.pr5;

import tp.pr5.items.InventoryObserver;

public abstract class Controller {
	
	public Controller(RobotEngine game){
		engine = game;
	}
	
	//Registers a GameObserver to the model
	public void registerEngineObserver(RobotEngineObserver gameObserver){
		engine.addEngineObserver(gameObserver);
	}
	
	 //Registers a MapObserver to the model
	public void registerItemContainerObserver(InventoryObserver containerObserver){
		engine.addItemContainerObserver(containerObserver);
	}
	
	//Registers a PlayerObserver to the model
	public void registerRobotObserver(NavigationObserver playerObserver){
		engine.addNavigationObserver(playerObserver);
	}
	
	//Abstract method that runs the game. 
	public abstract void startController(); 
	

	protected RobotEngine engine;
}
