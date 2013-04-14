package tp.pr4.gui;

import javax.swing.JButton;

import tp.pr4.Place;

public class PlaceCell extends JButton{
	//Constructor de las celda-lugar
	public PlaceCell(Place place) {
		//Por defecto no se esta en la celda ¿o lo pasamos como parametro al constructor?
		this.actual = false; 
		this.place = place;
	}
	
	//Metodo que modifica el booleano actual
	public void setActual(boolean bool) {
		this.actual = bool;
	}
	
	private boolean actual;
	private Place place;
	private static final long serialVersionUID = 1L;	//Daba warning
}
