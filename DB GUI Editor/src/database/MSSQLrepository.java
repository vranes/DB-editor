package database;

import database.settings.ISettings;
import model.TableModel;
import resource.DBNode;
import resource.Row;
import resource.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.AttributeConstraint;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import view.frame.MainFrame;
import resource.enums.ConstraintType;

import java.awt.Component;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableModel;

public class MSSQLrepository implements IRepository{

    private ISettings settings;
    private Connection connection;

    public MSSQLrepository(ISettings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException{
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        String ip = (String) settings.getParameter("mssql_ip");
        String database = (String) settings.getParameter("mssql_database");
        String username = (String) settings.getParameter("mssql_username");
        String password = (String) settings.getParameter("mssql_password");
        Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:jtds:sqlserver://"+ip+"/"+database,username,password);
    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (Exception e){
        	MainFrame.getInstance().getAppCore().getErrorHandler().CloseConnectionException();
        }
        finally {
            connection = null;
        }
    }


    @Override
    public DBNode getSchema() {

        try{
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("TIM_10");

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), "dbo", null, tableType);

            while (tables.next()){

                String tableName = tables.getString("TABLE_NAME");
                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);
                
                while (columns.next()){
                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");
                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    Attribute attribute = new Attribute(columnName, newTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    newTable.addChild(attribute);
                    
                    String isNullable = columns.getString("IS_NULLABLE");
                    if (isNullable.contentEquals("NO")) {
                    	AttributeConstraint constraint = new AttributeConstraint("NOT NULL", attribute, ConstraintType.NOT_NULL);
                    	attribute.addChild(constraint);
                    }
                    String hasDefault = columns.getString("COLUMN_DEF");
                    if (hasDefault != null) {
                    	AttributeConstraint constraint = new AttributeConstraint("DEFAULT VALUE", attribute, ConstraintType.DEFAULT_VALUE);
                    	attribute.addChild(constraint);
                    }
                    String dataType = columns.getString("TYPE_NAME");
                    if (!isValidDataType(dataType)) {
                    	AttributeConstraint constraint = new AttributeConstraint("DOMAIN VALUE", attribute, ConstraintType.DOMAIN_VALUE);
                    	attribute.addChild(constraint);
                    }
                    ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);
                    while (foreignKeys.next()) {
                    	if (columnName.equals(foreignKeys.getString("FKCOLUMN_NAME"))) {
                    		AttributeConstraint constraint = new AttributeConstraint("FOREIGN KEY", attribute, ConstraintType.FOREIGN_KEY);
                    		attribute.addChild(constraint);
                    		
                    		String homeTableName = foreignKeys.getString("PKTABLE_NAME");
                    		String homeColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                    		Entity homeTable = new Entity(homeTableName, ir);
                    		Attribute homeColumn = new Attribute(homeColumnName, homeTable, AttributeType.valueOf(columnType.toUpperCase()), columnSize);
                    		attribute.setRelationship(homeColumn);
                    		break;
                    	}
                    }
                    ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
                    while (primaryKeys.next()) {
                    	if (columnName.equals(primaryKeys.getString("COLUMN_NAME"))) {
                    		AttributeConstraint constraint = new AttributeConstraint("PRIMARY KEY", attribute, ConstraintType.PRIMARY_KEY);
                    		attribute.addChild(constraint);
                    		break;
                    	}
                    }
                }
            }		

