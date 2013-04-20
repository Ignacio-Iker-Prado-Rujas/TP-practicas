package tp.pr4.gui;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
	//Constructor que crea las columnas con sus nombres, que se pasan en un array
	public TableModel(String[] colNames) {
		this.columnNames = new String[colNames.length];
		for(int i = 0; i < colNames.length; i++) 
			this.columnNames[i] = colNames[i];
		this.data = new String[0][colNames.length];
		this.numElems = 0;
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
	
	private void duplicaTabla() {
		// Por si es cero, le sumo 1
		String nuevaTabla[][] = new String[numElems * 2 + 1][columnNames.length];
		for(int i= 0; i < numElems; i++) {
			nuevaTabla[i][0] = this.data[i][0];
			nuevaTabla[i][1] = this.data[i][1];
		}
		this.data = nuevaTabla;
	}

	public void addData(String id, String description) {
		if(getRowCount() == numElems)
			duplicaTabla();
		setValueAt(id, numElems, 0);
		setValueAt(description, numElems, 1);
		numElems++;
	}
	
	public void removeData(int row) { 
		for(int i = row; i < numElems - 1; i++) {
			setValueAt(getValueAt(i + 1, 0), i, 0);
			setValueAt(getValueAt(i + 1, 1), i, 1);
		}
		setValueAt(" ", numElems - 1, 0);
		setValueAt(" ", numElems - 1, 1);
		numElems--;
	}
	
	private String[] columnNames;
	private String[][] data;
	private int numElems;
	private static final long serialVersionUID = 1L;	//Daba warning
}
