package start;

import java.util.List;

import javax.swing.JTable;

import database.Database;
import database.IDatabase;
import database.MSSQLrepository;
import database.settings.ISettings;
import database.settings.Settings;
import errorHandling.ErrorHandler;
import errorHandling.ErrorHandlerView;
import model.TableModel;
import observer.Notification;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import resource.Row;
import resource.implementation.InformationResource;
import utils.Constants;

public class AppCore extends PublisherImplementation {

    private IDatabase database;
    private ISettings settings;
    private TableModel tableModel = null;
    private TableModel lowerTableModel = null;
    private ErrorHandler errorHandler;

    public AppCore() {
        initSettings();
        database = new Database(new MSSQLrepository(this.settings));
        errorHandler = ErrorHandler.getInstance();
        errorHandler.addSubscriber(new ErrorHandlerView());
    }

    private void initSettings() {
        ISettings settingsImplementation = new Settings();
        settingsImplementation.addParameter("mssql_ip", Constants.MSSQL_IP);
        settingsImplementation.addParameter("mssql_database", Constants.MSSQL_DATABASE);
        settingsImplementation.addParameter("mssql_username", Constants.MSSQL_USERNAME);
        settingsImplementation.addParameter("mssql_password", Constants.MSSQL_PASSWORD);
        settings = settingsImplementation;
    }

    public void loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        this.notifySubscribers(new Notification(NotificationCode.RESOURCE_LOADED,ir)); 
    }

    public List<Row> readDataFromTable(String tableName){ 					
        return database.readDataFromTable(tableName);
    }

    public TableModel getTableModel() {
        return tableModel;
    }

    public TableModel getLowerTableModel() {
        return lowerTableModel;
    }

	public void setTableModel(TableModel tableModel) {
		this.tableModel = tableModel;
	}
	
	public void setLowerTableModel(TableModel lowerTableModel) {
		this.lowerTableModel = lowerTableModel;
	}

	public void addRowToTable(String tableName, Row row) {
		database.addRowToTable(tableName, row);
	}
	
	public void updateTableRow(String tableName, Row row, Row oldRow) {
		database.updateTableRow(tableName, row, oldRow);
	}
	
	public void deleteTableRow(String tableName, Row row) {
		database.deleteTableRow(tableName, row);
	}
	
	
	public void searchTable(String query) {
		database.search(query);
	}
	
	public void reportTable(String query) {
		database.report(query);
	}
	public void relations(String query,String tableName) {
		database.relations(query, tableName);
	}
	public void sortFilterTable(String query , JTable jt) {
		database.filterSort(query, jt);
	}
	public ErrorHandler getErrorHandler() {
		return errorHandler;
	}

	
}

