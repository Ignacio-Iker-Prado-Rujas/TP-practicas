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
	/*public MainWindow(RobotEngine elRobot, Place initialPlace) {
		//Inicializamos el robotEngine
		this.robot = elRobot;
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
		this.robotPanel = new RobotPanel(this.robot);
		ventana.add(this.robotPanel, BorderLayout.NORTH);
		//Creamos el NavigationPanel (la parte que va al centro-sur con cosas de la ciudad)
		this.navPanel = new NavigationPanel(initialPlace);
		ventana.add(this.navPanel, BorderLayout.CENTER);
		//Llamamos al método que construye el menú
		buildMenuBar();
	}*/
	
	public MainWindow(GUIController guiController) {
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
		guiController.registerEngineObserver(robotPanel);
		guiController.registerItemContainerObserver(robotPanel);
		//Creamos el NavigationPanel (la parte que va al centro-sur con cosas de la ciudad)
		navPanel = new NavigationPanel();
		ventana.add(this.navPanel, BorderLayout.CENTER);
		guiController.registerRobotObserver(navPanel);
		//Llamamos al método que construye el menú
		buildMenuBar();
		infoPanel = new InfoPanel();
		ventana.add(infoPanel, BorderLayout.SOUTH);
		guiController.registerEngineObserver(infoPanel);
		guiController.registerItemContainerObserver(infoPanel);
		guiController.registerItemContainerObserver(infoPanel);
	}
	
	private void buildMenuBar(){
		//Creamos el menu
		this.menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		//Creamos la opcion File en el menu
		JMenu file = new JMenu("File");
		file.setBackground(Color.GRAY);
		menuBar.add(file);
		// Ponemos la opcion con al ayuda
		JMenuItem help = new JMenuItem("Help");
		file.add(help);
		help.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				communicationHelp(EscribeConsola.VALID_INSTRUCTIONS);
			}
		});
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
		// TODO: Revisar, da NullPointerException
		ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleError.png"));
		JOptionPane.showMessageDialog(robotPanel, help, "", JOptionPane.OK_OPTION, icon);		
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
	public void raiseError(String msg) {}

	// No se usa aqui
	@Override
	public void robotSays(String message) {}
	
	// No se usa aqui
	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {}

	private InfoPanel infoPanel;
	private NavigationPanel navPanel;
	private final JFrame ventana;
	//private RobotEngine robot;
	private RobotPanel robotPanel;
	//private NavigationPanel navPanel;
	private JMenuBar menuBar;
	}
