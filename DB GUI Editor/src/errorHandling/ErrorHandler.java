package errorHandling;

import java.util.ArrayList;
import java.util.List;
import observer.Notification;
import observer.Publisher;
import observer.Subscriber;
import observer.enums.NotificationCode;

public class ErrorHandler implements Publisher {
	
	private static ErrorHandler instance = null;
	private List<Subscriber> subscribers;
	
	private ErrorHandler() {}
	
	public static ErrorHandler getInstance() {
		if(instance == null) instance = new ErrorHandler();
		return instance;	
	}
	
	public void customException(String s) {
		Notification notification = new Notification(NotificationCode.ERROR, s);
		notifySubscribers(notification);
	}
	
	public void LoadException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri ucitavanju"));
		notifySubscribers(notification);
	}
	
	public void CloseConnectionException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri zatvaranju konekcije"));
		notifySubscribers(notification);
	}
	
	public void AddException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri dodavanju"));
		notifySubscribers(notification);
	}

	public void UpdateException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri editovanju"));
		notifySubscribers(notification);
	}

	public void DeleteException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri brisanju"));
		notifySubscribers(notification);
	}
	
	public void NothingChosenException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Niste selektovali red"));
		notifySubscribers(notification);
	}
	
	public void SearchException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri pretrazi"));
		notifySubscribers(notification);
	}
	
	public void ReportException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri izvestavanju"));
		notifySubscribers(notification);
	}
	
	public void FilterException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri filtriranju"));
		notifySubscribers(notification);
	}
	
	public void RelationsException() {
		Notification notification = new Notification(NotificationCode.ERROR, new String("Greska pri relacijama"));
		notifySubscribers(notification);
	}
	
	public void addSubscriber(Subscriber sub) {
	        if(sub == null)
	            return;
	        if(this.subscribers ==null)
	            this.subscribers = new ArrayList<>();
	        if(this.subscribers.contains(sub))
	            return;
	        this.subscribers.add(sub);
	}

	public void removeSubscriber(Subscriber sub) {
	        if(sub == null || this.subscribers == null || !this.subscribers.contains(sub))
	            return;
	        this.subscribers.remove(sub);
	}

   public void notifySubscribers(Notification notification) {
        if(notification == null || this.subscribers == null || this.subscribers.isEmpty())
            return;

        for(Subscriber subscriber : subscribers){
           subscriber.update(notification);
        }
   }
}
