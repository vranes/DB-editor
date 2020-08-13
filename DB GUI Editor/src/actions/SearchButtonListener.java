package actions;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import model.TableModel;
import resource.Row;
import view.frame.MainFrame;

public class SearchButtonListener implements ActionListener{
	String query ;
	boolean isString;
public void setString(String s1) {
	query = s1;
	
	System.out.println(query);
}
	@Override
	public void actionPerformed(ActionEvent e) {
	
		String[] options = new String[2];
		options[0] = new String("Lower");
		options[1] = new String("Upper");
		
		TableModel model = null;

		if((JOptionPane.showOptionDialog(MainFrame.getInstance(),"Choose which table to search","Search", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null)) == JOptionPane.OK_OPTION)
		{
			model = MainFrame.getInstance().getAppCore().getLowerTableModel();
		}else model = MainFrame.getInstance().getAppCore().getTableModel();
		
		
		String[] labels = new String[model.getColumnCount()];
		
		for(int i=0;i<model.getColumnCount();i++) {
			labels[i] = model.getColumnName(i);
		}
		
		
		String[] intSearch = { "<", "<=", ">", ">=", "=" }; 
		String[] stringSearch = {"STARTS WITH","ENDS WITH","CONTAINS","LIKE"};
		DefaultComboBoxModel cmbInt = new DefaultComboBoxModel(intSearch);
		DefaultComboBoxModel cmbStr = new DefaultComboBoxModel(stringSearch);

		Row row = model.getRows().get(0);
		HashMap<String, String> dataTypes = row.getDataTypes();
		    
		JComboBox columnBox = new JComboBox(labels);
		JComboBox opbox = new JComboBox();
		JTextField textField = new JTextField();
		textField.setMinimumSize(new Dimension(80, 30));
		textField.setEnabled(false);
		JButton btnAdd = new JButton("AND");
		JButton btnOR = new JButton("OR");
		JTextArea queryLabel = new JTextArea(10, 40);
       // textField.setPreferredSize(new Dimension(int,int));

		String[] options1 = new String[3];
	
		
		NumberFormat format = NumberFormat.getInstance();
		format.setGroupingUsed(false);//Remove comma from number greater than 4 digit
		NumberFormatter sleepFormatter = new NumberFormatter(format);
		sleepFormatter.setValueClass(Integer.class);
		sleepFormatter.setMinimum(0);
		sleepFormatter.setMaximum(3600);
		sleepFormatter.setAllowsInvalid(false);

		sleepFormatter.setCommitsOnValidEdit(true);
		
		 query = "SELECT * FROM "+model.getName()+" WHERE ";
		
		JPanel p = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(400, 50);
            }
        };
        JPanel bjp = new JPanel(new BorderLayout());
        
        
        JPanel jp = new JPanel();
        jp.add(queryLabel); 
		GroupLayout layout = new GroupLayout(p);
		 p.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setHorizontalGroup(
		   layout.createSequentialGroup()
		  
		      .addComponent(columnBox)
		      .addComponent(opbox)
		      .addComponent(textField)
		      .addComponent(btnAdd)
		      .addComponent(btnOR)
		    
		);
		layout.setVerticalGroup(
		   layout.createSequentialGroup()
		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    		  .addComponent(columnBox)
				      .addComponent(opbox)
				      .addComponent(textField)
				      .addComponent(btnAdd)
				      .addComponent(btnOR))
		);
		bjp.add(p, BorderLayout.PAGE_START);
		bjp.add(jp, BorderLayout.LINE_START);
		isString = false;
		queryLabel.setLineWrap(true);
		    columnBox.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
					textField.setEnabled(true);
					System.out.println(dataTypes.get((String)(columnBox.getSelectedItem())));

			        if (dataTypes.get((String)(columnBox.getSelectedItem())).equals("VARCHAR") || dataTypes.get((String)(columnBox.getSelectedItem())).equals("CHAR")){
			        	opbox.setModel(cmbStr);
			        	isString = true;
			        	    for( ActionListener al : textField.getActionListeners() ) {
			        	        textField.removeActionListener( al );
			        	    }
			        	
			        } else if(dataTypes.get((String)(columnBox.getSelectedItem())).equals("NUMERIC") || dataTypes.get((String)(columnBox.getSelectedItem())).equals("INTEGER")){
			        	opbox.setModel(cmbInt);
			        	isString = false;
			        	/*textField.addKeyListener(new KeyAdapter() {
			                public void keyPressed(KeyEvent ke) {
			                    if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
			                       textField.setEditable(true);
			                    } else {
			                       textField.setEditable(false);
			                    }
			                 }
			              });*.*/
			        }
			    }
			});
		   
		    btnAdd.addActionListener(new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
					

			        if (!textField.getText().equals("")){
			        	if(!isString)setString( query.concat(columnBox.getSelectedItem()+" "+opbox.getItemAt(opbox.getSelectedIndex())+" "+textField.getText())+" AND ");
			        	else setString( query.concat(columnBox.getSelectedItem()+" "+"LIKE"+" "+textField.getText())+" AND ");
			        	textField.setText("");	
			        	queryLabel.setText(query);
			        } 
			    }
			});
		    
		    btnOR.addActionListener(new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
					

			        if (!textField.getText().equals("")){
			        	setString(query.concat(columnBox.getSelectedItem()+" "+opbox.getItemAt(opbox.getSelectedIndex())+" "+textField.getText())+" OR ");
			        	textField.setText("");	
			        	queryLabel.setText(query);

			        } 
			    }
			});

		    if( JOptionPane.showConfirmDialog(null,bjp,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
			{
				System.out.println(query);
			while((query = query.substring(0, query.length()-1))!=" ") {
				if(query.substring(query.length()-1).equals(" "))break;
			}
			MainFrame.getInstance().getAppCore().searchTable(query);
			}
	}
}
