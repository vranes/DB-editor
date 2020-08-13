package observer;

public interface MyObservableInterface {
	
	public void addObserver(MyObserverInterface o);
	public void deleteObserver(MyObserverInterface o);
	public void notifyObservers();
	public void notifyObservers(Object o);
	public boolean hasChanged();
	public void setChanged();
	public void clearChanged();
}
