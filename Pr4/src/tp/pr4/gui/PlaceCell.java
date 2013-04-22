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
		this.setBackground(Color.GREEN);
	}
	
	// Al visitar un lugar, se cambia el botón
	public void leavePlace() {
		this.setBackground(Color.DARK_GRAY);
	}
	
	//Develve el numero de visitas, para el Undo
	public int getNumVisitas() {
		return numVisitas;
	}
	
	// Para el Undo, oculta un lugar
	public void ocultar() {
		this.setText(null);
		this.place = null;
	}
	
	private int numVisitas;
	private Place place;
	private static final long serialVersionUID = 1L;	//Daba warning
}
