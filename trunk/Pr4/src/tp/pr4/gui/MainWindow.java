package tp.pr4.gui;

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
import tp.pr4.Place;
import tp.pr4.RobotEngine;

public class MainWindow {
	public MainWindow(RobotEngine elRobot, Place initialPlace) {
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
		this.robot.setRobotPanel(robotPanel);
		ventana.add(this.robotPanel, BorderLayout.NORTH);
		//Creamos el NavigationPanel (la parte que va al centro-sur con cosas de la ciudad)
		this.navPanel = new NavigationPanel(initialPlace);
		ventana.add(this.navPanel, BorderLayout.CENTER);
		this.robot.setNavigationPanel(navPanel);
		//Llamamos al método que construye el menú
		buildMenuBar();
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
				JOptionPane salir = new JOptionPane();
				String[] opciones = {"Yes, please.", "No way."};
				ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleQuit.png"));
				int n = JOptionPane.showOptionDialog(salir, "Are you sure you want to quit?", "QUIT", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, opciones, opciones[0]);
				if(n == 0)
					System.exit(0);
				}
		});
		//Una ventana solo puede tener un menu
		ventana.setJMenuBar(menuBar);
	}
	
	public void setVisible(boolean bool){
		this.ventana.setVisible(bool);
	}
	
	private final JFrame ventana;
	private RobotEngine robot;
	private RobotPanel robotPanel;
	private NavigationPanel navPanel;
	private JMenuBar menuBar;
}
