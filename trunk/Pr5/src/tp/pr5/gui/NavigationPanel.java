package tp.pr5.gui;

import tp.pr5.Direction;
import tp.pr5.NavigationObserver;
import tp.pr5.PlaceInfo;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class NavigationPanel extends JPanel implements NavigationObserver {
	
	// Constructor: Se añade un MapViewPanel y el Area de Texto
	public NavigationPanel(GUIController guiController) {
		//Registramos observador
		guiController.registerRobotObserver(this);
		//Ponemos el NavigationPanel como BorderLayout
		this.setLayout(new BorderLayout());
		this.mapViewPanel = new JPanel(new BorderLayout());
		//Inicializamos las imagenes
		iconN = new ImageIcon(this.getClass().getResource("headingIcons/walleNorth.png"));
		iconE = new ImageIcon(this.getClass().getResource("headingIcons/walleEast.png"));
		iconS = new ImageIcon(this.getClass().getResource("headingIcons/walleSouth.png"));
		iconW = new ImageIcon(this.getClass().getResource("headingIcons/walleWest.png"));
		walle = new JLabel(iconN, JLabel.CENTER);
		//Metemos la imagen inicial (mirando al norte)
		mapViewPanel.add(walle, BorderLayout.WEST);
		//Creamos el panel-ciudad
		mapPanel = new JPanel(new GridLayout(width, width));
		TitledBorder mapa = new TitledBorder("City Map");
		mapPanel.setBorder(mapa);
		this.mapViewPanel.add(mapPanel, BorderLayout.CENTER);
		//Creamos el area de texto que muestra la información de los lugares
		this.textArea = new JTextArea();
		TitledBorder texto = new TitledBorder("Log");
		this.textArea.setBorder(texto);
		this.textArea.setEditable(false);
		this.textArea.setRows(6);
		JScrollPane scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(scrollPane, BorderLayout.SOUTH);
		//Inicializamos cada lugar
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				arrayLugares[i][j] = new PlaceCell(textArea);
				mapPanel.add(arrayLugares[i][j]);
			}
		}
		/*initNavigationModule(initialPlace, Direction.NORTH);*/
		this.mapViewPanel.add(mapPanel, BorderLayout.CENTER);
		this.add(mapViewPanel, BorderLayout.CENTER);
	}
	
	//Mueve a WALL·E desde currentPlace en direccion currentHeading
	/*public void move(Place currentPlace, Direction currentHeading) {
		arrayLugares[x][y].leavePlace();
		cambiarPosicion(currentHeading);
		arrayLugares[x][y].setPlace(currentPlace);
		arrayLugares[x][y].visitPlace();
		actualizarLog(currentPlace);
	}*/

	//Para el Undo, depende del numero de visitas de cada lugar
	//Cuentas con que estas posicionado en el lugar correcto, 
	// vuelves hacia atrás, actualizas y regresas.
	public void undoMove(Direction currentHeading, PlaceInfo currentPlace){
		/* Actualizas los valores del lugar anterior	*/
		cambiarPosicion(currentHeading.oppositeDirection());
		arrayLugares[x][y].desVisitar();
		if(arrayLugares[x][y].getNumVisitas() <= 0)
			arrayLugares[x][y].ocultar();
		/*	Actualizas los valores del lugar actual */
		cambiarPosicion(currentHeading);
		arrayLugares[x][y].desVisitar(); //Para que no le sume la visita
	}
	
	//Modifica la imagen
	public void actualizarDirection(Direction direction) {
		switch(direction) {
			case NORTH: walle.setIcon(this.iconN); break;
			case EAST:	walle.setIcon(this.iconE); break;
			case SOUTH: walle.setIcon(this.iconS); break;
			case WEST:	walle.setIcon(this.iconW); break;
			default: ; 
		}
	}
	
	//Cambia la posicion (x, y) en funcion de la direccion
	private void cambiarPosicion(Direction currentHeading){
		switch (currentHeading) {
		case EAST: 	y++; break;	//Ojo que va de arriba a abajo
		case NORTH: x--; break; //Y de izquierda a derecha
		case SOUTH: x++; break;
		case WEST:	y--; break;
		default: ;
		}
	}

	private void actualizarLog(PlaceInfo currentPlace) {
		this.textArea.setText(currentPlace.toString());
	}
	
	
	@Override
	public void headingChanged(Direction newHeading) {
		actualizarDirection(newHeading);
	}

	@Override
	public void initNavigationModule(PlaceInfo initialPlace, Direction heading) {
		actualizarDirection(heading);
		this.textArea.setText(initialPlace.toString());
		//Y situamos a WALL·E en el medio
		arrayLugares[width/2][width/2].visitPlace();
		arrayLugares[width/2][width/2].setPlace(initialPlace);
		this.x = width/2;
		this.y = width/2;
	}

	@Override
	public void placeHasChanged(PlaceInfo placeDescription) {
		this.actualizarLog(placeDescription);
	}

	// No es necesario, tenemos el campo de texto con la informacion del lugar
	@Override
	public void placeScanned(PlaceInfo placeDescription) {
		
	}

	
	@Override
	public void robotArrivesAtPlace(Direction heading, PlaceInfo place) {
		arrayLugares[x][y].leavePlace();
		cambiarPosicion(heading);
		arrayLugares[x][y].setPlace(place);
		arrayLugares[x][y].visitPlace();
		actualizarLog(place);
	}
	
    private int x;
    private int y;
    private PlaceCell[][] arrayLugares = new PlaceCell[width][width];
    private static final int width = 11;
    private JPanel mapViewPanel;
    private JPanel mapPanel;
    private JTextArea textArea;
    private JLabel walle;
    private ImageIcon iconN;
    private ImageIcon iconE;
    private ImageIcon iconS;
    private ImageIcon iconW;
    private static final long serialVersionUID = 1L;        //Daba warning
}
