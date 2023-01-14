package Model;

import Listeners.MainUiListener;
import javafx.application.Platform;
import javafx.scene.control.Button;
import java.util.Vector;

public class ExitCommand implements Command {

    private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
    Button exitButton;

    public ExitCommand(MainUiListener listener, Button closeButton) {
        questUiListeners.add(listener);
        this.exitButton = closeButton;
    }

    @Override
    public void execute() {
        Platform.exit();
    }
}
