package database;

import resource.DBNode;
import resource.Row;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;

public interface IRepository {

    DBNode getSchema();

    List<Row> get(String tableName);
    void add(String tableName, Row row);
    void update(String tableName, Row row, Row oldRow);
    void delete(String tableName, Row row);
	void search(String query);
	void report(String query);
	void filterSort(String query ,JTable jt);
    void relations(String query,String tableName);

}
