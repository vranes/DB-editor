package tree;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreePath;

public class DBtreeController implements TreeSelectionListener{

	@Override
	public void valueChanged(TreeSelectionEvent e) {
		if (e.isAddedPath()) {
			TreePath path = e.getPath();
			//System.out.println(path.toString());
			}		
	}

}

