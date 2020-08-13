package tree;

import java.awt.Component;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.InformationResource;

public class DBtreeCellRenderer extends DefaultTreeCellRenderer{

	
	private static final long serialVersionUID = 1L;
	
	Object paint = null;
	public DBtreeCellRenderer() {


	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf,int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		 
		if (value instanceof InformationResource ) {
			URL imageURL = getClass().getResource("icons/Workspace.png");
            Icon icon = null;
            if (imageURL != null) {
            	icon = new ImageIcon(imageURL);
            }
                
            setIcon(icon);

        } else if (value instanceof DBNodeComposite ) {
        	URL  imageURL = getClass().getResource("icons/Project.png");
            Icon icon = null;
            if (imageURL != null) {
            	icon = new ImageIcon(imageURL);
            }
                
            setIcon(icon);
              
       } else if (value instanceof DBNode ) {
    	   URL  imageURL = getClass().getResource("icons/Document.png");
           Icon icon = null;
           if (imageURL != null)                       
               icon = new ImageIcon(imageURL);
           setIcon(icon);
        
       }
	return this;
	}

}


