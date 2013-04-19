package tp.pr4.gui;

import tp.pr4.Direction;
import tp.pr4.Place;
import tp.pr4.Rotation;
import tp.pr4.gui.headingIcons.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class NavigationPanel extends JPanel {
	// Constructor: Se añade un MapViewPanel y el Area de Texto
	public NavigationPanel(Place initialPlace) {
		this.setLayout(new BorderLayout());
		this.mapViewPanel = new JPanel(new BorderLayout());
		 
		iconN = new ImageIcon("src/tp/pr4/gui/headingIcons/walleNorth.png");
		iconE = new ImageIcon("src/tp/pr4/gui/headingIcons/walleEast.png");
		iconS = new ImageIcon("src/tp/pr4/gui/headingIcons/walleSouth.png");
		iconW = new ImageIcon("src/tp/pr4/gui/headingIcons/walleWest.png");
		walle = new JLabel(iconN, JLabel.CENTER);
		walle.setOpaque(true);
		mapViewPanel.add(walle, BorderLayout.WEST);
		
		JPanel mapPanel = new JPanel(new GridLayout(11, 11));
		TitledBorder mapa = new TitledBorder("City Map");
		mapPanel.setBorder(mapa);
		
		this.textArea = new JTextArea(initialPlace.toString());
		TitledBorder texto = new TitledBorder("Log");
		this.textArea.setBorder(texto);
		this.textArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane, BorderLayout.SOUTH);
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				arrayLugares[i][j] = new PlaceCell(textArea);
				mapPanel.add(arrayLugares[i][j]);
			}
		}
		arrayLugares[width/2][width/2].visitPlace();
		arrayLugares[width/2][width/2].setPlace(initialPlace);
		this.x = width/2;
		this.y = width/2;
		this.mapViewPanel.add(mapPanel, BorderLayout.CENTER);
		
	
		this.add(mapViewPanel, BorderLayout.CENTER);
	}
	
	public void move(Place currentPlace, Direction currentHeading) {
		arrayLugares[x][y].leavePlace();
		cambiarPosicion(currentHeading);
		arrayLugares[x][y].setPlace(currentPlace);
		arrayLugares[x][y].visitPlace();
		actualizarLog(currentPlace);
	}
	
	public void actualizarDirection(Direction direction) {
		switch(direction) {
			case NORTH: walle.setIcon(this.iconN); break;
			case EAST:	walle.setIcon(this.iconE); break;
			case SOUTH: walle.setIcon(this.iconS); break;
			case WEST:	walle.setIcon(this.iconW); break;
			default: ; 
		}
	}
	
	private void cambiarPosicion(Direction currentHeading){
		switch (currentHeading) {
		case EAST: 	y++; break;	//Ojo que va de arriba a abajo
		case NORTH: x--; break;
		case SOUTH: x++; break;
		case WEST:	y--; break;
		default: ;
		}
	}

	public void actualizarLog(Place currentPlace) {
		this.textArea.setText(currentPlace.toString());
	}
	
	private int x;
	private int y;
	private PlaceCell[][] arrayLugares = new PlaceCell[width][width];
	private static final int width = 11;
	private JPanel mapViewPanel;
	private JTextArea textArea;
	private JLabel walle;
	private ImageIcon iconN;
	private ImageIcon iconE;
	private ImageIcon iconS;
	private ImageIcon iconW;
	private static final long serialVersionUID = 1L;	//Daba warning
}
