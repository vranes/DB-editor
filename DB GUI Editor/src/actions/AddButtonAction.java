package actions;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import model.TableModel;
import resource.Row;
import start.AppCore;
import view.frame.MainFrame;

public class AddButtonAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		TableModel model = null;
		
		if(((JButton)e.getSource()).getName().equals("add")) {
			model = MainFrame.getInstance().getAppCore().getTableModel();
		
		}else {
			model = MainFrame.getInstance().getAppCore().getLowerTableModel();
		}
		
		String[] labels = new String[model.getColumnCount()];
		
		for(int i=0;i<model.getColumnCount();i++) {
			labels[i] = model.getColumnName(i);
		}
		
		int numPairs = model.getColumnCount();
		//Create and populate the panel.
			
			GridBagConstraints c = new GridBagConstraints();
			c.insets = new Insets(10, 10, 10, 0);
		    c.fill = GridBagConstraints.HORIZONTAL;	        
		   // c.weightx = c.weighty = 0.5;
		
		JPanel p = new JPanel(new GridBagLayout());
		for (int i = 0; i < numPairs; i++) {
			c.gridx = 0;
		    c.gridy = i;
		    JLabel l = new JLabel(labels[i]+" :");
		    p.add(l,c);
		    c.gridx = 1;
		    JTextField textField = new JTextField(15);
		    textField.setName(labels[i]);
		    p.add(textField,c);
		}
		
		if( JOptionPane.showConfirmDialog(null,p,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		{
			int n=0;
			Row row = new Row();
			row.setName(model.getName());
			for(int i=0; i<p.getComponentCount(); i++) {
				if(p.getComponent(i) instanceof JTextField) {
					row.addField(labels[n++], ((JTextField)p.getComponent(i)).getText());
				}
			}
			
			MainFrame.getInstance().getAppCore().addRowToTable(row.getName(), row);
			
		}
			

			
		
		}
		
	}
	

