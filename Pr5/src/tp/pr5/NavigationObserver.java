package tp.pr5;

public interface NavigationObserver {

void	headingChanged(Direction newHeading);
//Notifies that the robot heading has changed
 


void	initNavigationModule(PlaceInfo initialPlace, Direction heading);
//Notifies that the navigation module has been initialized
 


void	placeHasChanged(PlaceInfo placeDescription);
//Notifies that the place where the robot stays has changed (because the robot picked or dropped an item)
 


void	placeScanned(PlaceInfo placeDescription);
//Notifies that the user requested a RADAR instruction
 


void	robotArrivesAtPlace(Direction heading, PlaceInfo place);
//Notifies that the robot has arrived at a place 

}
