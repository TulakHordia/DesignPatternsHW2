package Model;

import Listeners.MainUiListener;
import java.util.Vector;

public class ImportCommand implements Command {

    private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
    Manager newMan;

    public ImportCommand(MainUiListener listener, Manager manModel) {
        questUiListeners.add(listener);
        this.newMan = manModel;
    }

    @Override
    public void execute() {
        newMan.questionsList();
    }
}
