package Model;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


import javax.swing.JOptionPane;

import Listeners.MainEventsListener;
import javafx.beans.property.SimpleStringProperty;

public class Manager {

	List<Question> allQuestions;
	List<Question> manualExamArray;
	List<Question> autoExamArray;
	List<File> allExistingExams;
	List<File> examsNoDuplicatesList;
	//Collections.
	public TreeSet<Question> treeSet = new TreeSet<Question>();
	public List<Question> newArrayList = new ArrayList<Question>();
	public HashSet<Question> newHashSet = new HashSet<Question>();
	public LinkedHashSet<Question> newLinkedHashSet = new LinkedHashSet<Question>();
	public MyArrayList newList = new MyArrayList();
	public List<Question> anotherArrayList = new ArrayList<Question>();
	private Vector<MainEventsListener> mainListener = new Vector<MainEventsListener>();
	Caretaker careTaker = new Caretaker();
	List<Memento> mementoList = new ArrayList<>();
	List<Memento> savedStatesList = new ArrayList<>();
	@SuppressWarnings("unused")
	private int size;
	private int examNum;
	private int removedQuestionsCount;
	private Iterator mainIterator;
	private SimpleStringProperty name;

	Scanner input = new Scanner(System.in);

	public SimpleStringProperty nameProperty() {
		return name;
	}

	public void registerListener(MainEventsListener listener) {
		mainListener.add(listener);
	}

	private void fireAddOpenQuestion(OpenQ question) {
		for (MainEventsListener l : mainListener) {
			l.addedOpenQuestionToModelEventObject(question);
		}
	}

	private void fireAddAmericanQuestion(AmericanQ question) {
		for (MainEventsListener l : mainListener) {
			l.addedAmericanQuestionToModelEventObject(question);
		}
	}

	private void fireImportFromBinaryFile(List<Question> questions) {
		for (MainEventsListener l : mainListener) {
			l.importedFromBinaryFile(questions);
		}
	}

	private void fireSavedAllQuestionsToFile(String fileName, int amountOfQuestions) {
		for (MainEventsListener l : mainListener) {
			l.savedAllQuestionsToFile(fileName, amountOfQuestions);
		}
	}

	private void fireDeletedAmericanAnswer(AmericanAnswers aN, AmericanQ question) {
		for (MainEventsListener l : mainListener) {
			l.deletedAmericanAnswer(aN, question);
		}
	}


	private void fireCreateAutoExam(int amount, List<Question> exam) {
		for (MainEventsListener l : mainListener) {
			l.createdAutoExam(amount, exam);
		}
	}

	private void fireAddAmericanAnswerToQuestion(AmericanQ question) {
		for (MainEventsListener l : mainListener) {
			l.addedAmericanAnswerToQuestion(question);
		}
	}


	private void fireCopiedAnExam(File fileName) {
		for (MainEventsListener l : mainListener) {
			l.copiedAnExistingExam(fileName);
		}
	}


	private void fireSavedToBinaryOnExit() {
		for (MainEventsListener l : mainListener) {
			l.savedToBinaryFileOnExit();
		}
	}

	public Manager() {
		allQuestions = new ArrayList<Question>();
		manualExamArray = new ArrayList<Question>();
		autoExamArray = new ArrayList<Question>();
		allExistingExams = new ArrayList<File>();
		examsNoDuplicatesList = new ArrayList<File>();

		size = 0;
		examNum = 1;
	}

	public void setSize(int size) {
		this.size = size;
		manualExamArray = new ArrayList<Question>(size);
		for (int i = 0; i < size; i++) {
			manualExamArray.add(i, null);
		}
	}

	public boolean addOpenQuestions(String question, String answer) {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ) {
				OpenQ oQ = (OpenQ) allQuestions.get(i);
				if (oQ.getQuestion().equals(question)) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		OpenQ theQuestion = new OpenQ(question, answer);
		allQuestions.add(theQuestion);
		fireAddOpenQuestion(theQuestion);
		System.out.println("Created question #" + (allQuestions.size()));
		return true;
	}

