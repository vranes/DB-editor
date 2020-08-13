package start;

import view.frame.MainFrame;

public class Main {

	public static void main(String[] args) {
		
		 AppCore appCore = new AppCore();
	     MainFrame mainFrame = MainFrame.getInstance();
	     mainFrame.setAppCore(appCore);
	     mainFrame.getAppCore().loadResource();
	     
	     //mainFrame.getAppCore().readDataFromTable("NASTAVNI_PREDMETI"); //ovo treba pozvati pri odaberu tabele za prikaz
	}

}
