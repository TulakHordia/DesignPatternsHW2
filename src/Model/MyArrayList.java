package Model;

import Listeners.Observer;

import java.util.Iterator;
import java.util.Vector;

public class MyArrayList implements Observable, Iterable<Question> {
    private Vector<Observer> observers = new Vector<Observer>();
    public static final int MAX = 100;
    private Question[] newArray = new Question[MAX];
    private int index = 0;
    private boolean isChanged;


    public void add(Question value) {
        if (index < MAX) {
            newArray[index++] = value;
            setChanged(true);
        }
        else {
            throw new IllegalArgumentException("No more place");
        }
    }

    public Iterator<Question> iterator() {
        ConcreteIterator it = new ConcreteIterator();
        setChanged(true);
        notifyObservers(null);
        return it;
    }

    public Question get(int questionNumber) {
        for (int i = 0; i < newArray.length; i++) {
            if (newArray[i].getQuestionNumber() == questionNumber) {
                return newArray[i];
            }
        }
        return null;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void deletedObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        if(hasChanged()) {
            for (int i = 0; i < observers.size(); i++) {
                observers.get(i).update();
            }
            setChanged(false);
        }
    }

    @Override
    public void setChanged(boolean value) {
        this.isChanged = value;
    }

    @Override
    public boolean hasChanged() {
        return isChanged;
    }

    private class ConcreteIterator implements Iterator<Question> {
        private int cur = 0;

        @Override
        public boolean hasNext() {
            return cur < index;
        }

        @Override
        public void remove() {
            if (cur < 0 || cur >= index) {
                throw new IllegalStateException("Invalid position for remove operation");
            }
            for (int i = cur-1; i < index; i++) {
                newArray[i] = newArray[i + 1];
            }
            newArray[index--] = null;
        }

        public Question next() {
            if (!hasNext()) {
                System.out.println("Element does not exist.");
              //  throw new NoSuchElementException();
            }
            Question tmp = newArray[cur++];
            return tmp;
        }

    }
}
