package tp.pr4.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import tp.pr4.Rotation;

public class RobotPanel extends JPanel{
	// Constructor: Se añade el intructionPanel y el dataPanel 
	// Convendria separar en un par de emtodos privados, que es un tocho
	public RobotPanel () {
		this.setLayout(new BorderLayout());
		this.instructionPanel = new JPanel(new GridLayout(4, 2));
		TitledBorder instruct = new TitledBorder("Instructions");
		this.instructionPanel.setBorder(instruct);
		configureInstructionPanel();
		this.add(instructionPanel, BorderLayout.WEST);
		
		this.dataPanel = new JPanel(new BorderLayout());
		TitledBorder info = new TitledBorder("Instructions");
		info.setTitle("Robot info");
		this.dataPanel.setBorder(info);
		JLabel statusPanel = new JLabel("Aqui va el combustible y el material reciclado");
		this.dataPanel.add(statusPanel, BorderLayout.NORTH);
		/* Esto habra que ver como se hace
		 * Vector<String> nombCol = new Vector<String>();
		 * nombCol.add("Id"); 
		 * nombCol.add("Description");
		*/
		JTable table = new JTable(7, 2); //Habra que cambiar el numero
		//table.setSize(2, 3);
		this.dataPanel.add(table, BorderLayout.CENTER);
		this.add(dataPanel, BorderLayout.CENTER); 
	}

	//Método que crea los botones con las instrucciones que acepta WALL·E
	private void configureInstructionPanel() {
		//INSTRUCCION MOVE
		JButton move = new JButton("MOVE");
		move.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.instructionPanel.add(move);
		//INSTRUCCION QUIT
		JButton quit = new JButton("QUIT");
		quit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.instructionPanel.add(quit);
		//INSTRUCCION TURN
		JButton turn = new JButton("TURN");
		turn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.instructionPanel.add(turn);
		//DIRECION DE LA ROTACION
		Rotation[] dir = {Rotation.LEFT, Rotation.RIGHT};
		JComboBox<Rotation> directions = new JComboBox<Rotation>(dir);
		this.instructionPanel.add(directions);
		// System.out.println(directions.getSelectedItem()); //Este metodo nos da la rotacion seleccionada
		// directions.setSelectedItem(Rotation.RIGHT); 		 //Y este lo modifica
		
		//INSTRUCCION PICK
		JButton pick = new JButton("PICK");
		pick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.instructionPanel.add(pick);
		//CAMPO DE TEXTO: ITEM A RECOGER
		JTextField item = new JTextField();
		this.instructionPanel.add(item);
		//item.getSelectedText(); //Te da el texto del JtextField
		//INSTRUCCION DROP
		JButton drop = new JButton("DROP");
		drop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.instructionPanel.add(drop);
		//INSTRUCCION OPERATE
		JButton operate = new JButton("OPERATE");
		operate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.instructionPanel.add(operate);
	}
	
	private JPanel instructionPanel;
	private JPanel dataPanel;
	private static final long serialVersionUID = 1L;	//Daba warning
}
