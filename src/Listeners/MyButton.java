package Listeners;

import Model.Manager;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Vector;

public class MyButton extends Button implements Observer {
    private HashSet<Observer> set = new HashSet<>();
    private Vector<MainUiListener> questUiListeners = new Vector<MainUiListener>();
    Button newBut;
    MyLabel newLabel = new MyLabel();

    public MyButton() {
        this.newBut = newButton();
    }

    @Override
    public void update() {
        newBut.setDisable(false);
        newBut.setVisible(true);
    }

    @Override
    public void registerListener(MainUiListener listener) {
        questUiListeners.add(listener);
    }

    private Button newButton() {
        Button theNewButton = new Button();
        theNewButton.setText("Iterator Created");
        theNewButton.setMinSize(130, 25);
        theNewButton.setDisable(true);
        theNewButton.setVisible(false);
        theNewButton.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                for (MainUiListener l : questUiListeners) {
                    l.printMyArrayListFromUi();
                }
            }
        });
        return theNewButton;
    }

    @Override
    public Node getStyleableNode() {
        return super.getStyleableNode();
    }

    public Button getNewBut() {
        return newBut;
    }
}
