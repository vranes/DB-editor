package observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;


public class MyObservableNode extends DefaultMutableTreeNode implements MyObservableInterface, Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient ArrayList <MyObserverInterface> observers = new ArrayList<>();
	protected ArrayList <MyObservableNode> children = new ArrayList<MyObservableNode>();
	protected ArrayList <MyObservableNode> parents = new ArrayList<>();
	protected String name = null;
	boolean changed = false;
	boolean saved = false;
	boolean firstSave = false;
	
	public void setName (String name) {
		this.name = name;
		setChanged();
		this.notifyObservers(name);
		this.registerChange();
	}
	
	private Object readResolve() {
		observers = new ArrayList<MyObserverInterface>();
		return this;
	}
	
	public void addObserver(MyObserverInterface o) {
		if (observers == null) observers = new ArrayList<>();
		observers.add(o);
	}
	
	public void deleteObserver(MyObserverInterface o) {
		observers.remove(o);
	}
	
	public void notifyObservers() {
		if (changed) {
			for (MyObserverInterface o: observers) {
				o.update(this, null);
			}
			clearChanged();
		}
	}
	
	public void notifyObservers(Object arg) {
		if (changed) {
			for (MyObserverInterface o: observers) {
				o.update(this, arg);
			}
			clearChanged();
		}
	}
	
	public boolean hasChanged() {
		return changed;
	}
	
	public void setChanged() {
		changed = true;
	}
	
	public void clearChanged() {
		changed = false;
	}
	
	public ArrayList<MyObserverInterface> getObservers() {
		return observers;
	}
	
	public void registerChange() {
		saved = false;
		if (this.getParent() != null)
			((MyObservableNode) this.getParent()).registerChange();
	}

	public boolean isSaved() {
		return saved;
	}

	public void setSaved(boolean saved) {
		this.saved = saved;
	}
	
	public String getName() {
		return name;
	}
	
	public String toString() {
		return name;
	}
	
	public boolean isFirstSave() {
		return firstSave;
	}
	
	public void setFirstSave(boolean firstSave) {
		this.firstSave = firstSave;
	}
	
	public void remove(int index) {
		setChanged();
        MyObservableNode child = (MyObservableNode) getChildAt(index);
        children.remove(index);
        child.setParent(null);	
        //child.removeParent(this);
        notifyObservers(child);
        this.registerChange();
    }
	
	public int getChildCount () {
		if(children==null)return 0;
		return children.size();
	}
	
	public int getIndex (TreeNode child) {
		return children.indexOf(child);
	}
	
	public TreeNode getChildAt (int index) {
		return (TreeNode)children.get(index);
	}
	
	public ArrayList <MyObservableNode> getParents(){
		return parents;
	}
	
	public void addParent(MyObservableNode parent) {
		if (parents.contains(parent)) return;
		parents.add(parent);
	}
	
	public void removeParent(MyObservableNode parent) {
		parents.remove(parent);
	}
	
	@Override
	public void removeFromParent() {
		for (MutableTreeNode parent: parents) {
			if (parent != null)
				parent.remove(this);
		}
		parents.clear();
	}
	
	public void remove(MutableTreeNode aChild) {
        if (aChild == null) {
            throw new IllegalArgumentException("argument is null");
        }
        
        remove(getIndex(aChild));       // linear search
    }
	
	public ArrayList<MyObservableNode> getChildren(){
		return children;
	}

}
	
	
	
