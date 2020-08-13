package resource;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

public abstract class DBNodeComposite extends DBNode{

    private List<DBNode> children;

    public DBNodeComposite(String name, DBNode parent) {
        super(name, parent);
        this.children = new ArrayList<>();
    }

    public DBNodeComposite(String name, DBNode parent, ArrayList<DBNode> children) {
        super(name, parent);
        this.children = children;
    }

    public abstract void addChild(DBNode child);

    public DBNode getChildByName(String name) {
        for (DBNode child: this.getChildren()) {
            if (name.equals(child.getName())) {
                return child;
            }
        }
        return null;
    }
    
    public void add(MutableTreeNode child) {
    	addChild((DBNode)child);
    }
    
	public List<DBNode> getChildren() {
		return children;
	}

	public void setChildren(List<DBNode> children) {
		this.children = children;
	}
	
	public int getChildCount () {
		return children.size();
	}
	
	public int getIndex (TreeNode child) {
		return children.indexOf((DBNode)child);
	}
	
	public void remove(int index) {
		children.remove(index);
	}
	
	public void remove(MutableTreeNode child) {
		children.remove((DBNode)child);
	}
	
	public TreeNode getChildAt (int index) {
		return children.get(index);
	}

}
