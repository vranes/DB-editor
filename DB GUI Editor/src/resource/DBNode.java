package resource;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;


public abstract class DBNode extends DefaultMutableTreeNode{

 
	private static final long serialVersionUID = 1L;
	
	private String name;
    private DBNode parent;

	public DBNode(String name, DBNode parent) {
		this.name = name;
		this.parent = parent;
	}
	
	public void remove(MutableTreeNode child) {}
	public void add(MutableTreeNode child) {}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public DBNode getParent() {
		return parent;
	}

	public void setParent(DBNode parent) {
		this.parent = parent;
	}
	
	public int getChildCount () {
		return 0;
	}
	
	public int getIndex (TreeNode child) {
		return -1;
	}
	
	public void remove(int index) {}
	
	public TreeNode getChildAt (int index) {
		return null;
	}
	
	@Override
	public void removeFromParent() {
		parent.remove(this);
		parent = null;
	}
	
	public List<DBNode> getChildren(){
		return null;
	}
   
}
