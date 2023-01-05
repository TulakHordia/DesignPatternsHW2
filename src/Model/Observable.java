package Model;

import Listeners.Observer;

public interface Observable {
     abstract void addObserver(Observer observer);
     abstract void deletedObserver(Observer observer);
     abstract void notifyObservers(Object obj);
     abstract void setChanged(boolean value);
     abstract boolean hasChanged();
}
