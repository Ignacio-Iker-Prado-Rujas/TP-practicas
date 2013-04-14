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
	
	public MainWindow(RobotEngine robot){
		this.robot = robot;
		this.ventana = new JFrame("WALL·E The garbage collector");
		this.ventana.setSize(1080, 720);
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		EventQueue.invokeLater(new Runnable() { 
			public void run() { 
				ventana.setVisible(true); 
				} 
			});
		ventana.setLayout(new BorderLayout());
		this.robotPanel = new RobotPanel();
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
				String[] opciones = {"No way.", "Yes, please."};
				int n = JOptionPane.showOptionDialog(salir, "Are you sure you want to quit?", "QUIT", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/tp/pr4/gui/headingIcons/walleQuit.png"), opciones, opciones[1]);
				if(n == 1) //Es un poco juno salir con el System.exit, a lo mejor mirar algo de ventana.close()
					System.exit(0);
			}
		});
		ventana.setJMenuBar(menuBar);
		
		
		
		//JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, navPanel, robotPanel);
		//ventana.add(splitPanel, BorderLayout.NORTH);
	
		//this.ventana.setJMenuBar(new JMenuBar());
		//ventana.add(new JMenuItem("HOLA"), BorderLayout.NORTH);
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
