package tp.pr5.console;

import tp.pr5.Direction;
import tp.pr5.EscribeConsola;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;

public class Console implements NavigationObserver, RobotEngineObserver, InventoryObserver {

	@Override
	public void communicationCompleted() {
		EscribeConsola.say(EscribeConsola.COMUNICATION_PROBLEMS);
	}

	@Override
	public void communicationHelp(String help) {
		EscribeConsola.validInstructions(help);
	}

	@Override
	public void engineOff(boolean atShip) {
		EscribeConsola.endGame(atShip);
	}

	@Override
	public void raiseError(String msg) {
		//A lo mejor es EscribeConsola.mostrar(msg) en vez de say 
		EscribeConsola.say(msg);
	}

	@Override
	public void robotSays(String message) {
		//A lo mejor es EscribeConsola.mostrar(msg) en vez de say 
		EscribeConsola.say(message);
	}

	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		EscribeConsola.actualizarEstado(fuel, recycledMaterial);
	}

	@Override
	public void headingChanged(Direction newHeading) {
		EscribeConsola.lookingDirection(newHeading);
	}

	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		EscribeConsola.currentPlace(initialPlace);
		EscribeConsola.lookingDirection(heading);
		//Al principio de todo, primera llamada
	}

	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		//Para cuando coges o dejas un item
	}

	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		//muestra los items del lugar
	}

	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		//Cuando haces move correctamente, creo que es lo mismo que initNavigationModule()
	}

}
