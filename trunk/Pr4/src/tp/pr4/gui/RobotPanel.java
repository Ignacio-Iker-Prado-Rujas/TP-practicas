package tp.pr4.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class RobotPanel extends JPanel{
	// Constructor: Se añade el intructionPanel y el dataPanel 
	// Convendria separar en un par de emtodos privados, que es un tocho
	public RobotPanel () {
		this.setLayout(new BorderLayout());
		this.instructionPanel = new JPanel(new GridLayout(4, 2));
		TitledBorder instruct = new TitledBorder("Instructions");
		this.instructionPanel.setBorder(instruct);
		//final JTextField campoDeTexto = new JTextField("Hey");
		// Este for se sacará a una funcion aparte para poner cada cosa bien
		for (int i = 1; i <= 8; i++) {
			JButton button = new JButton(i+"");
			/*button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					campoDeTexto.setText(campoDeTexto.getText()+"i");
				}
			});*/
			instructionPanel.add(button);
		}
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

	private JPanel instructionPanel;
	private JPanel dataPanel;
	private static final long serialVersionUID = 1L;	//Daba warning
}
