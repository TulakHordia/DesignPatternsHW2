# Object-Oriented Programming - Project.
Creators Name: Benjamin Rain, Eli Levy
ID: 319520425, 206946790

# Object-Oriented Programmg course:
* Part 1: Hierarchy, Polymorphism, Object and Exceptions. [Done]
* Part 2: Interfaces, files, generics [Done]
* Part 3: GUI & MVC [Done]

# Design Patterns course:
* Part 1: Collections, Generics, Lambda Expressions, Iterator.

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
- For 'Design Patterns' course Part 1, use Option 14 & 15 in the menu.
- Option 14 -> 1 for question 12.
- Option 14 -> 2 to copy the 'HashMap' from question 12 to a 'HashSet' (Question 13).
- Option 15 to add a new 'Question' to the 'HashSet'.
> * [Object-Oriented Programming - Project / github.com/TulakHordia/OOPProject]


> * [PART 1 USAGE INSTRUCTIONS]
>	* INSTRUCTIONS
> * * * For the first part I have used the allQuestions list, located in 'Manager' class, line '28', '124'.
> * * * I used 'TreeMap' collection for sorting the 'allQuestions' with duplicates.
> * * * For Q2 (Sub Question 13) I have used a 'HashSet' collection.
> * * * I copied the 'HashMap' into 'HashSet' with the function copyToHashSet();
> * * * Every new value you want to add to the 'HashSet' collection goes to addNewStringToHashSet() and then through StringComparator class
> * * * to compare the new 'Question' with the 'HashSet' values by the String Length and if the letter 'a' shows up the same amount of times in both strings.
> * * * 'StringComparator.class' helps with that.
> * * * The 'HashSet" is then printed using the printHashSet() method.


> * [PART 2 USAGE INSTRUCTIONS]
>	* INSTRUCTIONS
> * * * For part 2 we added 'MyArrayList', 'Observable', 'MyButton', 'MyLabel' classes and 'Observer' interface.

>	* Question 17-18-19-20-21-22 usage:
> * * * Launch the program, press '1' to import the premade questions, select '14' -> '15' -> '19' to copy the previous lists to the new 'MyArrayList'.
> * * * Select '20' to print the newly made 'MyArrayList'.
> * * * Select '21' to remove a question by the question ID.

>	* Question 23-24-25 usage:
> * * * To see how the 'Observer' design pattern works, Select '22' in the menu to Launch the UI.
> * * * Press "Import from a premade questions list".
> * * * Then on the bottom click "Create 'MyArrayList'", this will create all the previous lists and turn the 'Iterator' online
> * * * The newly shown button will print 'MyArrayList' into the IDEs console.

- Everything is located in 'manager.class' unless stated otherwise.

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

