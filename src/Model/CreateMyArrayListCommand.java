package Model;

import Listeners.MainUiListener;

import java.sql.SQLException;
import java.util.Vector;

public class CreateMyArrayListCommand implements Command {

    private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
    Manager newMan;

    public CreateMyArrayListCommand(MainUiListener listener) {
        questUiListeners.add(listener);
    }

    public CreateMyArrayListCommand(Manager manModel) {
        this.newMan = manModel;
    }

    @Override
    public void execute() {
        newMan.copyArrayListToTreeSet();
        newMan.copyTreeSetIntoLinkedHashSet();
        newMan.copyOldCollectionToMyArrayList();
    }
}