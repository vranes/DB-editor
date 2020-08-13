package actions;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.TableModel;
import resource.DBNode;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import view.frame.MainFrame;

public class UpperPaneChangeListener implements ChangeListener{

	@Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JTabbedPane) {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            int index = pane.getSelectedIndex();
            String tableName = pane.getTitleAt(index);
            
            MainFrame.getInstance().closeLowerPane();
            
            InformationResource ir = (InformationResource)MainFrame.getInstance().getDBtree().getModel().getRoot();	//root stabla da bismo nasli cvor selektovane tabele pa odatle cvor za svaku kolonu (Attribute)
            Entity entity = (Entity)ir.getChildByName(tableName);				//treba nam jer u Attribute imamo polje relationship za povezanu tabelu ako je Attribute FK
            MainFrame.getInstance().getAppCore().setTableModel(entity.getModel());
            
            for (DBNode node: entity.getChildren()) {				//Iteriramo kroz atribute tj kolone u tabeli
            	Attribute attribute = (Attribute)node;				//trazimo redom svaki Attribute node u Entity
            	Attribute relatedAttributeCpy = attribute.getRelationship();		//uzimamo related attribut ako ga ima
            	if (relatedAttributeCpy == null) continue;
            	Entity relatedEntityCpy = (Entity)relatedAttributeCpy.getParent();	//i trazimo njegovu home tabelu
            	
            	Entity relatedEntity = (Entity)ir.getChildByName(relatedEntityCpy.getName());
            	TableModel model = relatedEntity.getModel();
				if (model == null) {
					 model = new TableModel();
					 model.setRows(MainFrame.getInstance().getAppCore().readDataFromTable(relatedEntity.getName()));
					 model.setName(relatedEntity.getName());
					 relatedEntity.setModel(model);
				}
            	
            	JTable jt = new JTable();
				jt.setName(relatedEntity.getName());
				jt.setModel(model);
				JPanel jp = new JPanel();
				JScrollPane jsp = new JScrollPane(jt);
				jsp.setPreferredSize(MainFrame.getInstance().getLowerPane().getSize());
				jp.add(jsp);
				 
				MainFrame.getInstance().getLowerPane().addTab(relatedEntity.getName(), jp);
				MainFrame.getInstance().getLowerPane().setSelectedComponent(jp);
            }
        }
    }

}
