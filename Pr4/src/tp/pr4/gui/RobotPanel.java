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

import tp.pr4.RobotEngine;
import tp.pr4.Rotation;
import tp.pr4.instructions.*;
import tp.pr4.instructions.exceptions.InstructionExecutionException;

public class RobotPanel extends JPanel{
	// Constructor: Se añade el intructionPanel y el dataPanel 
	// Convendria separar en un par de metodos privados, que es un tocho
	public RobotPanel (RobotEngine elRobot) {
		this.robot = elRobot;
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
		
		
		
		this.fuel = new JLabel("Fuel: " + elRobot.getFuel());
		statusPanel.add(fuel);		
		this.recycled = new JLabel("Recycled: " + elRobot.getRecycledMaterial());
		statusPanel.add(recycled);
		this.dataPanel.add(statusPanel, BorderLayout.NORTH);
		
		this.tableModel = new TableModel(new String[] {"Id", "Description"});
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(null);
		JScrollPane infoScroll  = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.dataPanel.add(infoScroll, BorderLayout.CENTER);
		this.add(dataPanel, BorderLayout.CENTER); 
		
		JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, instructionPanel, dataPanel);
		this.add(splitPanel);
	}

	public void actualizarFuel(int fuel) {
		this.fuel.setText("Fuel: " + fuel);
	}

	public void actualizarRecycled(int totRec) {
		this.recycled.setText("Recycled: " + totRec);
	}
	
	public void actualizarLastInstruction(Instruction instruction) {
		this.lastInstruction.setText(instruction.toString());
	}

	//Método que crea los botones con las instrucciones que acepta WALL·E
	private void configureInstructionPanel() {
		//INSTRUCCION MOVE
		JButton move = new JButton("MOVE");
		move.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				robot.communicateRobot(new MoveInstruction());
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
				ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleQuit.png"));
				int n = JOptionPane.showOptionDialog(salir, "Are you sure you want to quit?", "QUIT", 
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, opciones, opciones[0]);
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
				robot.communicateRobot(new TurnInstruction(forceRotation(rotations.getSelectedItem().toString()))); //Este cast habrá que cambiarlo
			}		
		});
		this.instructionPanel.add(turn);
		//DIRECION DE LA ROTACION
		Rotation[] rot = {Rotation.LEFT, Rotation.RIGHT};
		rotations = new JComboBox<Rotation>(rot);
		this.instructionPanel.add(rotations);
		
		//INSTRUCCION PICK
		JButton pick = new JButton("PICK");
		pick.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (item.getText() != null) 
					robot.communicateRobot( new PickInstruction(item.getText()));
				else 
					robot.darAvisoVentana("Escriba el nombre de algun item");
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
				int fila = table.getSelectedRow();
				if (fila < 0)
					robot.darAvisoVentana("Seleccione algún item de la tabla");
				else
					robot.communicateRobot(new DropInstruction(tableModel.getValueAt(fila, 0)));
			}		
		});
		this.instructionPanel.add(drop);
		//INSTRUCCION OPERATE
		JButton operate = new JButton("OPERATE");
		operate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int fila = table.getSelectedRow();
				if (fila < 0)
					robot.darAvisoVentana("Seleccione algún item de la tabla");
				else
					robot.communicateRobot(new OperateInstruction(tableModel.getValueAt(fila, 0)));
			}		
		});
		this.instructionPanel.add(operate);
		//INSTRUCCION UNDO
		JButton undo = new JButton("UNDO");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				robot.communicateRobot(new UndoInstruction());
			}		
		});
		this.instructionPanel.add(undo);
		try {
			lastInstruction = new JLabel(robot.lastInstruction().toString());
			this.instructionPanel.add(lastInstruction);
		} catch (InstructionExecutionException e1) {
			lastInstruction = new JLabel("Nothing to undo");
			this.instructionPanel.add(lastInstruction);
		}
	}
	
	private Rotation forceRotation(String nameRotation) {
		if(Rotation.LEFT.toString().equalsIgnoreCase(nameRotation))
			return Rotation.LEFT;
		else if(Rotation.RIGHT.toString().equalsIgnoreCase(nameRotation))
			return Rotation.RIGHT;
		else 
			return Rotation.UNKNOWN;
	}
	
	/***********operacinoes de la tabla **********************************/
	

	public void	addItem(String id, String description) {
		tableModel.addData(id, description);
	}
	
	public void deleteSelectedItem() { 
		int row = table.getSelectedRow();
		tableModel.removeData(row);
	}
	/*************************** fin metodos tabla *****************/
	
	private JLabel fuel;
	private JLabel recycled;
	private JLabel lastInstruction;
	private JComboBox<Rotation> rotations;
	private JTextField item;
	private RobotEngine robot;
	private JPanel instructionPanel;
	private JTable table;
	private TableModel tableModel;
	private JPanel dataPanel;
	private static final long serialVersionUID = 1L;	//Daba warning
}
