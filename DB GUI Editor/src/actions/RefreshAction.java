package actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import model.TableModel;
import view.frame.MainFrame;

public class RefreshAction implements ActionListener{

	@Override
	public void actionPerformed(ActionEvent e) {
		
		TableModel model;
		if(((JButton)e.getSource()).getName().equals("refresh")) {
			model = MainFrame.getInstance().getAppCore().getTableModel();

		}else {
			 model = MainFrame.getInstance().getAppCore().getLowerTableModel();
		}
		
		model.setRows(MainFrame.getInstance().getAppCore().readDataFromTable(model.getName()));
		//JTabbedPane pane = MainFrame.getInstance().getUpperPane();
		//int index = pane.getSelectedIndex();
        // String tableName = pane.getTitleAt(index);
	}

}