	public boolean addAmericanQuestion(AmericanQ question) {
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) allQuestions.get(i);
				if (aQ.getQuestion().equals(question.getQuestion())) {
					System.out.println("Question already exists.");
					return false;
				}
			}
		}
		allQuestions.add(question);
		fireAddAmericanQuestion(question);
		System.out.println("Created question #" + (allQuestions.size()));
		return true;
	}

	public void addAmericanAnswerToQuestion(AmericanQ question, String answer, boolean isTrue) {
		AmericanAnswers aN = new AmericanAnswers(answer, isTrue);
		System.out.println(question.addAnswer(aN));
		fireAddAmericanAnswerToQuestion(question);
	}

	private Question getQuestionById(int questionNumber) {
		return allQuestions.get(questionNumber);
	}

	private Question getQuestionByIdFromNewList(int questionNumber) {
		return newList.get(questionNumber);
	}

	private Question getQuestionByIdFromAnotherArrayList(int questionNumber) {
		for (Question s : anotherArrayList) {
			if (s.getQuestionNumber() == questionNumber) {
				return s;
			}
		}
	return null;
	}

	public String updateQuestion(int questionNumber, String question) {
		Question quest = getQuestionById(questionNumber-1);
		if (quest != null) {
			quest.setQuestion(question);
			return "Updated successfully.";
		}
		return "Question does not exist.";
	}

	public String deleteAnswer(int questionNumber, int loc) {
		Question quest = getQuestionById(questionNumber-1);
		if (quest instanceof OpenQ) {
			return "Cannot delete an answer from an open question.";
		}
		AmericanQ ameriQ = (AmericanQ) quest;
		if (loc > ameriQ.getAnswersNum() || loc < 1) {
			return "Answer does not exist.";
		}
		ameriQ.deleteAnswer(loc);
		return "Deleted successfully.";
	}

	public void deleteAmericanAnswer(AmericanAnswers aN, AmericanQ question) {
		if (question.deleteAmericanAnswer(aN)) {
			fireDeletedAmericanAnswer(aN, question);
			System.out.println("Deleted answer " + aN.getAnswer());
		}
	}

	public String updateAnswer(int questionNumber, int loc, String newAnswer) {
		Question quest = getQuestionById(questionNumber-1);
		if (quest instanceof OpenQ) {
			OpenQ open = (OpenQ) quest;
			open.setCorrectAnswer(newAnswer);
		}
		if (quest instanceof AmericanQ) {
			AmericanQ ameriQ = (AmericanQ) quest;
			if (loc > ameriQ.getAnswersNum() || loc < 1) {
				return "Answer does not exist.";
			}
			ameriQ.updateAnswer(loc, newAnswer);
		}
		return "Updated successfully.";
	}

	public void printEverything() {
		System.out.println("Printing all questions");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) != null) {
				System.out.println(allQuestions.get(i));
			}
		}
	}

	public void printQuestionsOnly() {
		System.out.println("-----All Questions-----");
		System.out.println("\nAmerican questions: ");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) != null) {
				if (allQuestions.get(i) instanceof AmericanQ) {
					System.out.println(allQuestions.get(i).getQuestionNumber() + ") " + allQuestions.get(i).getQuestion());
				}
			}
		}
		System.out.println("\nOpen questions: ");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ) {
				System.out.println(allQuestions.get(i).getQuestionNumber() + ") " + allQuestions.get(i).getQuestion());
			}
		}
	}

	public void printAmerican() {
		System.out.println("-----American Questions-----");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof AmericanQ) {
				System.out.println(allQuestions.get(i));
			}
		}
	}

	public void printAmericanQuestionsOnly() {
		System.out.println("-----American Questions-----");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof AmericanQ) {
				System.out.println((i+1)+") "+allQuestions.get(i).getQuestion());
			}
		}
	}

	public void printOpen() {
		System.out.println("-----Open Questions-----");
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) instanceof OpenQ) {
				System.out.println(allQuestions.get(i));
			}
		}
	}

	public void printAmericanAnswers(int questionNum) {
		System.out.println("American Answers for questions number: " + questionNum);
		Question quest = getQuestionById(questionNum);
		if (quest instanceof AmericanQ) {
			AmericanQ aQ = (AmericanQ) quest;
			for (int i = 0; i < 10; i++) {
				if (aQ.getAnswers(i) != null) {
					System.out.println("[" + (i + 1) + "] " + aQ.getAnswers(i));
				}
			}
		}
	}

	public void write(String filename, List<Question> manualExamArray) throws IOException {
		FileWriter fwQ = new FileWriter("Exams/"+"exam_"+examNum+"_"+filename+"_questions.txt");
		FileWriter fwA = new FileWriter("Exams/"+"exam_"+examNum+"_"+filename+"_solution.txt");
		examNum++;

		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) != null) {
				fwQ.write("Question number: " + (i+1) + "\n" + manualExamArray.get(i).getQuestion()+"\n");
			}
		}

		for (int i = 0; i < manualExamArray.size(); i++) {
			if (manualExamArray.get(i) instanceof AmericanQ) {
				AmericanQ aQ = (AmericanQ) manualExamArray.get(i);
				fwA.write("\nQuestion: " + aQ.getQuestion() + "\n");
				fwA.write("Answers for question number: " + (i+1) + "\n");
				for (int j = 0; j < aQ.getAnswersNum(); j++) {
					if (aQ.getAnswers(j) != null) {
						fwA.write(aQ.getAnswers(j).getAnswer()+" - "+aQ.getAnswers(j).getIsTrue()+"\n");
					}
				}
			}
			if (manualExamArray.get(i) instanceof OpenQ) {
				OpenQ oQ = (OpenQ) manualExamArray.get(i);
				fwA.write("\nQuestion: " + oQ.getQuestion() + "\n");
				fwA.write("Answer for question number: " + (i+1) + "\n");
				fwA.write(oQ.getAnswer()+"\n");
			}
		}

		System.out.println("Writing successful.");
		fwA.flush();
		fwA.close();
		fwQ.flush();
		fwQ.close();
	}

	public void writeAllExternally(String fileName) throws IOException {
		Files.createDirectories(Paths.get("Exams/"));
		boolean check = new File(fileName).exists();

		if (!check) {
			if (allQuestions.size() == 0) {
				JOptionPane.showMessageDialog(null, "No questions exist, cannot create an empty file");
			}
			else {
				FileWriter fwAll = new FileWriter("Exams/"+fileName+".txt");
				System.out.println("Created a new " + fileName +".txt"+ " File in Exams folder.");
				for (int i = 0; i < allQuestions.size(); i++) {
					fwAll.write("Question number: " + (i+1) + "\n" + allQuestions.get(i)+"\n");
				}
				fireSavedAllQuestionsToFile(fileName, getAllQuestionSize());
				fwAll.flush();
				fwAll.close();
			}
		}
		else {
			if (allQuestions.size() == 0) {
				JOptionPane.showMessageDialog(null, "No questions exist, cannot create an empty file");
			}
			else {
				System.out.println("A 'questions' file already exists.");
				System.out.println("Added all ");
				FileWriter fwAll = new FileWriter("Exams/"+fileName+".txt", true);
				for (int i = 0; i < allQuestions.size(); i++) {
					fwAll.write("Question number: " + (i+1) + "\n" + allQuestions.get(i)+"\n");
				}
				fireSavedAllQuestionsToFile(fileName, getAllQuestionSize());
				fwAll.flush();
				fwAll.close();
			}
		}
	}

	public void saveToBinaryFile(String fileName) throws IOException, FileNotFoundException {
		Files.createDirectories(Paths.get("Data/"));
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Data/"+fileName+".ser"));
		for (int i = 0; i < allQuestions.size(); i++) {
			outFile.writeObject(allQuestions.get(i));
		}
		outFile.close();
		JOptionPane.showMessageDialog(null, "Saved to: Data/"+fileName+".ser");
		System.out.println("Saved to: Data/"+fileName+".ser");

	}

	public void saveToBinaryFileAutomatically() throws IOException, FileNotFoundException {
		Files.createDirectories(Paths.get("Data/"));
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("Data/questions.ser"));
		if (allQuestions.size() > 0) {
			for (int i = 0; i < allQuestions.size(); i++) {
				outFile.writeObject(allQuestions.get(i));
			}
			outFile.close();
			fireSavedToBinaryOnExit();
			System.out.println("Saved to: Data/questions.ser");
		}
		else {
			JOptionPane.showMessageDialog(null, "Did not save as there were no questions in the data");
		}
	}


	public void readFromBinaryFile(String fileName) throws IOException, FileNotFoundException, ClassNotFoundException {
		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Data/"+fileName))){
			while (true) {
				allQuestions.add((Question) inFile.readObject());
			}
		} catch (EOFException e) {
			fireImportFromBinaryFile(allQuestions);
			JOptionPane.showMessageDialog(null, "Imported from: "+fileName);
			System.out.println("Imported data from " + fileName);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "File not found");
			int input = JOptionPane.showConfirmDialog(null, "File not found"+"\nWould you like to type again?"+"\nMake sure name ends with .ser");
			if (input == 0) {
				String m = JOptionPane.showInputDialog("Enter filename:(ends with .ser)");
				readFromBinaryFile(m);
			}
			else {
				JOptionPane.showMessageDialog(null, "No file imported");
			}
			System.out.println("File " + fileName + " does not exist in the directory.");
		}

	}


	public void autoImportOnLaunch() throws ClassNotFoundException, IOException {
		try (ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("Data/questions.ser"))){
			while (true) {
				allQuestions.add((Question) inFile.readObject());
			}
		} catch (EOFException e) {
			System.out.println("Automatically Imported data from questions.ser");
		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.out.println("Please import questions list. (Option 12)");
		}

		for (int i = 0; i < allQuestions.size(); i++) {
			Question quest = null;
			if (i == 0) {
				quest = getQuestionById(i+1);
			}
			else {
				quest = getQuestionById(i);
			}
			if (quest != null) {
				quest.setQuestionNumber(allQuestions);
				break;
			}
		}
	}

	public List<File> showAllExistingExamsInDirectory() {
		File[] files = new File("Exams/").listFiles();
		name = new SimpleStringProperty();
		for (File l : files) {
			if (l.isFile()) {
				allExistingExams.add(l);
				name.set(l.getName());
			}
		}
		removeDuplicates();
		return examsNoDuplicatesList;
	}

	public void removeDuplicates() {
		for (File l : allExistingExams) {
			if (!examsNoDuplicatesList.contains(l)) {
				examsNoDuplicatesList.add(l);
			}
		}
	}

	public void copyExistingExam(int copyChoice) throws IOException {
		BufferedReader inputS = new BufferedReader(new FileReader(allExistingExams.get(copyChoice-1)));
		FileWriter output = new FileWriter("Exams/"+"copy_of_"+allExistingExams.get(copyChoice-1).getName());
		String count;
		while ( (count = inputS.readLine()) != null ) {
			output.write(count+"\n");
		}
		System.out.println("Created a copy of: " + allExistingExams.get(copyChoice-1).getName());
		output.flush();
		output.close();
		inputS.close();
	}

	public void copyExistingExamByFileName(File fileName) throws IOException {
		for (int i = 0; i < examsNoDuplicatesList.size(); i++) {
			if (examsNoDuplicatesList.get(i).getName().equals(fileName.getName())) {
				BufferedReader inputS = new BufferedReader(new FileReader(fileName));
				FileWriter output = new FileWriter("Exams/"+"copy_of_"+examsNoDuplicatesList.get(i).getName());
				String count;
				while ( (count = inputS.readLine()) != null ) {
					output.write(count+"\n");
				}
				JOptionPane.showMessageDialog(null, "Created a copy of: " + examsNoDuplicatesList.get(i).getName());
				System.out.println("Created a copy of: " + examsNoDuplicatesList.get(i).getName());
				inputS.close();

				output.flush();
				output.close();
			}
		}
	}

	public void sortByAnswerLength(List<Question> array) {
		QuestionComparator qC = new QuestionComparator();
		array.sort(qC);
	}

	public void sortAndPrintAutoExamArray() throws IOException {
		sortByAnswerLength(autoExamArray);
		write(getDateTime(), autoExamArray);
		fireCreateAutoExam(autoExamArray.size(), autoExamArray);

		for (int i = 0; i < autoExamArray.size(); i++) {
			System.out.println(autoExamArray.get(i));
		}
		System.out.println("Exam created on the: " + getDateTime() + ", contains: " + autoExamArray.size() + " questions.");
		autoExamArray.clear();
	}

	public void sortAndPrintManualExamArray() throws IOException {
		if (manualExamArray.isEmpty()) {
			JOptionPane.showMessageDialog(null, "No questions added to the exam");
		}
		else {
			sortByAnswerLength(manualExamArray);
			for (int i = 0; i < manualExamArray.size(); i++) {
				if (manualExamArray.get(i) != null) {
					System.out.println(manualExamArray.get(i));
				}
			}
			write(getDateTime(), manualExamArray);
			int counter = 0;
			for (int i = 0; i < manualExamArray.size(); i++) {
				if (manualExamArray.get(i) != null) {
					counter++;
				}
			}
			JOptionPane.showMessageDialog(null, "Exam created on the: " + getDateTime() + ", contains: " + counter + " questions.");
			System.out.println("Exam created on the: " + getDateTime() + ", contains: " + counter + " questions.");
			manualExamArray.clear();
		}
	}

	public boolean checkQuestionIsInArray(Question quest) {
		if (quest == null) {
			return false;
		}
		for (int i = 0; i < autoExamArray.size(); i++) {
			if (autoExamArray.get(i) != null) {
				if (autoExamArray.contains(quest)) {
					return true;
				}
			}
		}
		return false;
	}

	public Question generateNewQuestion(int amount) {
		Random rand = new Random();
		while (autoExamArray.size() != amount) {
			Question quest = getQuestionById(rand.nextInt(amount));
			while (checkQuestionIsInArray(quest)) {
				quest = getQuestionById(rand.nextInt(amount));
			}
			return quest;
		}
		return null;
	}

	public int checkAllQuestionsLength() {
		int counter = 0;
		for (int i = 0; i < allQuestions.size(); i++) {
			if (allQuestions.get(i) != null) {
				counter++;
			}
		}
		return counter;
	}

	public void addBuiltInAnswers(AmericanQ answers) {
		int trueCounter = 0;
		int falseCounter = 0;
		for (int j = 0; j < answers.getAnswersNum(); j++) {
			if (answers.getAnswers(j).getIsTrue()) {
				trueCounter++;
			} else {
				falseCounter++;
			}
		}
		if (trueCounter == 1) {
			answers.addAnswer(new AmericanAnswers("Nothing is correct", false));
			answers.addAnswer(new AmericanAnswers("More than one answer is correct", false));
		}
		if (trueCounter > 1) {
			answers.addAnswer(new AmericanAnswers("Nothing is correct", false));
			answers.addAnswer(new AmericanAnswers("More than one answer is correct", true));
		}
		if (falseCounter > 0 && trueCounter == 0) {
			answers.addAnswer(new AmericanAnswers("Nothing is correct", true));
			answers.addAnswer(new AmericanAnswers("More than one answer is correct", false));
		}
	}

	public void autoCreateExam(int amount) throws IOException, NumberFormatException {
		try {
			if (amount > 0 && amount <= allQuestions.size()) {
				for (int i = 0; i < amount; i++) {
					Question quest = generateNewQuestion(amount);
					if (quest instanceof OpenQ) {
						OpenQ openQ = (OpenQ) quest;
						autoExamArray.add(openQ);
					}
					if (quest instanceof AmericanQ) {
						AmericanQ ameriQ = (AmericanQ) quest;
						autoExamArray.add(ameriQ);
						addBuiltInAnswers(ameriQ);
					}
				}
				sortAndPrintAutoExamArray();
			}
			else {
				String m = JOptionPane.showInputDialog("Only "+allQuestions.size()+" Questions available.");
				int newAmount = Integer.parseInt(m);
				autoCreateExam(newAmount);
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter a numerical value!");
		}

	}


	public boolean checkInstanceOfQuestion(int questionNum) {
		Question quest = getQuestionById(questionNum);
		if (quest instanceof OpenQ) {
			return false;
		}
		if (quest instanceof AmericanQ) {
			return true;
		}
		return false;
	}

	public void getAmericanAnswer(int questionNum) {
		Question quest = getQuestionById(questionNum);
		if (quest instanceof AmericanQ) {
			AmericanQ aQ = (AmericanQ) quest;
			for (int i = 0; i < aQ.getAnswersNum(); i++) {
				if (!aQ.getAnswers(i).getAnswer().isEmpty()) {
					System.out.println("[" + (i + 1) + "] " + aQ.getAnswers(i));
				}
				else {
					System.out.println("No answers available.");
				}
			}
		}
	}

	public boolean addAmericanQuestionToManualExam(AmericanQ question) {
		if (!manualExamArray.contains(question)) {
			manualExamArray.add(question);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addOpenQuestionToManualExam(OpenQ oQ) {
		if (!(manualExamArray.contains(oQ))) {
			manualExamArray.add(oQ);
			return true;
		}
		else {
			return false;
		}
	}

	public boolean addQuestionToManualExam(int questionNum, List<Integer> answersArray) {
		Question question = getQuestionById(questionNum);
		if (question instanceof OpenQ) {
			OpenQ open = (OpenQ) question;
			addOpenQuestionToManualExam(open);
			return true;
		}
		if (question instanceof AmericanQ) {
			AmericanQ dQ = (AmericanQ) question;
			AmericanQ aQ = new AmericanQ(dQ.getQuestion());

			for (int i = 0; i < answersArray.size(); i++) {
				System.out.println(answersArray.get(i));
				aQ.addAnswer(new AmericanAnswers(dQ.getAnswers(answersArray.get(i)-1)));
			}
			addAmericanQuestionToManualExam(aQ);
			return true;
		}
		return false;
	}

	public int safeNextInt(Scanner e) {
		int num = 0;
		boolean invalid = false;
		do {
			invalid = false;
			try {
				num = e.nextInt();
			} catch (InputMismatchException exception) {
				System.out.println("Expected numerical value.");
				invalid = true;
			}
			e.nextLine();
		} while (invalid);
		return num;
	}

	public boolean safeNextBoolean(Scanner e) {
		boolean crembo = false;
		boolean invalid = false;
		do {
			invalid = false;
			try {
				crembo = e.nextBoolean();
			} catch (InputMismatchException exception) {
				System.out.println("Expected boolean value (true/false).");
				invalid = true;
			}
			e.nextLine();
		} while (invalid);
		return crembo;
	}

	private final static String getDateTime()
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
		return df.format(new Date());
	}


	public void sortAllQuestions() {
		QuestionComparator qC = new QuestionComparator();
		allQuestions.sort(qC);
		System.out.println("Sorted all questions array.");
	}


	public OpenQ getOpenQuestions() {
		for (Question l : allQuestions) {
			if (l instanceof OpenQ) {
				OpenQ oQ = (OpenQ) l;
				return oQ;
			}
		}
		return null;
	}

	public int getAllQuestionSize() {
		return allQuestions.size();
	}

	public Question getAllQuestions(int index) {
		return allQuestions.get(index);
	}

	public void questionsList() {
		//General questions
		String quest1 = "First question z";
		String quest2 = "Second question z";
		String quest3 = "ThirdWithExtraSteps question";
		String quest4 = "Fourth question";
		String quest5 = "FifthWith question";
		String quest6 = "SixthWithExtra question";
		String quest7 = "Seventh question";
		String quest8 = "Eighth question";

		//Just answers
		String ans1 = "First answer";
		String ans2 = "Second answer";
		String ans3 = "Another answer";
		String ans4 = "And another one";
		String ans5 = "Fifth answer";
		String ans6 = "Sixth answer";
		String ans7 = "Seventh answer";
		String ans8 = "Eighth answer";

		//False answers
		String ansF1 = "False answer #1";
		String ansF2 = "False answer #2";
		String ansF3 = "False answer #3";
		String ansF4 = "False answer #4";
		String ansF5 = "False answer #5";

		//Open answers
		String openAns = "First open answer";
		String openAns2 = "Second open answer has many characters aswell";
		String openAns3 = "Third open answer is a very very very very very very very long one";
		String openAns4 = "Fourth open answer is pretty short";

		//Create new american question objects
		AmericanQ ameriTest1 = new AmericanQ(quest1);
		AmericanQ ameriTest2 = new AmericanQ(quest2);
		AmericanQ ameriTest3 = new AmericanQ(quest3);
		AmericanQ ameriTest4 = new AmericanQ(quest4);

		//Add american questions
		addAmericanQuestion(ameriTest1);
		addAmericanQuestion(ameriTest2);
		addAmericanQuestion(ameriTest3);
		addAmericanQuestion(ameriTest4);

		//American answers
		AmericanAnswers ameriAns = new AmericanAnswers(ans1, true);
		AmericanAnswers ameriAns2 = new AmericanAnswers(ans2, true);
		AmericanAnswers ameriAns3 = new AmericanAnswers(ans3, true);
		AmericanAnswers ameriAns4 = new AmericanAnswers(ans4, true);
		AmericanAnswers ameriAns5 = new AmericanAnswers(ans5, true);
		AmericanAnswers ameriAns6 = new AmericanAnswers(ans6, true);
		AmericanAnswers ameriAns7 = new AmericanAnswers(ans7, true);
		AmericanAnswers ameriAns8 = new AmericanAnswers(ans8, true);
		AmericanAnswers ameriAnsF1 = new AmericanAnswers(ansF1, false);
		AmericanAnswers ameriAnsF2 = new AmericanAnswers(ansF2, false);
		AmericanAnswers ameriAnsF3 = new AmericanAnswers(ansF3, false);
		AmericanAnswers ameriAnsF4 = new AmericanAnswers(ansF4, false);
		AmericanAnswers ameriAnsF5 = new AmericanAnswers(ansF5, false);

		//More than one is correct:
		ameriTest1.addAnswer(ameriAns);
		ameriTest1.addAnswer(ameriAns2);
		ameriTest1.addAnswer(ameriAns3);
		ameriTest1.addAnswer(ameriAns4);
		ameriTest1.addAnswer(ameriAnsF1);
		ameriTest1.addAnswer(ameriAnsF4);
		addBuiltInAnswers(ameriTest1);

		//All false
		ameriTest2.addAnswer(ameriAnsF1);
		ameriTest2.addAnswer(ameriAnsF2);
		ameriTest2.addAnswer(ameriAnsF3);
		ameriTest2.addAnswer(ameriAnsF4);
		ameriTest2.addAnswer(ameriAnsF5);
		addBuiltInAnswers(ameriTest2);


		//Only one question is true
		ameriTest3.addAnswer(ameriAns5);
		ameriTest3.addAnswer(ameriAnsF1);
		ameriTest3.addAnswer(ameriAnsF2);
		//ameriTest3.addAnswer(ameriAnsF3);
		//ameriTest3.addAnswer(ameriAnsF4);
		addBuiltInAnswers(ameriTest3);


		//more than one is correct #2
		ameriTest4.addAnswer(ameriAns6);
		ameriTest4.addAnswer(ameriAns7);
		//ameriTest4.addAnswer(ameriAns8);
		//ameriTest4.addAnswer(ameriAnsF3);
		//ameriTest4.addAnswer(ameriAnsF4);
		addBuiltInAnswers(ameriTest4);


		//Open questions + Answers
		addOpenQuestions(quest5, openAns);
		addOpenQuestions(quest6, openAns2);
		addOpenQuestions(quest7, openAns3);
		addOpenQuestions(quest8, openAns4);
	}

	//////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////// Design Patterns Homework //////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////

	public void copyArrayListToTreeSet() {
		treeSet = new TreeSet<Question>(new Comparator<Question>() {
			@Override
			public int compare(Question o1, Question o2) {
				if (o1.getQuestion().length() >= o2.getQuestion().length()) {
					return -1;
				}
				if (o1.getQuestion().length() < o2.getQuestion().length()) {
					return 1;
				}
				return 0;
			}
		});
		treeSet.addAll(allQuestions);
		System.out.println("Copied 'ArrayList' to 'TreeSet' successfully.");
	}

	public void printTreeSet() {
		System.out.println("This is the sorted 'TreeSet' collection: \n");
		Iterator<Question> entryIt = treeSet.iterator();
		while (entryIt.hasNext()) {
			System.out.println(entryIt.next());
		}
	}

	public void copyTreeSetIntoLinkedHashSet() {
		newLinkedHashSet.addAll(treeSet);
		System.out.println("Copied 'TreeSet' into 'LinkedHashSet'.");
	}

	public void printLinkedHashSet() {
		mainIterator = newLinkedHashSet.iterator();
		while (mainIterator.hasNext()) {
			System.out.println(mainIterator.next());
		}
	}

	public void copyOldCollectionToMyArrayList() {
		mainIterator = newLinkedHashSet.iterator();
		while (mainIterator.hasNext()) {
			newList.add((Question) mainIterator.next());
		}
		System.out.println("Copied 'LinkedHashSet' to 'MyArrayList' successfully.");
		fireCreatedMyArrayListInManager(newList);
	}

	private void fireCreatedMyArrayListInManager(MyArrayList newList) {
		for (MainEventsListener l : mainListener) {
			l.createdMyArrayListInManager(newList);
		}
	}

	public void printMyArrayList() {
		Iterator<Question> newIterator = newList.iterator();
		while (newIterator.hasNext()) {
			System.out.println(newIterator.next());
		}
	}

	public void removeQuestionFromMyArrayList(int theQuest) {
		Question current = getQuestionByIdFromNewList(theQuest);
		for (Iterator<Question> newIterator = newList.iterator(); newIterator.hasNext(); ) {
			String itQuestion = newIterator.next().getQuestion();
			if (itQuestion.equals(current.getQuestion())) {
				System.out.println("Removed question: " + itQuestion);
				newIterator.remove();
			}
		}
	}

	//ArrayList, HW2
	public void copyOldCollectionToAnotherArrayList() {
		anotherArrayList.addAll(newLinkedHashSet);
		System.out.println("Copied 'LinkedHashSet' to 'ArrayList' successfully.");
	}

	public void printAnotherArrayList() {
		Iterator<Question> newIterator = anotherArrayList.iterator();
		while (newIterator.hasNext()) {
			System.out.println(newIterator.next());
		}
	}

	public void removeQuestionFromAnotherArrayList(int theQuest) {
		Iterator<Question> iterator = anotherArrayList.iterator();
		Question s = getQuestionByIdFromAnotherArrayList(theQuest);
		System.out.println("Removed question: " + s.getQuestion());
		anotherArrayList.remove(s);
	}

	//Memento, HW3
	public void saveOpenQuestionsToMemento() {
		for (Question l : allQuestions) {
			if (l instanceof OpenQ) {
				careTaker.saveMemento((OpenQ) l);
				Memento m = careTaker.getMemento();
				careTaker.add(m);
				mementoList.add(m);
			}
		}
	}

	public void printSavedStates() {
		System.out.println("Saved states in the careTaker: ");
		for (OpenQ l : careTaker.getMemento().getOpenQList()) {
			System.out.println(l);
		}
	}

	public void deleteAllOpenQuestionsFromAllQuestionsArray() {
		Iterator<Question> iter = allQuestions.iterator();
		while (iter.hasNext()) {
			Question l = iter.next();
			if (l instanceof OpenQ) {
				iter.remove();
				removedQuestionsCount++;
			}
		}
		System.out.println("All Open Questions have been deleted successfully.");
	}

	public void restoreAllOpenQuestions() {
		System.out.println(removedQuestionsCount);
		for (int i = 0; i < removedQuestionsCount; i++) {
			allQuestions.add(careTaker.restoreQuestions(i));
		}
	}

	public void showMultipleStatesOfTheSameQuestion() {
		OpenQ question1 = new OpenQ("What is the capital of France?", "Paris");
		careTaker.saveMemento(question1);
		Memento m1 = careTaker.getMemento();
		savedStatesList.add(m1);

		OpenQ question2 = new OpenQ("What is the capital of France?", "Marseille");
		careTaker.saveMemento(question2);
		Memento m2 = careTaker.getMemento();
		savedStatesList.add(m2);

		OpenQ question3 = new OpenQ("What is the capital of France?", "Lyon");
		careTaker.saveMemento(question3);
		Memento m3 = careTaker.getMemento();
		savedStatesList.add(m3);

		OpenQ question4 = new OpenQ("What is the capital of France?", "Jerusalem");
		careTaker.saveMemento(question4);
		Memento m4 = careTaker.getMemento();
		savedStatesList.add(m4);

		careTaker.printAllStates(savedStatesList);
	}

	int getLinkedHashSetSize() {
		return newLinkedHashSet.size();
	}
}
