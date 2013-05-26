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

import tp.pr5.RobotEngineObserver;
import tp.pr5.Rotation;
import java.util.List;
import tp.pr5.instructions.*;
import tp.pr5.items.InventoryObserver;
import tp.pr5.items.Item;

public class RobotPanel extends JPanel implements RobotEngineObserver, InventoryObserver {

	public RobotPanel (GUIController guiController) {
		//Fijamos el controlador
		this.controlador = guiController;
		this.setLayout(new BorderLayout());
		//Registramos observadores
		this.controlador.registerEngineObserver(this);
		this.controlador.registerItemContainerObserver(this);
		//Creamos el panel de los botones 
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
		this.fuel = new JLabel("Fuel: 0");
		statusPanel.add(fuel);		
		this.recycled = new JLabel("Recycled: 0");
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
	
	//Método que crea los botones con las instrucciones que acepta WALL·E
	private void configureInstructionPanel() {
		//INSTRUCCION MOVE
		JButton move = new JButton("MOVE");
		move.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.communicateRobot(new MoveInstruction());
			}		
		});
		this.instructionPanel.add(move);
		//INSTRUCCION QUIT
		JButton quit = new JButton("QUIT");
		quit.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent e) {
				communicationCompleted();
				}
		});
		this.instructionPanel.add(quit);
		//INSTRUCCION TURN
		JButton turn = new JButton("TURN");
		turn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.communicateRobot(new TurnInstruction(forceRotation(rotations.getSelectedItem().toString()))); //Este cast habrá que cambiarlo
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
					controlador.communicateRobot( new PickInstruction(item.getText()));
				else 
					raiseError("Escriba el nombre de algun item");
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
					raiseError("Seleccione algún item de la tabla");
				else
					controlador.communicateRobot(new DropInstruction(tableModel.getValueAt(fila, 0)));
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
					raiseError("Seleccione algún item de la tabla");
				else
					controlador.communicateRobot(new OperateInstruction(tableModel.getValueAt(fila, 0)));
			}		
		});
		this.instructionPanel.add(operate);
		//INSTRUCCION UNDO
		JButton undo = new JButton("UNDO");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controlador.communicateRobot(new UndoInstruction());
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
	
	@Override
	public void inventoryChange(List<Item> inventory) {
		int numElems = 0;
		for(Item item: inventory)
			if(item!=null) numElems++;
		String[][] nuevoInventory = new String[numElems][2];
		int i = 0;
		for(Item item: inventory){
			if(item!=null){
				nuevoInventory[i][0] = item.getId();
				nuevoInventory[i][1] = item.getDescription();
				i++;
			}
		}
		tableModel.actualizarTabla(nuevoInventory, numElems);
	}

	// No se necesita, ya tenemos la tabla con los items
	@Override
	public void inventoryScanned(String inventoryDescription) {}

	@Override
	public void itemEmpty(String itemName) {
		tableModel.deleteItem(itemName);
	}

	@Override
	public void itemScanned(String description) {}

	// Este metodo es para el QUIT, pero se llama desde su actionListener
	@Override
	public void communicationCompleted() {
		JOptionPane salir = new JOptionPane();
		String[] opciones = {"Yes, please.", "No way."};
		ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleQuit.png"));
		int n = JOptionPane.showOptionDialog(salir, "Are you sure you want to quit?", "QUIT", 
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, opciones, opciones[0]);
		if(n == 0)
			System.exit(0);
	}

	@Override
	public void communicationHelp(String help) { 
		
	}

	@Override
	public void engineOff(boolean atShip) {
		if (!atShip) {
			ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleQuit.png"));
			JOptionPane.showMessageDialog(this, "I run out of fuel. I cannot move. Shutting down...", 
					"Bye, bye!", JOptionPane.OK_OPTION, icon);
			System.exit(0);
		} else {
			ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleQuit.png"));
			JOptionPane.showMessageDialog(this, "I am at my spaceship. Bye bye", 
					"Bye, bye!", JOptionPane.OK_OPTION, icon);
			System.exit(0);
		}
	}

	@Override
	public void raiseError(String msg) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource("headingIcons/walleError.png"));
		JOptionPane.showMessageDialog(this, msg, "¡Cuidado!", JOptionPane.OK_OPTION, icon);
	}

	// No usa en el RobotPanel
	@Override
	public void robotSays(String message) {
		
	}

	@Override
	public void robotUpdate(int fuel, int recycledMaterial) {
		this.fuel.setText("Fuel: " + fuel);
		this.recycled.setText("Recycled: " + recycledMaterial);
	}
	
	private JLabel fuel;
	private JLabel recycled;
	private JComboBox<Rotation> rotations;
	private JTextField item;
	private GUIController controlador;
	private JPanel instructionPanel;
	private JTable table;
	private TableModel tableModel;
	private JPanel dataPanel;
	private static final long serialVersionUID = 1L;	//Daba warning
}
