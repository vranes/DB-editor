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

public class ReportButtonListener implements ActionListener{
	String query ;
	String select;
	TableModel model;
public void setString(String s1) {
	query = s1;
	
	System.out.println(query);
}
	@Override
	public void actionPerformed(ActionEvent e) {
	
		String[] options = new String[2];
		options[0] = new String("Lower");
		options[1] = new String("Upper");
		
		 model = null;
		 int k = JOptionPane.showOptionDialog(MainFrame.getInstance(),"Make a report","Report", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
		if(k == 0)
		{
			model = MainFrame.getInstance().getAppCore().getLowerTableModel();
		}else if(k == 1) model = MainFrame.getInstance().getAppCore().getTableModel();
		else return;
		
		String[] labels = new String[model.getColumnCount()];
		
		for(int i=0;i<model.getColumnCount();i++) {
			labels[i] = model.getColumnName(i);
		}
				
		String[] report = { "AVG", "COUNT"}; 
	
		DefaultComboBoxModel cmbReport = new DefaultComboBoxModel(report);	    
		JComboBox columnBox = new JComboBox(labels);
		JComboBox reportBox = new JComboBox(report);
		JComboBox groupBox = new  JComboBox(labels);
		JTextArea queryLabel = new JTextArea(10, 40);
		queryLabel.setLineWrap(true);

		JButton btnAdd = new JButton("ADD");
		JButton btnOR = new JButton("OR");
		
		columnBox.setEnabled(false);
		
		String[] options1 = new String[3];
	
		 query = "";
		 select = "";
		 
		
		JPanel p = new JPanel(){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(500, 50);
            }
        };
        
        JPanel jp = new JPanel();
        jp.add(queryLabel);
        
        JPanel bjp = new JPanel(new BorderLayout());
        
		GroupLayout layout = new GroupLayout(p);
		 p.setLayout(layout);

		layout.setAutoCreateGaps(true);
		//layout.setAutoCreateContainerGaps(true);
		layout.setHorizontalGroup(
		   layout.createSequentialGroup()
		      .addComponent(reportBox)
		      .addComponent(columnBox)
		      .addComponent(groupBox)
		      .addComponent(btnAdd)
		     
		    
		);
		layout.setVerticalGroup(
		   layout.createSequentialGroup()
		      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    		  .addComponent(reportBox)
				      .addComponent(columnBox)
				      .addComponent(groupBox)
				      .addComponent(btnAdd))
		);
		bjp.add(p, BorderLayout.PAGE_START);
		bjp.add(jp, BorderLayout.LINE_START);

		    columnBox.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			    	select = select.concat("("+columnBox.getSelectedItem()+")");
			    	queryLabel.setText("SELECT "+select+" FROM "+model.getName());
					columnBox.setEnabled(false);
			    	bjp.repaint();

			    }
			});
		    reportBox.addActionListener (new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			    	columnBox.setEnabled(true);
			    	select =  (String)reportBox.getSelectedItem();
					reportBox.setEnabled(false);
					bjp.repaint();
			    }
			});
		    
		    btnAdd.addActionListener(new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
					

			        if (!columnBox.isEditable() && !reportBox.isEditable()){
			        	select = groupBox.getSelectedItem()+", "+select;
			        	query = groupBox.getSelectedItem()+", "+query;
			        	queryLabel.setText("SELECT "+select+" FROM "+model.getName()+" GROUP BY "+query);
			        } 
			    }
			});
		    

		    if( JOptionPane.showConfirmDialog(null,bjp,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
			{
		    	if(query.contentEquals("")) {
			    	query = "SELECT "+select+" FROM "+model.getName();

		    	}else {
		    		query = "SELECT "+select+" FROM "+model.getName()+" GROUP BY "+query;
			    	query = query.substring(0, query.length()-2);
		    	}
		    	
			//	System.out.println(query);
				MainFrame.getInstance().getAppCore().reportTable(query);
			}
	}
}
