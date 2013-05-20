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
		
	}

	@Override
	public void communicationHelp(String help) {
		EscribeConsola.validInstructions(help);
	}

	@Override
	public void engineOff(boolean atShip) {
		if (!atShip)
			EscribeConsola.say(EscribeConsola.OUT_OF_FUEL);
		else					
			EscribeConsola.say(EscribeConsola.IN_SPACESHIP);
	}

	@Override
	public void raiseError(String msg) {
		
	}

	@Override
	public void robotSays(String message) {
		EscribeConsola.say(message);
	}

	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		
	}

	@Override
	public void headingChanged(Direction newHeading) {
		
	}

	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		
	}

	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		
	}

	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		
	}

	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		
	}

}
