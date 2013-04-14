package tp.pr4.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RobotPanel extends JPanel{
	// Constructor: Se añade el intructionPanel y el dataPanel 
	public RobotPanel () {
		this.instructionPanel = new JPanel(new GridLayout(4, 2));
		final JTextField campoDeTexto = new JTextField("Hey");
		for (int i = 1; i <= 8; i++) {
			JButton button = new JButton(i%10+"");
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					campoDeTexto.setText(campoDeTexto.getText()+"i");
				}
			});
			instructionPanel.add(button);
		}
		this.dataPanel = new JPanel(new BorderLayout());
	}

	private JPanel instructionPanel;
	private JPanel dataPanel;
	private static final long serialVersionUID = 1L;	//Daba warning
}
