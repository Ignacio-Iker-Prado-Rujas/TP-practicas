package tp.pr4.gui;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	//Constructor que crea las columnas con sus nombres, que se pasan en un array
	public TableModel(String[] colNames) {
		this.columnNames = new String[colNames.length];
		for(int i = 0; i < colNames.length; i++) 
			this.columnNames[i] = colNames[i];
	}
	
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getValueAt(int row, int col) {
		return data[row][col];
	}
	
	public void setValueAt(String cad, int row, int col) {
		data[row][col] = cad;
		fireTableCellUpdated(row, col);
	}
	
	private String[] columnNames;
	private String[][] data;
	private static final long serialVersionUID = 1L;	//Daba warning
}
