package Model;

import Listeners.MainUiListener;

import java.util.Vector;

public class ImportCommand implements Command {

    private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
    Manager newMan;

    public ImportCommand(MainUiListener listener) {
        questUiListeners.add(listener);
    }

    public ImportCommand(Manager manModel) {
        this.newMan = manModel;
    }

    @Override
    public void execute() {
        newMan.questionsList();
    }
}
