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
import tp.pr4.instructions.Instruction;

public class MainWindow {
	public MainWindow(RobotEngine elRobot, Place initialPlace) {
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
		this.robot.setRobotPanel(robotPanel);
		ventana.add(this.robotPanel, BorderLayout.NORTH);
		
		this.navPanel = new NavigationPanel(initialPlace);
		ventana.add(this.navPanel, BorderLayout.CENTER);
		this.robot.setNavigationPanel(navPanel);
		buildMenuBar();
	}
	
	private void buildMenuBar(){
		this.menuBar = new JMenuBar();
		menuBar.setBackground(Color.GRAY);
		JMenu file = new JMenu("File");
		file.setBackground(Color.GRAY);
		menuBar.add(file);
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
		ventana.setJMenuBar(menuBar);
	}
	public void setVisible(boolean bool){
		this.ventana.setVisible(bool);
	}
	
	public void ActualizaLastInstruction(Instruction instruction) {
		robotPanel.actualizarLastInstruction(instruction);
	}
	
	private final JFrame ventana;
	private RobotEngine robot;
	private RobotPanel robotPanel;
	private NavigationPanel navPanel;
	private JMenuBar menuBar;
}

/* Cosas por hacer:
 * 
 * 1.hecho) Apañarlo para que saque una ventana cuando llegue al 
 * 	spaceship o se le acabe el fuel, el método está hecho en 
 * 	robotEngine ->  mostrarFinalVentana();
 * 
 * 2. hecho) Comprobar que pasa al hacer operate o drop sin seleccionar nada
 * 	(sale NullPointerException)
 * 
 * 3.este le haces tú, no consigoque se vuelva opaca la celda)
 *  Al hacer undo del move deberia ponerse la celda opaca y quitar el titulo
 * 
 * 4 hecho) Al hacer Undo de pick no desaparece de la tabla, 
 * 	y al hacer undo de operate si se gasta el item no funciona, falta hacer que lo vuelva a añadir a la tabla
 * 	y al hacer undo de drop no se recupera en la tabla
 * 
 * 5 Hecho) Lo de apache esta ya hecho?
 * 
 * 6 Mejor quitarlo, por razones logísticas) El campo de al lado del undo que pone la ultima instruccion hecha
 * 	Si no funciona, se quita
 * 
 * 7) Borrar comentarios que sobran como este y añadir alguno util, REORGANIZAR CODIGO
 * 
 */
