package Model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

interface ProgramMethods {

	void managingMethod();
	void menuOptions();
	void mainMenu() throws IOException, ClassNotFoundException, SQLException;
	void autoImportQuestions() throws ClassNotFoundException, IOException;
	boolean importAndSaveQuestionsList(String[] args) throws FileNotFoundException, IOException;
}