            return ir;
        }
        catch (Exception e) {
        	MainFrame.getInstance().getAppCore().getErrorHandler().LoadException();
        }
        finally {
            this.closeConnection();
        }

        return null;
    }
    
    private boolean isValidDataType(String dataType) {
		dataType = dataType.toUpperCase();
		if (dataType.equals(AttributeType.BIGINT.toString()) || dataType.equals(AttributeType.BIT.toString())
				|| dataType.equals(AttributeType.CHAR.toString()) || dataType.equals(AttributeType.DATE.toString())
				|| dataType.equals(AttributeType.DATETIME.toString()) || dataType.equals(AttributeType.DECIMAL.toString())
				|| dataType.equals(AttributeType.FLOAT.toString()) || dataType.equals(AttributeType.IMAGE.toString())
				|| dataType.equals(AttributeType.INT.toString()) || dataType.equals(AttributeType.NUMERIC.toString())
				|| dataType.equals(AttributeType.NVARCHAR.toString()) || dataType.equals(AttributeType.REAL.toString())
				|| dataType.equals(AttributeType.SMALLINT.toString()) || dataType.equals(AttributeType.TEXT.toString())
				|| dataType.equals(AttributeType.TIME.toString()) || dataType.equals(AttributeType.VARCHAR.toString()))
			return true;
		return false;
	}

	@Override
    public List<Row> get(String tableName) {
		HashMap<String, String> dataTypes =  new HashMap<String, String>();
        List<Row> rows = new ArrayList<>();
        try{
            this.initConnection();
           
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);
    		
    		while (columns.next()) {
            	dataTypes.put(columns.getString("COLUMN_NAME"), columns.getString("TYPE_NAME").toUpperCase());
    		}
            
            String query = "SELECT * FROM " + tableName;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            
        	 
            while (rs.next()){
            	
                Row row = new Row();
                row.setName(tableName);
                row.setDataTypes(dataTypes);
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
        	MainFrame.getInstance().getAppCore().getErrorHandler().LoadException();
        }
        finally {
            this.closeConnection();
        }
        return rows;
    }

	@Override
	public void add(String tableName, Row row) {
		 try{
	            this.initConnection();

	            String query = "INSERT INTO " + tableName + " " + row.keysToString() + " values " + row.valuesToString();
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.executeUpdate();
	        }								
	        catch (Exception e) {
	        	if (e instanceof SQLException) {
	        		MainFrame.getInstance().getAppCore().getErrorHandler().customException(e.getMessage());
	        	} else {
	        		 MainFrame.getInstance().getAppCore().getErrorHandler().AddException();
	        	}
	        }
	        finally {
	            this.closeConnection();
	        }
	}

	@Override
	public void update(String tableName, Row row, Row oldRow) {
		 try{
	            this.initConnection();
	            StringBuilder sb = new StringBuilder("UPDATE " + tableName + " SET ");
	    		for (String key : row.getFields().keySet()) {
	    			sb.append(key + " = '" + row.getFields().get(key) + "', ");
	    		}
	    		sb.delete(sb.length()-2, sb.length());
	    		sb.append(" WHERE ");
	    		
	    		DatabaseMetaData metaData = connection.getMetaData();
	    		ResultSet pk = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
	    		ArrayList <String> primaryKeys = new ArrayList<String>();
                while (pk.next())
                	primaryKeys.add(pk.getString("COLUMN_NAME"));
                
                //prodjemo kroz sve tabele i uzmemo njihove ImportedKeys, ako sadrze PK iz ove tabele radimo pretvaranje njihovih vrednosti na null   
                String tableType[] = {"TABLE"};
                ResultSet tables = metaData.getTables(connection.getCatalog(), "dbo", null, tableType);
                
                while (tables.next()){
                    String name = tables.getString("TABLE_NAME");
                    ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, name);
                 
                    while (foreignKeys.next()) {
                    	 String homeTableName = foreignKeys.getString("PKTABLE_NAME");
                 		 String homeColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                 		 if (!homeTableName.equals(tableName)) continue;
                 		 for (String pkName: primaryKeys) {
                 			 if (pkName.equals(homeColumnName)) {
                 				 String hostTableName = foreignKeys.getString("FKTABLE_NAME");
                         		 String hostColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                 				 String q = "UPDATE " + hostTableName + " SET " + hostColumnName + " = null where " + hostColumnName + " = '" + oldRow.getFields().get(pkName) + "'";
                 				// String q = "UPDATE " + hostTableName + " SET " + hostColumnName + " = '" + row.getFields().get(pkName) + "' where " + hostColumnName + " = '" + oldRow.getFields().get(pkName) + "'";
                 				 System.out.println(q);
                 				 PreparedStatement ps = connection.prepareStatement(q);  //query za azuriranje vrednosti u drugim tabelama
                 	             ps.executeUpdate();
                 			 } 
                 		 }
                    } 
                }
                
                for (String key: primaryKeys) {
                	sb.append(key + " = '" + oldRow.getFields().get(key) + "' AND ");
                }
                sb.delete(sb.length()-5, sb.length());
	            
	    		String query = sb.toString();
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.executeUpdate();
	            
	            
	            tables = metaData.getTables(connection.getCatalog(), "dbo", null, tableType);
                
                while (tables.next()){
                    String name = tables.getString("TABLE_NAME");
                    ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, name);
                 
                    while (foreignKeys.next()) {
                    	 String homeTableName = foreignKeys.getString("PKTABLE_NAME");
                 		 String homeColumnName = foreignKeys.getString("PKCOLUMN_NAME");
                 		 if (!homeTableName.equals(tableName)) continue;
                 		 for (String pkName: primaryKeys) {
                 			 if (pkName.equals(homeColumnName)) {
                 				 String hostTableName = foreignKeys.getString("FKTABLE_NAME");
                         		 String hostColumnName = foreignKeys.getString("FKCOLUMN_NAME");
                 				 //String q = "UPDATE " + hostTableName + " SET " + hostColumnName + " = null where " + hostColumnName + " = '" + oldRow.getFields().get(pkName) + "'";
                 				 String q = "UPDATE " + hostTableName + " SET " + hostColumnName + " = '" + row.getFields().get(pkName) + "' where " + hostColumnName + " IS NULL" ;
                 				// System.out.println(q);
                 				 PreparedStatement ps = connection.prepareStatement(q);  //query za azuriranje vrednosti u drugim tabelama
                 	             ps.executeUpdate();
                 			 } 
                 		 }
                    } 
                }
	        }
	        catch (Exception e) {
	        	if (e instanceof SQLException) {
	        		MainFrame.getInstance().getAppCore().getErrorHandler().customException(e.getMessage());
	        	} else {
	        	MainFrame.getInstance().getAppCore().getErrorHandler().UpdateException();
	        	}
	        }
	        finally {
	            this.closeConnection();
	        }
	}

	@Override
	public void delete(String tableName, Row row) {
		try{
            this.initConnection();

            StringBuilder sb = new StringBuilder("DELETE FROM " + tableName + " WHERE ");
            
            DatabaseMetaData metaData = connection.getMetaData();
    		ResultSet pk = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
    		ArrayList <String> primaryKeys = new ArrayList<String>();
            while (pk.next())
            	primaryKeys.add(pk.getString("COLUMN_NAME"));
  
            //prodjemo kroz sve tabele i uzmemo njihove ImportedKeys, ako sadrze PK iz ove tabele radimo pretvaranje njihovih vrednosti na null

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), "dbo", null, tableType);
            while (tables.next()){
                String name = tables.getString("TABLE_NAME");
                ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, name);
             
                while (foreignKeys.next()) {
                	 String homeTableName = foreignKeys.getString("PKTABLE_NAME");
             		 String homeColumnName = foreignKeys.getString("PKCOLUMN_NAME");
             		 if (!homeTableName.equals(tableName)) continue;
             		 for (String pkName: primaryKeys) {
             			 if (pkName.equals(homeColumnName)) {
             				 String hostTableName = foreignKeys.getString("FKTABLE_NAME");
                     		 String hostColumnName = foreignKeys.getString("FKCOLUMN_NAME");
             				 String q = "UPDATE " + hostTableName + " SET " + hostColumnName + " = null where " + hostColumnName + " = '" + row.getFields().get(pkName) + "'";
             				 System.out.println(q);
             				 PreparedStatement ps = connection.prepareStatement(q);  //query za pretvaranje u null
             	             ps.executeUpdate();
             			 } 
             		 }
                }
                
                
            }
            
            for (String key: primaryKeys) {
            	sb.append(key + " = '" + row.getFields().get(key) + "' and ");
            }
            sb.delete(sb.length()-5, sb.length());
            System.out.println(sb.toString());
    		String query = sb.toString();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        }
        catch (Exception e) {
        	if (e instanceof SQLException) {
        		MainFrame.getInstance().getAppCore().getErrorHandler().customException(e.getMessage());
        	} else {
        	MainFrame.getInstance().getAppCore().getErrorHandler().DeleteException();
        	}
        }
        finally {
            this.closeConnection();
        }
		
	}

	@Override
	public void search(String query) {
		try{
            this.initConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            JPanel jp = new JPanel();
            JTable jt =  new JTable(buildTableModel(rs));
            jp.add(jt);
            
            JOptionPane.showConfirmDialog(null,jp,"Search result",JOptionPane.OK_OPTION) ;
			
			
        }
        catch (Exception e) {
        	if (e instanceof SQLException) {
        		MainFrame.getInstance().getAppCore().getErrorHandler().customException(e.getMessage());
        	} else {
        	MainFrame.getInstance().getAppCore().getErrorHandler().SearchException();
        	}	
        }
        finally {
            this.closeConnection();
        }		
	}
	
	@Override
	public void report(String query) {
		try{
            this.initConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            JPanel jp = new JPanel();
            JTable jt =  new JTable(buildTableModel(rs));
            jp.add(jt);
            
            JOptionPane.showConfirmDialog(null,jp,"Search result",JOptionPane.OK_OPTION) ;
			
			
        }
        catch (Exception e) {
        	if (e instanceof SQLException) {
        		MainFrame.getInstance().getAppCore().getErrorHandler().customException(e.getMessage());
        	} else {
        	MainFrame.getInstance().getAppCore().getErrorHandler().ReportException();
        	}
        }
        finally {
            this.closeConnection();
        }		
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) {
	        columnNames.add(metaData.getColumnName(column));
	    }

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}

	@Override
	public void filterSort(String query, JTable jt) {
		HashMap<String, String> dataTypes =  new HashMap<String, String>();
        List<Row> rows = new ArrayList<>();
        try{
            this.initConnection();
           
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(connection.getCatalog(), null, jt.getName(), null);
    		
    		while (columns.next()) {
            	dataTypes.put(columns.getString("COLUMN_NAME"), columns.getString("TYPE_NAME").toUpperCase());
    		}
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            
        	 
            while (rs.next()){
            	
                Row row = new Row();
                row.setName(jt.getName());
                row.setDataTypes(dataTypes);
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
        	if (e instanceof SQLException) {
        		MainFrame.getInstance().getAppCore().getErrorHandler().customException(e.getMessage());
        	} else {
        	MainFrame.getInstance().getAppCore().getErrorHandler().FilterException();
        	}
        }
        finally {
            this.closeConnection();
        }
      TableModel tm = new TableModel();
      
      tm.setRowCount(rows.size());
      tm.setRows(rows);
      tm.setName(jt.getName());
      jt.setModel(tm);
		
	}

	@Override
	public void relations(String query, String tableName) {
		HashMap<String, String> dataTypes =  new HashMap<String, String>();
		System.out.println(query+" "+tableName);
        List<Row> rows = new ArrayList<>();
        try{
            this.initConnection();
           
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);
    		
    		while (columns.next()) {
            	dataTypes.put(columns.getString("COLUMN_NAME"), columns.getString("TYPE_NAME").toUpperCase());
    		}
            
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            
        	 
            while (rs.next()){
            	
                Row row = new Row();
                row.setName(tableName);
                row.setDataTypes(dataTypes);
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                    System.out.println( rs.getString(i));
                }
                rows.add(row);
            }
        }
        catch (Exception e) {
        	if (e instanceof SQLException) {
        		MainFrame.getInstance().getAppCore().getErrorHandler().customException(e.getMessage());
        	} else {
        	MainFrame.getInstance().getAppCore().getErrorHandler().RelationsException();
        	}
        }
        finally {
            this.closeConnection();
        }
      TableModel tm = new TableModel();
      tm.setRowCount(rows.size());
      tm.setRows(rows);
      tm.setName(tableName);
      
  	JTable jtUp = null;
	JScrollPane jsp = null;
	JPanel jp = (JPanel)MainFrame
			.getInstance().getLowerPane().getSelectedComponent();
	if(jp.getComponents() == null) return;
	for(Component c : jp.getComponents()) {
		if(c instanceof JScrollPane) {
			jsp = (JScrollPane)c;
			JViewport viewport = jsp.getViewport(); 		//izvalcenje jtabele
			jtUp = (JTable)viewport.getView();
		}
	}
    jtUp.setModel(tm);
	}
}


