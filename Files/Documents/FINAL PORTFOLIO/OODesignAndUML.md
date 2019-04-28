OO Design and UML
=================
Context
-------

The most important part of the system has from the beginning been marking system for courseworks. Each coursework is represented by four types of elements:
1. Category (at least one per Coursework)
1. Criterion (at least one per Category)
1. Cell (look below for number)
1. Band

Bands represent quality of work (i.e. "Exceptional", "Very good"). Bands are the same for all courseworks. For each pair (Criterion, Band), there is up to one Cell, which holds a detailed description of what student must have achieved to receive that band. Students may hand in a number of types of coursework.

Moreover, the client set the requirement to do "double marking" - two teachers could mark the same coursework for the same student and then could use admin's support to insert the final mark.

Architecture
------------

![Architecture](/Documents/FINAL PORTFOLIO/Architecture.png)

Each user is certified through the University's Single Sign-on. Once done, their information is passed on
to our application, and they interact with web pages to input students' marks. This data is then stored in
our database.

Our database can be pushed through into an Excel spreadsheet which CELFS can then pull into their internal
MS Access database.

Database
--------

![Database schema](/Documents/FINAL PORTFOLIO/Final_database_UML.png)

Database for Final Release realizes the context and provides flexibility for extension. Database schema can be divided into two parts: first represents the coursework, second represents marks for students. Let's describe elements of the first part:

[All classes have id of type Long]
1. Coursework - this class only stores name, as the rest of information is stored in later classes
1. Category - stores name, obligatory reference to Coursework it represents and weight for this category. To calculate the final mark, marks from all categories are multiplied with that weight and then summed
1. Criterion - stores name of criterion and reference to category
1. Band - as described earlier, the list of bands is identical for all courseworks. As a result, this class does not have reference to any of three previous classes. It stores name and description, as this was required to make easier for teachers and admins to know what each band represents (i.e. band "Very good" has description "A very good pass is clear, fluent and accurate and should show extensive application of learning")
1. Cell - as described in context, each Cell represents a mark (Band) for certain Criterion. Thus, it has a reference to Criterion and Band it represents. Moreover, Cell class has description attribute, for example, for Band "Exceptional" and Criterion "Response", the description is "Rigorous, lucid, creative & original response"

The second part is made up of three classes - again, all classes have id of type Long:
1. CourseworkEntry - it stores overall score for the Coursework, student for whom this is marked, teacher who did the marking and of course the coursework that is marked for student. Moreover, it stores a boolean value which states whether the mark is a final mark resolved with double mark schema. This enables to state three states of marking:
    1. There is one mark for pair (Student, Coursework) and it can stay as a single mark or, if another mark is inserted for the same pair in the future, it will proceed to double marking
    1. There are two marks for pair (Student, Coursework) - they wait until admin inserts the final mark
    1. Admin has inserted the final mark, which also means that previous marks have been deleted. The boolean value described above is set to True and cannot be modified.
1. CategoryEntry - this class stores the Category this entry represents, mark which student achieved for the referenced Category and reference to the marked entry.
1. CellEntry - here we store certain Cells that were chosen by marker. The class therefore stores reference to Cell class and comment to the Criterion this cell references. Note that because Cell stores reference to Criterion, we do not need to create CriterionEntry class as it would store no additional information

Dynamic UML
-----------

![Dynamic UML](/Documents/FINAL PORTFOLIO/Dynamic_UML.png)

//HERE DESCRIBE STEP BY STEP WHAT'S GOING ON

Note that all communication between MainController and repositories is done through services. This gives better separation between front-end and back-end.  
