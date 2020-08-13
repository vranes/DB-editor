package actions;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import model.TableModel;
import resource.Row;
import view.frame.MainFrame;

public class UpdateButtonAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		TableModel model = null;
		JTabbedPane jtp  = null;
		
		if(((JButton)e.getSource()).getName().equals("update")) {
			jtp = MainFrame.getInstance().getUpperPane();
			model = MainFrame.getInstance().getAppCore().getTableModel();

		}else {
		     jtp = MainFrame.getInstance().getLowerPane();
			 model = MainFrame.getInstance().getAppCore().getLowerTableModel();
		}
		
		JPanel jp = (JPanel)jtp.getSelectedComponent();
		String tableName = jtp.getTitleAt(jtp.getSelectedIndex());
		JScrollPane jsp;
		JTable jt = null;
		String[] labels = new String[model.getColumnCount()];

		for(int i=0;i<model.getColumnCount();i++) {
			labels[i] = model.getColumnName(i);			//nazivi kolona
		}
	
		for(Component c : jp.getComponents()) {
			if(c instanceof JScrollPane) {
				jsp = (JScrollPane)c;
				JViewport viewport = jsp.getViewport(); 		//izvalcenje jtabele
				jt = (JTable)viewport.getView();
				
			}
		}

		if(jt == null) return;	//ovo se desi ako je pritisnnut commit pre otvaranja tabela 

		int srow = jt.getSelectedRow();
		if(srow == -1) return;	//ovo se desi ako je pritisnut commit pre selektovanja reda

		Vector v = (Vector) model.getDataVector().elementAt(srow);
		Row oldRow = new Row();
		oldRow.setName(model.getName());


		for(int i=0;i<v.size();i++) {
			String vstring = (String) v.get(i);
			if(vstring == null) vstring = "";
			else vstring = v.get(i).toString();
			oldRow.addField(labels[i], vstring);		//pravljene redova iz vektor i imena kolona			
			
		}
		
		GridBagConstraints c = new GridBagConstraints();
		int numPairs = model.getColumnCount();

		JPanel p = new JPanel(new GridBagLayout());
		for (int i = 0; i < numPairs; i++) {
			c.gridx = 0;
	        c.gridy = i;
		    JLabel l = new JLabel(labels[i]+" :");		//generate form
		    p.add(l,c);
		    c.gridx = 1;
		    JTextField textField = new JTextField(15);
		    String vstring = (String) v.get(i);
		    if(vstring == null) vstring = "";
			else vstring = v.get(i).toString();
		    textField.setText(vstring);
		    textField.setName(labels[i]);
		    p.add(textField,c);
		}

		if(JOptionPane.showConfirmDialog(null,p,"Fill this form to add",JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
	    {
	    	int n=0;
	    	Row newrow = new Row();
	    	newrow.setName(model.getName());
	    	for(int i=0; i<p.getComponentCount(); i++) {		//generate new row
	    		if(p.getComponent(i) instanceof JTextField) {
	    			newrow.addField(labels[n++], ((JTextField)p.getComponent(i)).getText());
	    		}
	    	}
	    	MainFrame.getInstance().getAppCore().updateTableRow(tableName, newrow, oldRow);
	    }
	}
	
}
