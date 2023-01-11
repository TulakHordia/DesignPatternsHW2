package Model;

import Listeners.MainUiListener;
import javafx.application.Platform;
import javafx.scene.control.Button;

import javax.swing.*;
import java.io.IOException;
import java.util.Vector;

public class ExitCommand implements Command {

    private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
    Button exitButton;

    public ExitCommand(MainUiListener listener) {
        questUiListeners.add(listener);
    }

    public ExitCommand(Button closeButton) {
        this.exitButton = closeButton;
    }

    @Override
    public void execute() {
        for (MainUiListener l : questUiListeners) {
            try {
                l.saveToBinaryFileOnExit();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to save.");
            }
        }
        Platform.exit();
    }
}
