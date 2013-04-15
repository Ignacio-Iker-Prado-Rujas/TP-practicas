package tp.pr4.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

import tp.pr4.RobotEngine;

public class MainWindow {
	/*  This class creates the window for the Swing interface. 
	 	The MainWindow contains the following components:
	 	
		· A menu with the QUIT action
		· An Action panel with several buttons to perform MOVE, TURN, OPERATE, PICK, and DROP actions.
			Additionally it has a combo box of turn rotations and a text field to write the name of the 
			item that the robot wants to pick from the current place
		· A RobotPanel that displays the robot information (fuel and recycled material) and the robot inventory, 
			which shows a table with item names and descriptions that the robot carries. The user can select the items 
			contained in the inventory in order to DROP or OPERATE an item
		· A CityPanel that represents the city. It shows the places that the robot has visited and an 
			icon that represents the robot heading. The robot heading is updated when the user performs a TURN action. 
			The visible places are updated when the robot performs a MOVE action. Once a place is visited, the user can click 
			on it in order to display the place description (similar to the RADAR command).
		*/
	
	public MainWindow(RobotEngine elRobot) {
		this.robot = elRobot;
		this.ventana = new JFrame("WALL·E The garbage collector");
		this.ventana.setSize(1080, 720);	
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		EventQueue.invokeLater(new Runnable() { 
			public void run() { 
				ventana.setVisible(true); 
				} 
			});
		ventana.setLayout(new BorderLayout());
		this.robotPanel = new RobotPanel(this.robot);
		ventana.add(this.robotPanel, BorderLayout.NORTH);
		
		this.navPanel = new NavigationPanel();
		ventana.add(this.navPanel, BorderLayout.CENTER);
		
		this.menuBar = new JMenuBar();
		//this.menuBar.setBorderPainted(true);
		menuBar.setBackground(Color.GRAY);
		JMenu file = new JMenu("File");
		file.setBackground(Color.GRAY);
		//file.setBorderPainted(false);
		menuBar.add(file);
		JMenuItem quit = new JMenuItem("Quit");
		file.add(quit);
		quit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane salir = new JOptionPane();
				String[] opciones = {"Yes, please.", "No way."};
				int n = JOptionPane.showOptionDialog(salir, "Are you sure you want to quit?", "QUIT", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, 
						new ImageIcon("src/tp/pr4/gui/headingIcons/walleQuit.png"), opciones, opciones[0]);
				if(n == 0)
					System.exit(0);
				}
		});
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

/* Dudas:
 * 1) Preguntar por KeyStroke
 * 2) ¿Como va la comunicacion con el robotEngine y que hacen los metodos esos?
 * 3) Iniciar en modo ventana vs. consola
 * 4) Instruccion UNDO
 * 5) Hacer JOptionPane para cuando no se puede hacer una accion
 * 6) 
 * 
 * Cosas que hacer:
 * 1) NO FUNCIONA: Cabiar el modo de meter la imagen: ¿new ImageIcon(new URL(navPanel.class.getResource("headingIcons/walleQuit.png")))?
 */
