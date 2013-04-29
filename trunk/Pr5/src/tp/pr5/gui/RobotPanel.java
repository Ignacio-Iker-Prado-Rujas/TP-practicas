package tp.pr5.gui;

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

import tp.pr5.RobotEngine;
import tp.pr5.Rotation;
import tp.pr5.instructions.*;

public class RobotPanel extends JPanel{
	// Constructor: Se añade el intructionPanel y el dataPanel 
	public RobotPanel (RobotEngine elRobot) {
		//Fijamos el robotEngine
		this.robot = elRobot;
		this.setLayout(new BorderLayout());
		//Creamos el panel con los botones 
		this.instructionPanel = new JPanel(new GridLayout(5, 2));
		TitledBorder instruct = new TitledBorder("Instructions");
		this.instructionPanel.setBorder(instruct);
		//Llamamos al método que crea los botones y sus listeners
		configureInstructionPanel();
		this.add(instructionPanel, BorderLayout.WEST);
		//Creamos el panel de datos del robot
		this.dataPanel = new JPanel(new BorderLayout());
		TitledBorder info = new TitledBorder("Robot info");
		this.dataPanel.setBorder(info);
		JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 5));
		//Añadimos los Label con el Fuel y el RecycledMaterial
		this.fuel = new JLabel("Fuel: " + elRobot.getFuel());
		statusPanel.add(fuel);		
		this.recycled = new JLabel("Recycled: " + elRobot.getRecycledMaterial());
		statusPanel.add(recycled);
		this.dataPanel.add(statusPanel, BorderLayout.NORTH);
		//Creamos la TableModel (que hereda de AbstractTableModel)
		this.tableModel = new TableModel(new String[] {"Id", "Description"});
		//Creamos la JTable y le pasamos la TableModel en el constructor
		table = new JTable(tableModel);
		table.setPreferredScrollableViewportSize(null);
		//Permitimos que se pueda hacer Scroll por si se cogen muchos Items
		JScrollPane infoScroll  = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.dataPanel.add(infoScroll, BorderLayout.CENTER);
		this.add(dataPanel, BorderLayout.CENTER); 
		//Ponemos un SplitPane para cambiar las dimensiones del RobotPanel
		JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, instructionPanel, dataPanel);
		this.add(splitPanel);
	}

	public void actualizarFuel(int fuel) {
		this.fuel.setText("Fuel: " + fuel);
	}

	public void actualizarRecycled(int totRec) {
		this.recycled.setText("Recycled: " + totRec);
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
	}
	
	private Rotation forceRotation(String nameRotation) {
		if(Rotation.LEFT.toString().equalsIgnoreCase(nameRotation))
			return Rotation.LEFT;
		else if(Rotation.RIGHT.toString().equalsIgnoreCase(nameRotation))
			return Rotation.RIGHT;
		else 
			return Rotation.UNKNOWN;
	}
	
	/***********Operacinoes de la tabla **********************************/
	

	public void	addItem(String id, String description) {
		tableModel.addData(id, description);
	}
	
	public void deleteItem(String id){
		tableModel.deleteItem(id);
	}
	public void deleteSelectedItem() { 
		int row = table.getSelectedRow();
		tableModel.removeData(row);
	}
	/***************************Fin metodos tabla *****************/
	
	private JLabel fuel;
	private JLabel recycled;
	private JComboBox<Rotation> rotations;
	private JTextField item;
	private RobotEngine robot;
	private JPanel instructionPanel;
	private JTable table;
	private TableModel tableModel;
	private JPanel dataPanel;
	private static final long serialVersionUID = 1L;	//Daba warning
}
