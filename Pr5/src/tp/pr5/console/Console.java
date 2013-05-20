package tp.pr5.console;

import tp.pr5.Direction;
import tp.pr5.EscribeConsola;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

public class Console implements NavigationObserver, RobotEngineObserver, InventoryObserver {

	// El RobotEngine informa de que la comunicacion ha terminado (QUIT)
	@Override
	public void communicationCompleted() {
		EscribeConsola.say(EscribeConsola.COMUNICATION_PROBLEMS);
	}

	// El RobotEngine informa de que se ha pedido la ayuda
	@Override
	public void communicationHelp(String help) {
		EscribeConsola.validInstructions(help);
	}

	/*
	 * El RobotEngine informa de que el robot se ha apagado
	 * (ya sea porque ha llegado al spaceship o porque no tiene fuel)
	 */
	@Override
	public void engineOff(boolean atShip) {
		EscribeConsola.endGame(atShip);
	}

	/* 
	 * Metodo que muestra por consola el error que se ha producido
	 * Puede ser porque no sea una instruccion valida, porque no se encuentre el objeto requerido...
	 */
	@Override
	public void raiseError(String msg) {
		// TODO: A lo mejor es EscribeConsola.say(msg) en vez de say 
		EscribeConsola.mostrar(msg);
	}

	// El RobotEngine informa de que el robot quiere decir algo ("WALL·E says: " y el mensaje)
	@Override
	public void robotSays(String message) {
		EscribeConsola.say(message);
	}
	
	/*
	 * El RobotEngine informa de que el fuel o la cantidad de 
	 * material reciclado ha cambiado
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		EscribeConsola.actualizarEstado(fuel, recycledMaterial);
	}

	// Notifica que la direccion del robot ha cambiado
	@Override
	public void headingChanged(Direction newHeading) {
		EscribeConsola.lookingDirection(newHeading);
	}

	// Notifica que el NavigationModule ha sido inicializado
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		EscribeConsola.currentPlace(initialPlace);
		EscribeConsola.lookingDirection(heading);
	}

	/*
	 * Notifica que el lugar donde esta el robot ha cambiado, 
	 * porque el robot ha cogido o soltado un item
	 * Para el modo consola no hace falta, lo dejamos vacio
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		
	}

	// Notifica que se ha solicitado una instruccion RADAR
	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		EscribeConsola.currentPlace(placeDescription);
	}

	// Notifica que el robot ha llegado a un lugar
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		EscribeConsola.moving(heading);
		EscribeConsola.currentPlace(place);
	}

	// Notifica que el container de items ha cambiado (no se usa en modo consola)
	@Override
	public void inventoryChange(Item[] inventory) {
		
	}

	// Notifica que se ha solcitado una instruccion SCAN del inventario
	@Override
	public void inventoryScanned(String inventoryDescription) {
		EscribeConsola.mostrar(inventoryDescription);
	}
	
	// Notifica que un item esta vacio (gastado) y que se borrará del container
	@Override
	public void itemEmpty(String itemName) {
		EscribeConsola.say(EscribeConsola.NO_MORE_OBJECT.replace("<id>", itemName));
	}

	// Notifica que el usuario quiere hacer SCAN sobre un item del inventorio
	@Override
	public void itemScanned(String description) {
		EscribeConsola.say(description);
	}

}
