package errorHandling;

import javax.swing.JOptionPane;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import view.frame.MainFrame;

public class ErrorHandlerView extends JOptionPane implements Subscriber{

	private static final long serialVersionUID = 1L;

	@Override
	public void update(Notification notification) {
		if (notification.getCode() != NotificationCode.ERROR) return;
		String errorMessage = (String)notification.getData();
		showMessageDialog(MainFrame.getInstance(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
}
