package Model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public abstract class Question implements Serializable {
	
	private static int questionNumCounter = 1;
	private int questionNumber;
	protected String question;

	public Question(String question) {
		this.question = question;
		questionNumber = questionNumCounter++;
	}

	public int getQuestionNumber() {
		return questionNumber;
	}
	
	public void setQuestionNumber(List<Question> allQuestions) {
		questionNumCounter = (allQuestions.size()+1);
	}
	
	public String getQuestion() {
		return question;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}

	@Override
	public String toString() {
		return question + "\n";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Question question1 = (Question) o;

		return this.getQuestion().contains("z") || this.getQuestion().length() == question1.getQuestion().length();
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionNumber, question.hashCode());
	}
}
