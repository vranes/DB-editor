package database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JTable;

import resource.DBNode;
import resource.Row;

public interface IDatabase{

    DBNode loadResource();
    List<Row> readDataFromTable(String tableName);
    void addRowToTable(String tableName, Row row);
    void updateTableRow(String tableName, Row row, Row oldRow);
    void deleteTableRow(String tableName, Row row);
	void search(String query);
	void report(String query);
	void filterSort(String query ,JTable jt);
    void relations(String query,String tableName);
}