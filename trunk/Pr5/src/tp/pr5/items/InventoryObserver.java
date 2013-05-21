package tp.pr5.items;

import java.util.List;

public interface InventoryObserver {

	// Notifica que el container de items ha cambiado
	public void inventoryChange(List<Item> inventory);

	// Notifica que se ha solcitado una instruccion SCAN del inventario
	public void inventoryScanned(String inventoryDescription);

	// Notifica que un item esta vacio (gastado) y que se borrará del container
	public void itemEmpty(String itemName);

	// Notifica que el usuario quiere hacer SCAN sobre un item del inventorio
	public void itemScanned(String description);

}
