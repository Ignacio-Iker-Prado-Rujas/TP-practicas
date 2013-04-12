package tp.pr4.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;

import tp.pr4.RobotEngine;

public class MainWindow {
	public MainWindow(RobotEngine robot){
		this.robot = robot;
		this.ventana = new JFrame("WALL·E The garbage collector");
		this.ventana.setSize(1080, 720);
		this.ventana.setVisible(true);
		ventana.setLayout(new BorderLayout());
		ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		RobotPanel robotPanel = new RobotPanel();
		Botonera botonera = new Botonera();
		JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, botonera, robotPanel);
		ventana.add(splitPanel, BorderLayout.NORTH);
	
		ventana.add(robotPanel, BorderLayout.NORTH);
		this.ventana.setJMenuBar(new JMenuBar());
		ventana.add(new JMenuItem("HOLA"), BorderLayout.NORTH);
	}
	
	public void setVisible(boolean bool){
		this.ventana.setVisible(bool);
	}
	
	private RobotEngine robot;
	private JFrame ventana;
}
