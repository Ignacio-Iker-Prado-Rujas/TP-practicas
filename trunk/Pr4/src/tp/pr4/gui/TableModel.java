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
	
	private void ampliaTabla() {
		String nuevaTabla[][] = new String[numElems + 1][columnNames.length];
		for(int i= 0; i < numElems; i++) {
			nuevaTabla[i][0] = this.data[i][0];
			nuevaTabla[i][1] = this.data[i][1];
		}
		this.data = nuevaTabla;
	}
	
	public void addData(String id, String description) {
		if(getRowCount() == numElems)
			ampliaTabla();
		setValueAt(id, numElems, 0);
		setValueAt(description, numElems, 1);
		numElems++;
	}
	
	public void removeData(int row) { 
		String nuevaTabla[][] = new String[numElems - 1][columnNames.length];
		for(int i= 0; i < numElems; i++) {
			if(i != row) {
				nuevaTabla[i][0] = this.data[i][0];
				nuevaTabla[i][1] = this.data[i][1];
			}
		}
		this.data = nuevaTabla;		
		numElems--;
		this.fireTableDataChanged();
	}
	
	private String[] columnNames;
	private String[][] data;
	private int numElems;
	private static final long serialVersionUID = 1L;	//Daba warning
}
