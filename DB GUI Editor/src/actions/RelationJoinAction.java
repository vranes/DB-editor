package actions;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import model.TableModel;
import resource.DBNode;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import start.Main;
import view.frame.MainFrame;

public class RelationJoinAction implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JTable jtUp = null;
		JScrollPane jsp = null;
		JPanel jp = (JPanel)MainFrame
				.getInstance().getUpperPane().getSelectedComponent();
		if(jp.getComponents() == null) return;
		for(Component c : jp.getComponents()) {
			if(c instanceof JScrollPane) {
				jsp = (JScrollPane)c;
				JViewport viewport = jsp.getViewport(); 		//izvalcenje jtabele
				jtUp = (JTable)viewport.getView();
			}
		}
		TableModel model = MainFrame.getInstance().getAppCore().getTableModel();
		
		int srow = jtUp.getSelectedRow();
		if(srow == -1) return;	//ovo se desi ako je pritisnut commit pre selektovanja reda
	
		String labele ="";
		String[] labels = new String[MainFrame.getInstance().getAppCore().getLowerTableModel().getColumnCount()];
		
		for(int i=0;i<MainFrame.getInstance().getAppCore().getLowerTableModel().getColumnCount();i++) {
			
			labele +=  MainFrame.getInstance().getAppCore().getLowerTableModel().getName()+"."+MainFrame.getInstance().getAppCore().getLowerTableModel().getColumnName(i)+",";
		}
		
		Vector v = (Vector) MainFrame.getInstance().getAppCore().getTableModel().getDataVector().elementAt(srow);
		HashMap<String, String> rowValues = new HashMap<String, String>();
		for(int j=0;j<v.size();j++) {
			rowValues.put(model.getColumnName(j), (String)v.get(j));
		}
		JTabbedPane pane = MainFrame.getInstance().getUpperPane();
        int index = pane.getSelectedIndex();
        String tableName = pane.getTitleAt(index);
        
        ArrayList<String> querys = new ArrayList<String>();
             		
        InformationResource ir = (InformationResource)MainFrame.getInstance().getDBtree().getModel().getRoot();	//root stabla da bismo nasli cvor selektovane tabele pa odatle cvor za svaku kolonu (Attribute)
        Entity entity = (Entity)ir.getChildByName(tableName);				//treba nam jer u Attribute imamo polje relationship za povezanu tabelu ako je Attribute FK
        MainFrame.getInstance().getAppCore().setTableModel(entity.getModel());
        HashMap<String, String>  qrs =  new HashMap<String, String>();

        for (DBNode node: entity.getChildren()) {				//Iteriramo kroz atribute tj kolone u tabeli
        	Attribute attribute = (Attribute)node;				//trazimo redom svaki Attribute node u Entity
        	Attribute relatedAttributeCpy = attribute.getRelationship();		//uzimamo related attribut ako ga ima
        	if (relatedAttributeCpy == null) continue;
        	Entity relatedEntityCpy = (Entity)relatedAttributeCpy.getParent();	//i trazimo njegovu home tabelu
        	String query = "SELECT *FROM "+relatedEntityCpy.getName()+" WHERE "+relatedAttributeCpy.getName()+
            "="+rowValues.get(attribute.getName());
        	String query1 = "SELECT "+ labele+" FROM " + relatedEntityCpy.getName() +" "
        			+ "INNER JOIN "+model.getName() + "ON " +relatedEntityCpy.getName()+"."
        			+attribute.getName()+"="+model.getName()+"."+attribute.getName();
        	querys.add(query1);
        	qrs.put(relatedEntityCpy.getName(), query);
			System.out.println(query1);
			if(MainFrame.getInstance().getAppCore().getLowerTableModel()
					.getName().equals(relatedEntityCpy.getName()))break;
			
        }
        MainFrame.getInstance().getAppCore().relations(qrs.get(MainFrame.getInstance().getAppCore().getLowerTableModel().getName()), 
        		MainFrame.getInstance().getAppCore().getLowerTableModel().getName());
	}

}
