package resource.implementation;

import model.TableModel;
import resource.DBNode;
import resource.DBNodeComposite;


public class Entity extends DBNodeComposite {

	private static final long serialVersionUID = 1L;
	TableModel model = null;
	
    public Entity(String name, DBNode parent) {
        super(name, parent);
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof Attribute){
            Attribute attribute = (Attribute) child;
            this.getChildren().add(attribute);
        }

    }

	public TableModel getModel() {
		return model;
	}

	public void setModel(TableModel model) {
		this.model = model;
	}
    
    
}
