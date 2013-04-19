package tp.pr4.gui;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	//Constructor que crea las columnas con sus nombres, que se pasan en un array
	public TableModel(String[] colNames) {
		this.columnNames = new String[colNames.length];
		for(int i = 0; i < colNames.length; i++) 
			this.columnNames[i] = colNames[i];
		this.data = new String[0][colNames.length];
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
		fireTableCellUpdated(row, col);
	}
	
	public void addData(String id, String description) {
		this.data = new String[1][this.columnNames.length];
		this.data[this.columnNames.length - 1][0] = id;
		this.data[this.columnNames.length - 1][1] = description;
	}
	
	public void removeData() {
		
		
	}
	
	private String[] columnNames;
	private String[][] data;
	private static final long serialVersionUID = 1L;	//Daba warning
}
