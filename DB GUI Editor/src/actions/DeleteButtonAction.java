package actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import model.TableModel;
import resource.Row;
import view.frame.MainFrame;

public class DeleteButtonAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		TableModel model = null;
		JTabbedPane jtp  = null;
		
		if(((JButton)e.getSource()).getName().equals("delete")) {
			jtp = MainFrame.getInstance().getUpperPane();
			model = MainFrame.getInstance().getAppCore().getTableModel();

		}else {
		     jtp = MainFrame.getInstance().getLowerPane();
			 model = MainFrame.getInstance().getAppCore().getLowerTableModel();
		}
		
		String tableName = jtp.getTitleAt(jtp.getSelectedIndex());
		JPanel jp = (JPanel)jtp.getSelectedComponent();
		JScrollPane jsp;
		JTable jt = null;
		String[] labels = new String[model.getColumnCount()];
		System.out.println(labels.length);

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
		Row row = new Row();
		row.setName(model.getName());
		for(int i=0;i<v.size();i++) {
			 String vstring = (String) v.get(i);
			    if(vstring == null) vstring = "";
				else vstring = v.get(i).toString();
			    row.addField(labels[i], vstring);		//pravljene redova iz vektor i imena kolona			
		}
		
		MainFrame.getInstance().getAppCore().deleteTableRow(tableName, row);
	}

}
