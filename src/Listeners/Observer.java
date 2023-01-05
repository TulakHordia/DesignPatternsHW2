package Listeners;

import Model.MyArrayList;
import Model.Observable;

public interface Observer {
    void update();
    void registerListener(MainUiListener listener);
}
