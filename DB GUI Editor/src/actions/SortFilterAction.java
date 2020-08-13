package actions;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import model.TableModel;
import resource.Row;
import start.AppCore;
import start.Main;
import view.frame.MainFrame;

public class SortFilterAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JTable jt = null;
		JScrollPane jsp;
		TableModel model = null;
		
		String[] options = new String[2];
		options[0] = new String("Lower");
		options[1] = new String("Upper");
		System.out.println("Lower");

		 model = null;
		 int k = JOptionPane.showOptionDialog(MainFrame.getInstance(),"Choose which table to filter/sort","Filter/Sort", 0,JOptionPane.INFORMATION_MESSAGE,null,options,null);
		if( k == 0)
		{
			System.out.println("Lower");

			model = MainFrame.getInstance().getAppCore().getLowerTableModel();
			JPanel jp = (JPanel)MainFrame
					.getInstance().getLowerPane().getSelectedComponent();
			
			for(Component c : jp.getComponents()) {
				if(c instanceof JScrollPane) {
					jsp = (JScrollPane)c;
					JViewport viewport = jsp.getViewport(); 		//izvalcenje jtabele
					jt = (JTable)viewport.getView();
					
				}
			}

		}else if(k == 1){
			model = MainFrame.getInstance().getAppCore().getTableModel();
			System.out.println("Upper");

			JPanel jp = (JPanel)MainFrame
					.getInstance().getUpperPane().getSelectedComponent();
			
			for(Component c : jp.getComponents()) {
				if(c instanceof JScrollPane) {
					jsp = (JScrollPane)c;
					JViewport viewport = jsp.getViewport(); 		//izvalcenje jtabele
					jt = (JTable)viewport.getView();
					
				}
			}
		}
		else return;
	
		
		String[] labels = new String[model.getColumnCount()];
		String[] op = {"ASC","DESC"};
		DefaultComboBoxModel cmbop = new DefaultComboBoxModel(op);

		JComboBox opBox = new JComboBox();
		opBox.setModel(cmbop);
		
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
		    JCheckBox jcbox = new JCheckBox();
		    jcbox.setName(labels[i]);
		//    textField.setName(labels[i]);
		    p.add(jcbox,c);
		    c.gridx = 2;
		    JComboBox jcmb = new JComboBox();
		    jcmb.setModel(new DefaultComboBoxModel(op));
		    jcmb.setName(labels[i]);
		    p.add(jcmb,c);
		    
		    jcmb.setEnabled(false);
		    jcbox.addActionListener(new ActionListener () {
			    public void actionPerformed(ActionEvent e) {
			    	if(((JCheckBox)e.getSource()).isSelected())jcmb.setEnabled(true);
			    	else jcmb.setEnabled(false);

			    }
			});
		    
		}
		
		if( JOptionPane.showConfirmDialog(null,p,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
		{
			String filter = "SELECT ";
			String sort = "ORDER BY ";
			int n=0;
			
			for(int i=0; i<p.getComponentCount(); i++) {
				if(p.getComponent(i) instanceof JCheckBox && ((JCheckBox)p.getComponent(i)).isSelected()) {
					filter = filter.concat(p.getComponent(i).getName()+", ");
				}
				if(p.getComponent(i) instanceof JComboBox && ((JComboBox)p.getComponent(i)).isEnabled()) {
					sort = sort.concat(p.getComponent(i).getName()+" "+((JComboBox)p.getComponent(i)).getSelectedItem()+", ");
				}
			}
			
			String query = filter.substring(0,filter.length()-2) +" FROM "+model.getName()+" "+ sort.substring(0,sort.length()-2);
			System.out.println(jt.getName());
			if(filter.equals("SELECT "))query ="SELECT *FROM "+model.getName();
			
			MainFrame.getInstance().getAppCore().sortFilterTable(query, jt);
			
		}
			

			
		
		}
		
	}
	

