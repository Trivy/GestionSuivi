package gestion.aexecuter;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class AExecuterModel extends DefaultTableModel {

	public AExecuterModel(Object[][] data, Object[] columnNames) {
		super(data, columnNames);
	}
	
	public boolean isCellEditable(int row, int column){
		return false;
	}
	
	public void setData(Object[][] data){		
		this.setDataVector(DefaultTableModel.convertToVector(data), 
						   this.columnIdentifiers);
	}

}
