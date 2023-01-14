package Model;

import java.util.ArrayList;
import java.util.List;

public class Memento {

    private OpenQ openQ;
    private List<OpenQ> openQList;

    public Memento() {
        openQList = new ArrayList<>();
    }

    public void addOpenQ(OpenQ openQ) {
        openQList.add(openQ);
    }

    public List<OpenQ> getOpenQList() {
        return openQList;
    }

    public OpenQ getOpenQ() {
        return openQ;
    }

    public String getQuestion() {
        return openQ.getQuestion();
    }

    public String getAnswer() {
        return openQ.getAnswer();
    }
}
