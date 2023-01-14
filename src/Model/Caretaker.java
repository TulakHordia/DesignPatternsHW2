package Model;

import java.util.*;

public class Caretaker {

    private List<Memento> mementoList = new ArrayList<Memento>();
    private Memento memento;

    public void add(Memento state) {
        mementoList.add(state);
    }

    public Memento get(int index) {
        return mementoList.get(index);
    }

    public int getCareTakerSize() {
        return mementoList.size();
    }

    public void saveMemento(OpenQ openQ) {
        if (memento == null) {
            memento = new Memento();
        }
        memento.addOpenQ(openQ);
    }

    public OpenQ restoreQuestions(int index) {
        Memento m = mementoList.get(index);
        OpenQ newOpenQuestion = new OpenQ(m.getQuestion(), m.getAnswer());
        return newOpenQuestion;
    }

    public void printAllStates(List<Memento> mementoList) {
        Map<String, List<Memento>> questionMementos = new HashMap<>();
        for (Memento m : mementoList) {
            String question = m.getOpenQList().get(0).getQuestion();
            if (!questionMementos.containsKey(question)) {
                questionMementos.put(question, new ArrayList<>());
            }
            questionMementos.get(question).add(m);
        }
        questionMementos.forEach((q, mementos) -> {
            System.out.println("Question: " + q);
            boolean first = true;
            for(Memento m : mementos) {
                if(first) {
                    m.getOpenQList().forEach(o -> System.out.println("State : " + o.getAnswer()));
                    first = false;
                }
            }
            System.out.println("------------------");
        });
    }

    public Memento getMemento() {
        return memento;
    }
}
