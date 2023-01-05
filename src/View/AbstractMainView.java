package View;

import java.util.List;

import Controllers.MainController;
import Listeners.MainUiListener;
import Listeners.Observer;
import Model.AmericanQ;
import Model.OpenQ;
import Model.Question;

public interface AbstractMainView {
	
	void addOpenQuestionToTableInUi(OpenQ question);
	void addAmericanQuestionToTableInUi(AmericanQ question);
	void registerListener(MainUiListener listener);
	void errorMessageUi(String msg);
	void addQuestionsToTable(List<Question> questions);
	void savedAllQuestionsMessageBox(String fileName, int amountOfQuestions);
	void addedAmericanAnswerPopUpDialog(AmericanQ question);
}

