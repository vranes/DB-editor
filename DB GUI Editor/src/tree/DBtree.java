package tree;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import observer.MyObservableInterface;
import observer.MyObserverInterface;

public class DBtree extends JTree implements MyObserverInterface{

	private static final long serialVersionUID = 1L;
	
	public DBtree() {
		setCellEditor(new DefaultTreeCellEditor(this, new DefaultTreeCellRenderer()));
		addTreeSelectionListener(new DBtreeController()); 
		setCellRenderer(new DBtreeCellRenderer());
	    setEditable(true);
	  //  addMouseListener(MainFrame.getInstance().getActionManager().getRightClickAction());
	}
	
	@Override
	public void update(MyObservableInterface o, Object arg) {
		update();

	}
	public void update(){
		  SwingUtilities.updateComponentTreeUI(this);
	}
}


/*
public void update(){
	  SwingUtilities.updateComponentTreeUI(this);
}

@Override
public void update(MyObservableInterface o, Object arg) {
	update();
	
	if (arg instanceof MutableTreeNode)
		clearSelection();

}


}*/
