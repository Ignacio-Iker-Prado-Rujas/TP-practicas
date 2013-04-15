package tp.pr4.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import tp.pr4.RobotEngine;
import tp.pr4.Rotation;
import tp.pr4.instructions.*;

public class RobotPanel extends JPanel{
	// Constructor: Se añade el intructionPanel y el dataPanel 
	// Convendria separar en un par de emtodos privados, que es un tocho
	public RobotPanel (RobotEngine elRobot) {
		this.setLayout(new BorderLayout());
		this.instructionPanel = new JPanel(new GridLayout(5, 2));
		TitledBorder instruct = new TitledBorder("Instructions");
		this.instructionPanel.setBorder(instruct);
		configureInstructionPanel();
		this.add(instructionPanel, BorderLayout.WEST);
		
		this.dataPanel = new JPanel(new BorderLayout());
		TitledBorder info = new TitledBorder("Instructions");
		info.setTitle("Robot info");
		this.dataPanel.setBorder(info);
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 5));
		JLabel fuel = new JLabel("Aquí va el fuel.");
		statusPanel.add(fuel);
		JLabel recycled = new JLabel("Y aquí el material reciclado.");
		statusPanel.add(recycled);
		this.dataPanel.add(statusPanel, BorderLayout.NORTH);
		
		TableModel tableModel = new TableModel(new String[] {"Id", "Description"});
		JTable table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(null);
		JScrollPane infoScroll  = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.dataPanel.add(infoScroll, BorderLayout.CENTER);
		this.add(dataPanel, BorderLayout.CENTER); 
		
		JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, instructionPanel, dataPanel);
		this.add(splitPanel);
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
		quit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane salir = new JOptionPane();
				String[] opciones = {"Yes, please.", "No way."};
				int n = JOptionPane.showOptionDialog(salir, "Are you sure you want to quit?", "QUIT", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon("src/tp/pr4/gui/headingIcons/walleQuit.png"), opciones, opciones[0]);
				if(n == 0)
					System.exit(0);
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
				if (item.getText() != null)
					robot.communicateRobot(new PickInstruction(item.getText()));
			}
		});
		this.instructionPanel.add(pick);
		//CAMPO DE TEXTO: ITEM A RECOGER
		item = new JTextField();
		this.instructionPanel.add(item);
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
		//INSTRUCCION UNDO
		JButton undo = new JButton("UNDO");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}		
		});
		this.instructionPanel.add(undo);
		/*
		 * Estaria bien poner en el hueco la ultima instruccion 
		 * realizada a la que se le pudiera hacer UNDO 
		 */
	}
	
	private JTextField item;
	private RobotEngine robot;
	private String itemId;
	private JPanel instructionPanel;
	private JPanel dataPanel;
	private static final long serialVersionUID = 1L;	//Daba warning
}
