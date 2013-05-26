package tp.pr5.gui;

import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import tp.pr5.Direction;
import tp.pr5.EscribeConsola;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;
import tp.pr5.RobotEngineObserver;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

public class InfoPanel extends JPanel implements RobotEngineObserver, NavigationObserver, InventoryObserver {
	
	// Constructor, se muestra el fuel y el material reciclado en la etiqueta
	public InfoPanel(GUIController guiController) {
		this.displayInfo = new JLabel(" ");
		this.add(displayInfo);
		guiController.registerEngineObserver(this);
		guiController.registerItemContainerObserver(this);
		guiController.registerItemContainerObserver(this);
	}

	// Notifica que el container de items ha cambiado
	@Override
	public void inventoryChange(List<Item> inventory) {
		
	}

	// Notifica que se ha solcitado una instruccion SCAN del inventario (no se usa en GUI)
	@Override
	public void inventoryScanned(String inventoryDescription) {
		
	}

	// Notifica que un item esta vacio (gastado) y que se borrará del container
	@Override
	public void itemEmpty(String itemName) {
		this.displayInfo.setText(EscribeConsola.SAY + EscribeConsola.NO_MORE_OBJECT.replace("<id>", itemName));
	}

	// Notifica que el usuario quiere hacer SCAN sobre un item del inventorio (no se usa en GUI)
	@Override
	public void itemScanned(String description) {

	}

	// Notifica que la direccion del robot ha cambiado
	@Override
	public void headingChanged(Direction newHeading) {
		this.displayInfo.setText(EscribeConsola.LOOK_DIRECTION.replace("<DIR>",
				newHeading.toString()));
	}

	// Notifica que el NavigationModule ha sido inicializado (no se usa en GUI)
	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {

	}

	/*
	 * Notifica que el lugar donde esta el robot ha cambiado, 
	 * porque el robot ha cogido o soltado un item
	 */
	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		
	}

	// Notifica que se ha solicitado una instruccion RADAR (no se usa en GUI)
	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		
	}

	// Notifica que el robot ha llegado a un lugar
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		
	}

	// El RobotEngine informa de que la comunicacion ha terminado
	@Override
	public void communicationCompleted() {
		
	}

	// El RobotEngine informa de que se ha pedido la ayuda
	@Override
	public void communicationHelp(String help) {
		
	}

	/*
	 * El RobotEngine informa de que el robot se ha apagado
	 * (ya sea porque ha llegado al spaceship o porque no tiene fuel)
	 */
	@Override
	public void engineOff(boolean atShip) {}

	// El RobotEngine informa de que ha habido un error
	@Override
	public void raiseError(String msg) {
		this.displayInfo.setText(msg);
	}

	// El RobotEngine informa de que el robot quiere decir algo
	@Override
	public void robotSays(String message) {
		this.displayInfo.setText(EscribeConsola.SAY + message);
	}

	/*
	 * El RobotEngine informa de que el fuel o la cantidad de 
	 * material reciclado ha cambiado
	 */
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.displayInfo.setText("Robot attributes has been updated: (" + fuel
				+ ", " + recycledMaterial + ")");
	}

	
	private JLabel displayInfo;
	private static final long serialVersionUID = 1L;  //Daba warning
	@Override
	public void undoMove(Direction heading, PlaceInfo place) {}

}
