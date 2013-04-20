package tp.pr4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import tp.pr4.instructions.UndoInstruction;

public class TableModel extends AbstractTableModel {
	//Constructor que crea las columnas con sus nombres, que se pasan en un array
	public TableModel(String[] colNames) {
		this.columnNames = new String[colNames.length];
		for(int i = 0; i < colNames.length; i++) 
			this.columnNames[i] = colNames[i];
		this.data = new String[0][colNames.length];
		this.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				
			}
			
			public void actionPerformed(ActionEvent e) {
				//getSelectedRow();
			}		
			
		});
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public int getRowCount() {
		return this.data.length;
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public String getValueAt(int row, int col) {
		return this.data[row][col];
	}
	
	public void setValueAt(String cad, int row, int col) {
		this.data[row][col] = cad;
		this.fireTableCellUpdated(row, col);
		this.fireTableDataChanged();
		/*Este ultimo método notifica a los listeners
		  que la tabla ha cambiado y la redibuja */
	}
	

	public void addData(String id, String description) {
		this.data = new String[1][this.columnNames.length];
		setValueAt(id, this.getRowCount() - 1, 0);
		setValueAt(description, this.getRowCount() - 1, 1);
	}
	
	public void removeData(int row) { 
		for(int i = row; i < getRowCount(); i++) {
			setValueAt(getValueAt(i + 1, 0), i, 0);
			setValueAt(getValueAt(i + 1, 1), i, 1);
		}
		setValueAt(null, getRowCount(), 0);
		setValueAt(null, getRowCount(), 1);
	}
	
	private String[] columnNames;
	private String[][] data;
	private static final long serialVersionUID = 1L;	//Daba warning
}
