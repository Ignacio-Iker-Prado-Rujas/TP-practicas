package tp.pr4.gui;

import tp.pr4.gui.headingIcons.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

public class NavigationPanel extends JPanel {
	// Constructor: Se añade un MapViewPanel y el Area de Texto
	public NavigationPanel() {
		this.setLayout(new BorderLayout());
		this.mapViewPanel = new JPanel(new BorderLayout());
		 
		ImageIcon icon = new ImageIcon("src/tp/pr4/gui/headingIcons/walleNorth.png");
		JLabel walle = new JLabel(icon, JLabel.CENTER);
		walle.setOpaque(true);
		mapViewPanel.add(walle, BorderLayout.WEST);
		
		JPanel mapPanel = new JPanel(new GridLayout(11, 11));
		TitledBorder mapa = new TitledBorder("City Map");
		mapPanel.setBorder(mapa);
		for (int i = 1; i <= width * width; i++) {
			PlaceCell lugar = new PlaceCell();
			mapPanel.add(lugar);
		}
		// Alñadir atributo array de placecells con coordenadas x, y
		this.mapViewPanel.add(mapPanel, BorderLayout.CENTER);
		
		this.textArea = new JTextArea("Hello", 5, 2);	//Cambiar
		TitledBorder texto = new TitledBorder("Log");
		this.textArea.setBorder(texto);
		this.textArea.setEditable(false);
		
		JScrollPane scrollPane = new JScrollPane(this.textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		this.add(mapViewPanel, BorderLayout.CENTER);
		this.add(scrollPane, BorderLayout.SOUTH);
	}

	private static final int width = 11;
	private JPanel mapViewPanel;
	private JTextArea textArea;
	private static final long serialVersionUID = 1L;	//Daba warning
}
