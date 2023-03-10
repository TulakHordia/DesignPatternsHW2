package Listeners;

import java.io.File;
import java.util.List;

import Model.*;

public interface MainEventsListener {
	
	void addedOpenQuestionToModelEventObject(OpenQ question);
	void addedAmericanQuestionToModelEventObject(AmericanQ question);
	void deletedAmericanAnswer(AmericanAnswers aN, AmericanQ question);
	void importedFromBinaryFile(List<Question> questions);
	void savedAllQuestionsToFile(String fileName, int amountOfQuestions);
	void createdAutoExam(int amount, List<Question> exam);
	void addedAmericanAnswerToQuestion(AmericanQ question);
	void copiedAnExistingExam(File fileName);
	void savedToBinaryFileOnExit();
	void createdMyArrayListInManager(MyArrayList newList);
}

