package tp.pr4.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

import tp.pr4.Place;

public class PlaceCell extends JButton {
	public PlaceCell(final JTextArea textArea) {
		this.numVisitas = 0;
		this.actual = false; 
		this.visited = false;
		this.place = null;
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(place == null) textArea.setText("Zona inexplorada");
				else textArea.setText(place.toString());
			}
		});
	}
	
	public void desVisitar() {
		numVisitas--;
	}
	
	public void setPlace(Place place) {
		this.setText(place.getName());
		this.place = place;
	}
	
	// Al visitar un lugar, se cambia el botón
	public void visitPlace() {
		this.numVisitas++;
		this.visited = true;
		this.actual = true;
		this.setBackground(Color.GREEN);
	}
	
	// Al visitar un lugar, se cambia el botón
	public void leavePlace() {
		this.actual = false;
		this.setBackground(Color.DARK_GRAY);
	}
	
	public int getNumVisitas(){
		return numVisitas;
	}
	public void ocultar(){
		this.setText(null);
		this.place = null;
		this.actual = false;
		this.visited = false;
	}
	/* 
	 * Tenemos dos booleanos, uno para saber si 
	 * se ha visitado el lugar y otro para saber 
	 * si es el actual
	 */
	
	private int numVisitas;
	private boolean actual;
	private boolean visited;
	private Place place;
	private static final long serialVersionUID = 1L;	//Daba warning
}
