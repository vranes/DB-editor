package actions;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import model.TableModel;
import resource.EntityManager;
import resource.implementation.Entity;
import tree.DBtree;
import view.frame.MainFrame;

public class DoubleClickAction extends MouseAdapter{
	
	public void mousePressed(MouseEvent e) {
		
		 DefaultMutableTreeNode selectedNode = 	(DefaultMutableTreeNode)((DBtree) MainFrame.getInstance().getDBtree()).getLastSelectedPathComponent();

				 if (e.getClickCount() == 2 && selectedNode instanceof Entity) {
					 
					 EntityManager manager = EntityManager.getInstance();
					 Entity entity = (Entity)selectedNode;
					 
					 if (manager.isEntityOpen(entity)) {
						 int idx = MainFrame.getInstance().getUpperPane().indexOfTab(entity.getName());
						 MainFrame.getInstance().getUpperPane().setSelectedIndex(idx);
						 
						 return;
					 }
					 
					 TableModel model = entity.getModel();
					 if (model == null) {
						 model = new TableModel();
						 model.setRows(MainFrame.getInstance().getAppCore().readDataFromTable(entity.getName()));
						 model.setName(entity.getName());
						 entity.setModel(model);
					 }
					 
					 JTable jt = new JTable();
					 JPanel jp = new JPanel();

					 jt.setName(entity.getName());
					 jt.setModel(model);
	//TODO			 //jt.setPreferredScrollableViewportSize();
	//TODO			 //jt.setPreferredSize(preferredSize);
					 //jt.setPreferredScrollableViewportSize(new Dimension(100,100));
					 JScrollPane jsp = new JScrollPane(jt);
					 jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					 jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

					 jsp.setPreferredSize(MainFrame.getInstance().getUpperPane().getSize());
					 jp.add(jsp);
					 
					 MainFrame.getInstance().getUpperPane().addTab(entity.getName(), jp);
					 MainFrame.getInstance().getUpperPane().setSelectedComponent(jp);

					 manager.addEntity(entity); 
		}
    }
 }
	
