# Object-Oriented Programming - Project.
Creators Name: Benjamin Rain, Eli Levy
ID: 319520425, 206946790

# Object-Oriented Programmg course:
* Part 1: Hierarchy, Polymorphism, Object and Exceptions. [Done]
* Part 2: Interfaces, files, generics [Done]
* Part 3: GUI & MVC [Done]

# Design Patterns course:
* Part 1: Collections, Generics, Lambda Expressions, Iterator.
* Part 2: ArrayList, Design Patterns: Iterator, Observer.
* Part 3: Design Patterns: Command, Memento.

* <https://github.com/TulakHordia>

## Table of contents

> * [Object-Oriented Programming - Project / github.com/TulakHordia/OOPProject]
>   * [Table of contents]
>   * [Usage]
>   * [Content]
>   * [For known issues please refer to the known_issues file]

## About / Synopsis
Whole project is complete.
A program used to create, edit and manipulate questions.
Create exams automatically or even manually and save them into a .txt file.
Import/Export binary files, save to .txt files.

## Usage
- Run the program and use the built-in console in the IDE to traverse through the options.
- Type 1 to import a pre-made questions list, 2 to ignore it (it will import anyway at this point).
- TYPE 3 TO OPEN THE UI.
- For 'Design Patterns' course Part 1, use Options 14 to 17.
- For 'Design Patterns' course Part 2, use Options 18 to 25.

> * [Object-Oriented Programming - Project / github.com/TulakHordia/OOPProject]

> * [PART 1 USAGE INSTRUCTIONS]
>	* INSTRUCTIONS
> * * * Select [14] to copy and sort allQuestions (ArrayList) into a new collection (TreeSet).
> * * * Select [15] to copy and sort the previous collection (TreeSet) into a new one (LinkedHashSet).
> * * * Select [16] to print the TreeSet.
> * * * Select [17] to print the LinkedHashSet.

> * [PART 2 USAGE INSTRUCTIONS]
>	* INSTRUCTIONS
> * * * Select [18] to copy the previous collection (LinkedHashSet) to 'MyArrayList' (question #17&18).
> * * * Select [19] to print 'MyArrayList'. (question #19).
> * * * Select [20] remove a question from 'MyArrayList'. (question #20).
> * * * * * The remove basically shifts the whole array one step left, overriding the value we want to "Remove".
> * * * * * It doesn't really "REMOVE" it, it overrides it completely and remove the last index (suppoused to be NULL after the shift).
>
> * * * Select [21] to copy the previous collection (LinkedHashSet) to a new one (ArrayList). (question #21).
> * * * Select [22] to print the ArrayList. (question #21).
> * * * Select [23] to remove a question from ArrayList. (question #21).
>
> * * * Options [18],[19],[20] are all located at: Line 933-963 in Manager.class, along with 'MyArrayList.class' that has the rest.
> * * * Options [21],[22],[23] are all located at: Line 965-982 in Manager.class.

> * * * Select [24] to have the whole process done for the 'MyArrayList' part (whole part 1 + [18],[19],[20]).
> * * * Select [25] to do the same with ArrayList.

> * * * TO LAUNCH THE UI EITHER SELECT OPTION [26] OR RESTART THE PROGRAM AND SELECT [3].

>	* Question #23-24-25 usage (Observer Design Pattern):
> * * * To see how the 'Observer' design pattern works, Select [26] in the menu to Launch the UI. OR Select option 3 when launching the whole Program.
> * * * Press "Import from a premade questions list" button located in the middle of the UI.
> * * * Then on the bottom click "Create 'MyArrayList'", this will create all the previous lists and turn the 'Iterator' online.
> * * * The newly shown button will print 'MyArrayList' into the IDEs console.

> * [PART 3 USAGE INSTRUCTIONS]
>	* INSTRUCTIONS
> * * * Select option [3] on launch to open the UI.
> * * * Question 26 (Command design pattern):
> * * * The 'Exit','Import Premade Questions' and 'Create MyArrayList' buttons have all been changed to work with the Command design pattern.
> * * * I have added the following classes:
> - CreateMyArrayListCommand.class, ExitCommand.class, ImportCommand.class, along with 'Command' interface that holds the execute() command.
> - The changes can be seen in 'QuestionView.class' class, lines 480-520.
> - Along with 'MainController.class', lines 110-125.
> * * * Question 27 (Memento design pattern):
> * * * 

## Content
Contains a way to add a new question + answer. (American or an Open one)
Double clicking the questions table will enter "edit mode" and allow you to edit the question (by pressing Enter when done).
Can see the Answers for an american question in a separate window.
Can import & export files in and out of the program.
Can opt into creating an automatic exam with set amount of questions or a manual one by selecting them in the table.
Functions to manually create an exam with the available questions or rather let the program create one for you.

* Project consists of 4 Packages working in an MVC module.
* Model, View, Controller and Listeners.
* Listeners listen to changes in the View and Model classes and update the Controller accordingly.
* View package consists of an Abstract interface for every GUI stage.

## Work to be done
Nothing, project is done, good luck to us. :)

### Thank you for using the program.
### Best regards,
### Benjamin Rain. :)

