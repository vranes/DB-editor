package view.frame;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultTreeModel;
import actions.ActionManager;
import resource.implementation.InformationResource;
import observer.Notification;
import observer.Subscriber;
import observer.enums.NotificationCode;
import start.AppCore;
import tree.DBtree;
import view.component.Menu;

public class MainFrame extends JFrame implements Subscriber{
	
	private static final long serialVersionUID = 1L;
	private static MainFrame instance = null;
	private AppCore appCore;
	private ActionManager actionManager;
	private Menu menu;
	private JSplitPane horizontalScreenSplit;
	private JSplitPane rightVerticalSplit;
	private JScrollPane left;
	private JTabbedPane upperPane;
	private JTabbedPane lowerPane;
	private DBtree  dbTree;
	private JPanel upperPanel;
	private JPanel lowerPanel;
	private JButton btnRefresh;
	private JButton btnDelete;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnRefresh1;
	private JButton btnDelete1;
	private JButton btnAdd1;
	private JButton btnUpdate1;
	
	private MainFrame(){}

	private void initialize(){
		 actionManager = ActionManager.getInstance();
	     initializeTree();
         initializeGUI();
         dbTree.expandRow(0);         
	}
	
	public void initializeGUI() {
		
		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		btnRefresh = new JButton("Refresh");
		btnAdd1 = new JButton("Add");
		btnUpdate1= new JButton("Update");
		btnDelete1 = new JButton("Delete");
		btnRefresh1 = new JButton("Refresh");
		upperPanel = new JPanel();
		lowerPanel = new JPanel();
		
		btnAdd.setName("add");
		btnUpdate.setName("update");
		btnDelete.setName("delete");
		btnAdd1.setName("add1");
		btnUpdate1.setName("update1");
		btnDelete1.setName("delete1");
		btnRefresh.setName("refresh");
		btnRefresh1.setName("refresh1");
		    
		btnAdd.addActionListener(actionManager.getAddButtonAction());
		btnAdd1.addActionListener(actionManager.getAddButtonAction()); 
		btnUpdate.addActionListener(actionManager.getUpdateButtonAction());
		btnUpdate1.addActionListener(actionManager.getUpdateButtonAction());
		btnRefresh.addActionListener(actionManager.getRefreshAction());
		btnRefresh1.addActionListener(actionManager.getRefreshAction());
		btnDelete.addActionListener(actionManager.getDeleteButtonAction());
		btnDelete1.addActionListener(actionManager.getDeleteButtonAction());
		
		Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        screenWidth = screenWidth - (screenWidth/2)/2;
        screenHeight = screenHeight - (screenHeight/2)/2+100;
        setSize(screenWidth , screenHeight );      
        setLocationRelativeTo(null);
        
        setTitle("Tim 10");
      //s  setLayout(new BorderLayout());

        menu = new Menu();
        setJMenuBar(menu);
       
        horizontalScreenSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); 
        rightVerticalSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT); 
        
        horizontalScreenSplit.setDividerLocation(300);
        horizontalScreenSplit.setEnabled(false);
        
        rightVerticalSplit.setResizeWeight(0.5);
        rightVerticalSplit.setEnabled(true);


        left = new JScrollPane(dbTree);
        left.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        upperPane = new JTabbedPane();
        upperPane.addChangeListener((ChangeListener)actionManager.getUpperPaneChangeListener());
        lowerPane = new JTabbedPane();    
        lowerPane.addChangeListener((ChangeListener)actionManager.getLowerPaneChangeListener());
        
        horizontalScreenSplit.setLeftComponent(left);
        horizontalScreenSplit.setRightComponent(rightVerticalSplit);
        
        rightVerticalSplit.setLeftComponent(upperPanel);
        rightVerticalSplit.setRightComponent(lowerPanel);
        
        upperPanel.setLayout(new GridBagLayout());
       
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 7;
        c.weightx = c.weighty = 1.0;
        upperPanel.add(upperPane,c);
      
        c.weightx = c.weighty = 0;
        c.fill = GridBagConstraints.NONE;
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        c.gridwidth = 1;
        upperPanel.add(new JPanel(),c);
        
        c.weightx = c.weighty =0;
        c.gridx = 1;
        c.gridy = 1;
        c.insets = new Insets(10, 10, 5, 5);
        upperPanel.add(btnRefresh,c);
   

        c.gridx = 2;
        c.gridy = 1;
        

        upperPanel.add(btnDelete,c);
        
        c.gridx = 3;
        c.gridy = 1;
        upperPanel.add(btnDelete,c);

        c.gridx = 4;
        c.gridy = 1;
        upperPanel.add(btnAdd,c);
        
        c.gridx = 6;
        c.gridy = 1;
        upperPanel.add(btnUpdate,c);
        
     
       // upperPane.add(new Label("temp"));
         
         
         ///////////////////////////////////////////////////////////////////
         
         
        lowerPanel.setLayout(new GridBagLayout());
         
         c.insets = new Insets(10, 0, 0, 0);
         c.fill = GridBagConstraints.BOTH;
         c.gridx = 0;
         c.gridy = 0;
         c.gridwidth = 7;
           c.weightx = c.weighty = 1.0;
         lowerPanel.add(lowerPane,c);
         
         c.weightx = c.weighty = 0;
         c.fill = GridBagConstraints.NONE;
         c.gridx = 0;
         c.gridy = 1;
         c.weightx = 1;
         c.gridwidth = 1;
         lowerPanel.add(new JPanel(),c);
         
         c.weightx = c.weighty =0;
         c.gridx = 1;
         c.gridy = 1;
         c.insets = new Insets(10, 10, 5, 5);
         lowerPanel.add(btnRefresh1,c);
    

         c.gridx = 2;
         c.gridy = 1;
         

         lowerPanel.add(btnDelete1,c);
         
         c.gridx = 3;
         c.gridy = 1;
         lowerPanel.add(btnDelete1,c);

         c.gridx = 4;
         c.gridy = 1;
         lowerPanel.add(btnAdd1,c);
         
         c.gridx = 6;
         c.gridy = 1;
         lowerPanel.add(btnUpdate1,c);
         
         
       //  lowerPane.add(new Label("temp"));
         
        add(horizontalScreenSplit);
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
	}
	
	public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        //this.jTable.setModel(appCore.getTableModel());
    }
	
	public AppCore getAppCore() {
		return appCore;
	}

	public static MainFrame getInstance(){
		   if (instance == null){
			  instance  = new MainFrame();
			  instance.initialize();
		   }
		   return instance;
	   }

	@Override
	public void update(Notification notification) {
		if (notification.getCode().equals(NotificationCode.RESOURCE_LOADED))
		{
			dbTree.setModel(new DefaultTreeModel(((InformationResource)notification.getData())));
			dbTree.repaint();
			this.repaint();
		}
	}
	
	public void initializeTree() {
		InformationResource irs = new InformationResource("Loading...");
        dbTree = new DBtree();
        dbTree.addMouseListener(actionManager.getDoubleClickAction());
        dbTree.setModel(new DefaultTreeModel(irs));
	}

	public DBtree getDBtree() {
		return dbTree;
	}

	public ActionManager getActionManager() {
		return actionManager;
	}
	
	public JTabbedPane getUpperPane() {
		return upperPane;
	}
	
	public JTabbedPane getLowerPane() {
		return lowerPane;
	}
	
	public void closeLowerPane() {
		lowerPane.removeAll();
	}
	
}
