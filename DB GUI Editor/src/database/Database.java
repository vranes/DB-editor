package database;

import resource.DBNode;
import resource.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;

public class Database implements IDatabase {

    private IRepository repository;
    
    public Database(IRepository repository) {
		this.repository = repository;
	}

	@Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }

	@Override
	public void addRowToTable(String tableName, Row row) {
		repository.add(tableName, row);
	}

	@Override
	public void updateTableRow(String tableName, Row row, Row oldRow) {
		repository.update(tableName, row, oldRow);
	}

	@Override
	public void deleteTableRow(String tableName, Row row) {
		repository.delete(tableName, row);
	}
	
	public void search(String query) {
		repository.search(query);
	}
	
	public void report(String query) {
		repository.report(query);
	}

	@Override
	public void filterSort(String query, JTable jt) {
		repository.filterSort(query, jt);
	}

	@Override
	public void relations(String query, String tableName) {
		repository.relations(query, tableName);
	}
	
				
		
}
