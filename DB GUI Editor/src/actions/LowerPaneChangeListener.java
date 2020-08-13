package actions;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.TableModel;
import resource.implementation.Entity;
import resource.implementation.InformationResource;
import view.frame.MainFrame;

public class LowerPaneChangeListener implements ChangeListener{

	@Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() instanceof JTabbedPane) {
            JTabbedPane pane = (JTabbedPane) e.getSource();
            int index = pane.getSelectedIndex();
            if (index == -1) return;
            String tableName = pane.getTitleAt(index);
            InformationResource ir = (InformationResource)MainFrame.getInstance().getDBtree().getModel().getRoot();
            Entity entity = (Entity)ir.getChildByName(tableName);
            TableModel model = entity.getModel();
            MainFrame.getInstance().getAppCore().setLowerTableModel(model);
        }
    }

}
