package view.component;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import view.frame.MainFrame;

public class Menu extends JMenuBar{
	
	private static final long serialVersionUID = 1L;

	public Menu() {
		
		JMenu fileMenu = new JMenu("File");
		/*fileMenu.add(MainFrame.getInstance().getActionManager().getSaveFileAsAction());
		fileMenu.add(MainFrame.getInstance().getActionManager().getSaveFileAction());
		fileMenu.add(MainFrame.getInstance().getActionManager().getNewAction());
		fileMenu.add(MainFrame.getInstance().getActionManager().getDeleteAction());
		
		fileMenu.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		JMenuItem newProjectItem = new JMenuItem("New project");
		newProjectItem.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		//fileMenu.add(newProjectItem);
		//newProjectItem.addActionListener(MainFrame.getInstance().getActionManager().getNewAction());
		
		JMenuItem deleteMenuItem = new JMenuItem("Delete");
		deleteMenuItem.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		//fileMenu.add(deleteMenuItem);
		//deleteMenuItem.addActionListener(MainFrame.getInstance().getActionManager().getDeleteAction());*/
		
		JMenu aboutMenu = new JMenu("Analyse");
		/*aboutMenu.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
		//aboutMenu.add(aboutItem);*/
		
		//aboutItem.addActionListener(MainFrame.getInstance().getActionManager().getAboutAction());
		JMenu windowMenu = new JMenu("Window");
		
		
		JMenu HelpMenu = new JMenu("Help");

		JButton btnSearch = new JButton();
		btnSearch.setIcon(new ImageIcon("resources/search.png"));
		btnSearch.addActionListener(MainFrame.getInstance().getActionManager().getSearchButtonListener());
		
		JButton btnReport = new JButton();
		btnReport.setIcon(new ImageIcon("resources/report.png"));
		btnReport.addActionListener(MainFrame.getInstance().getActionManager().getReportButtonListener());

		JButton btnFilterSort = new JButton();
		btnFilterSort.setIcon(new ImageIcon("resources/filter.png"));
		btnFilterSort.addActionListener(MainFrame.getInstance().getActionManager().getSortFilterAction());
		
		JButton btnRelations =  new JButton();
		btnRelations.setIcon(new ImageIcon("resources/relation.png"));
		btnRelations.addActionListener(MainFrame.getInstance().getActionManager().getRelationJoinAction());
		
		add(fileMenu);
		add(aboutMenu);
		add(windowMenu);
		add(HelpMenu);
		//add(Box.createHorizontalGlue());
		
		add(Box.createHorizontalGlue());
		add(btnRelations);
		add(btnFilterSort);
		add(btnReport);
		add(btnSearch);
		setBorder(BorderFactory.createEtchedBorder());
		setBorderPainted(true);
	}
	
}
