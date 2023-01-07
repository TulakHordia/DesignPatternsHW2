package Listeners;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Vector;

public class MyLabel extends Label implements Observer {
    private Label newLabel;
    private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();

    public MyLabel() {
        this.newLabel = new Label("Iterator Created.");
        newLabel.setFont(new Font("Arial", 30));
        newLabel.setTextFill(Color.RED);
        newLabel.setVisible(false);
        newLabel.setDisable(true);
    }

    @Override
    public void update() {
        newLabel.setTextFill(Color.GREEN);
        newLabel.setVisible(true);
        newLabel.setDisable(false);
    }

    @Override
    public void registerListener(MainUiListener listener) {
        questUiListeners.add(listener);
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    public Label getNewLabel() {
        return newLabel;
    }
}
