package tp.pr5.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import tp.pr5.EscribeConsola;
import tp.pr5.RobotEngineObserver;

public class MainWindow implements RobotEngineObserver {
	
	public MainWindow(GUIController guiController) {
		//Registramos observador
		guiController.registerEngineObserver(this);	
		//Creamos la ventana principal
		this.ventana = new JFrame("WALL·E The garbage collector");
		this.ventana.setSize(1080, 720);	
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		EventQueue.invokeLater(new Runnable() { 
			public void run() { 
				ventana.setVisible(true); 
				} 
			});
		//Y la ponemos un BorderLayout
		ventana.setLayout(new BorderLayout());
		//Creamos el RobotPanel (la parte que va al norte con cosas del robot)
		this.robotPanel = new RobotPanel(guiController);
		ventana.add(this.robotPanel, BorderLayout.NORTH);
		//Creamos el NavigationPanel (la parte que va al centro-sur con cosas de la ciudad)
		navPanel = new NavigationPanel(guiController);
		ventana.add(this.navPanel, BorderLayout.CENTER);
		//Llamamos al método que construye el menú
		buildMenuBar();
		//Construimos el infoPanel que va abajo
		infoPanel = new InfoPanel(guiController);
		ventana.add(infoPanel, BorderLayout.AFTER_LAST_LINE);
	}
	
	private void buildMenuBar(){
		//Creamos el menu
		this.menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		//Creamos la opcion File en el menu
		JMenu file = new JMenu("File");
		file.setBackground(Color.GRAY);
		menuBar.add(file);
		//Y dentro de la opcion File creamos la opcion Quit
		JMenuItem quit = new JMenuItem("Quit");
		file.add(quit);
		quit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				communicationCompleted();
			}
		});
		//Una ventana solo puede tener un menu
		ventana.setJMenuBar(menuBar);
	}
	
	public void setVisible(boolean bool){
		this.ventana.setVisible(bool);
	}
	

	@Override
	public void communicationCompleted() {
		JOptionPane salir = new JOptionPane();
		String[] opciones = {"Yes, please.", "No way."};
		ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleQuit.png"));
		int n = JOptionPane.showOptionDialog(salir, "Are you sure you want to quit?", "QUIT", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, opciones, opciones[0]);
		if (n == 0)
			System.exit(0);
	}

	@Override
	public void communicationHelp(String help) {
		
	}

	@Override
	public void engineOff(boolean atShip) {
		String mensaje;
		if (atShip){
			mensaje = EscribeConsola.IN_SPACESHIP;
			ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleQuit.png"));
			JOptionPane.showMessageDialog(robotPanel, mensaje, 
					"Bye, bye!", JOptionPane.OK_OPTION, icon);
			System.exit(0);
		}
		else 
			mensaje = EscribeConsola.OUT_OF_FUEL;
		ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleError.png"));
		JOptionPane.showMessageDialog(robotPanel, mensaje, "", JOptionPane.OK_OPTION, icon);
		System.exit(0);
	}

	// No se usa aqui
	@Override
	public void raiseError(String msg) {
		
	}

	// No se usa aqui
	@Override
	public void robotSays(String message) {
		
	}
	
	// No se usa aqui
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		
	}

	private InfoPanel infoPanel;
	private NavigationPanel navPanel;
	private final JFrame ventana;
	private RobotPanel robotPanel;
	private JMenuBar menuBar;
	}
