package model;

import resource.Row;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

public class TableModel extends DefaultTableModel {

	private static final long serialVersionUID = 1L;
	private List<Row> rows;
	private String name;

    private void updateModel(){

        int columnCount = rows.get(0).getFields().keySet().size();

        Vector columnVector = DefaultTableModel.convertToVector(rows.get(0).getFields().keySet().toArray());
        Vector dataVector = new Vector(columnCount);

        for (int i=0; i<rows.size(); i++){
            dataVector.add(DefaultTableModel.convertToVector(rows.get(i).getFields().values().toArray()));
        }
        setDataVector(dataVector, columnVector);
    }

    public void setRows(List<Row> rows) {		//glavna funkcija za menjanje prikazane tabele
        this.rows = rows;
        updateModel();
    }

	public List<Row> getRows() {
		return rows;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
