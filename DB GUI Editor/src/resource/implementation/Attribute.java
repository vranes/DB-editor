package resource.implementation;

import resource.DBNode;
import resource.DBNodeComposite;
import resource.enums.AttributeType;

public class Attribute extends DBNodeComposite {

    private AttributeType attributeType;
    private int length;
    private Attribute relationship;

    public Attribute(String name, DBNode parent) {
        super(name, parent);
    }

    public Attribute(String name, DBNode parent, AttributeType attributeType, int length) {
        super(name, parent);
        this.attributeType = attributeType;
        this.length = length;
    }

    @Override
    public void addChild(DBNode child) {
        if (child != null && child instanceof AttributeConstraint){
            AttributeConstraint attributeConstraint = (AttributeConstraint) child;
            this.getChildren().add(attributeConstraint);
        }
    }

	public Attribute getRelationship() {
		return relationship;
	}

	public void setRelationship(Attribute relationship) {
		this.relationship = relationship;
	}
    
    

}
