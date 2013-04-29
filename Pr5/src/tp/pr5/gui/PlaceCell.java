package tp.pr5.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;

import tp.pr5.Place;

public class PlaceCell extends JButton {
	public PlaceCell(final JTextArea textArea) {
		this.numVisitas = 0;
		this.place = null;
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(place == null) textArea.setText("Zona inexplorada");
				else textArea.setText(place.toString());
			}
		});
	}
	
	/* Al hacer undo del move, se desvisita un lugar */
	public void desVisitar() {
		numVisitas--;
	}
	
	/* Actualiza el lugar que representa un PlaceCell */
	public void setPlace(Place place) {
		this.setText(place.getName());
		this.place = place;
	}
	
	// Al visitar un lugar, se cambia el botón
	public void visitPlace() {
		this.numVisitas++;
		this.setBackground(Color.GREEN);
	}
	
	// Al visitar un lugar, se cambia el botón
	public void leavePlace() {
		this.setBackground(Color.GRAY);
	}
	
	//Develve el numero de visitas, para el Undo
	public int getNumVisitas() {
		return numVisitas;
	}
	
	// Para el Undo, oculta un lugar
	public void ocultar() {
		this.setText(null);
		this.place = null;
		this.setBackground(null);
	}
	
	private int numVisitas;
	private Place place;
	private static final long serialVersionUID = 1L;	//Daba warning
}
