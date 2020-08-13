package resource;

import java.util.ArrayList;

import javax.swing.JTable;

import resource.implementation.Entity;

public class EntityManager {
	
	private static EntityManager instance = null;
	private ArrayList<Entity> openEntities;

	private EntityManager() {
		openEntities = new ArrayList<Entity>();
	}
	
	public static EntityManager getInstance() {
		if (instance == null) instance = new EntityManager();
		return instance;
	}
	
	public void addEntity(Entity e) {
		openEntities.add(e);
	}
	
	public boolean isEntityOpen (Entity e) {
		return openEntities.contains(e);
	}
	
	public void removeEntity(Entity e) {
		openEntities.remove(e);
	}
	
}
