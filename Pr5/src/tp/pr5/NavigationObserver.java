package tp.pr5;

public interface NavigationObserver {
	
	// Notifica que la direccion del robot ha cambiado
	void headingChanged(Direction newHeading);

	// Notifica que el NavigationModule ha sido inicializado
	void initNavigationModule(PlaceInfo initialPlace, Direction heading);

	/*
	 * Notifica que el lugar donde esta el robot ha cambiado, 
	 * porque el robot ha cogido o soltado un item
	 */
	void placeHasChanged(PlaceInfo placeDescription);

	// Notifica que se ha solicitado una instruccion RADAR
	void placeScanned(PlaceInfo placeDescription);

	// Notifica que el robot ha llegado a un lugar
	void robotArrivesAtPlace(Direction heading, PlaceInfo place);
	
	void undoMove(Direction heading, PlaceInfo place);

}
