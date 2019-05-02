OO Design and UML
=================
Context
-------

The most important part of the system has from the beginning been the marking system for coursework. Each coursework in paper version is represented by three types of elements:
1. Category (at least one per Coursework)
1. Band
1. Cell (look below for number)

Bands represent the quality of work (i.e. "Exceptional", "Very good"). Bands are the same for all types of coursework. For each pair (Category, Band), there is a Cell, which holds a number of criteria a student must meet to receive the Band for the Category. Each Criterion consists of a title and description of how well student must perform in this criterion, for example, "Method" and "Personalised rationale for 3 appropriate sources given; limitations explained". There are a number of different types of coursework a student may hand in.

Moreover, the client set the requirement to do "double marking" - two teachers could mark the same coursework for the same student and then could use admin's support to insert the final mark.

Architecture
------------

![Architecture](/Files/Documents/FINAL_PORTFOLIO/architectureDiagram.png)

The page uses Spring Security to authenticate the user. Once done, the user interacts with the website to use implemented functionalities, such as inputting marks or seeing them. The database is stored using CrudRepository. Passwords are stored safely using BCryptPasswordEncoder from Spring Security.

Our database can be pushed through into an Excel spreadsheet which CELFS can then pull into their internal
MS Access database.

Database
--------

![Database schema](/Files/Documents/FINAL_PORTFOLIO/Final_database_UML.png)

Database for Final Release realizes the context and provides flexibility for extension. We decided to systematize given tables by having a subtable for each Category. Such subtable's row would be represented by a Criterion - columns stay the same (Bands). The database schema can be divided into two parts: first represents the coursework, second represents marks for students.

**Storing courseworks**
[All classes have id of type Long]
1. *Coursework* - this class only stores name, as the rest of the information, is stored in later classes
1. *Category* - stores name, obligatory reference to Coursework it represents and weight for this category. To calculate the final mark, marks from all categories are multiplied with that weight and then summed
1. *Criterion* - stores name of criterion and reference to the Category
1. *Band* - as described earlier, the list of bands is identical for all courseworks. As a result, this class does not have reference to any of the three previous classes. It stores name and description, as this was required to make easier for teachers and admins to know what each band represents (i.e. band "Very good" has the description "A very good pass is clear, fluent and accurate and should show extensive application of learning")
1. *Cell* - as described in context, each Cell represents a mark (Band) for certain Criterion. Thus, it has a reference to Criterion and Band it represents. Moreover, Cell class has description attribute, for example, for Band "Exceptional" and Criterion "Response", the description is "Rigorous, lucid, creative & original response". If, in the paper version, there was no representative description for (Criterion, Band) pair, the description becomes "N/A" and front-end makes the Cell not choosable.

As you can see, our system enables to store any number of Categories per Coursework, any number of Criteria per Category and any number of Band. This was implemented as pieces of coursework designed in the future in CELFS may have a different number of above attributes. Of course, changing a number of Bands is difficult in this design (as this requires adding/deleting records in Cell table), but it was decided to be implemented this way as the list of Bands is expected to same identical in the future and benefits of this design outdo the above risk.

**Storing marks**
[Again, all classes have id of type Long]
1. *CourseworkEntry* - it stores overall score for the Coursework, student for whom this is marked, teacher who did the marking and of course the coursework that is marked for the student. Moreover, it stores a boolean value which states whether the mark is a final mark resolved with double mark schema. This enables to state three states of marking:
    1. There is one mark for pair (Student, Coursework) and it can stay as a single mark or, if another mark is inserted for the same pair in the future, it will proceed to double marking
    1. There are two marks for pair (Student, Coursework) - they wait until admin inserts the final mark
    1. Admin has inserted the final mark, which also means that previous marks have been deleted. The boolean value described above is set to True and cannot be modified.
1. *CategoryEntry* - this class stores the Category this entry represents, a mark which student achieved for the referenced Category and reference to the marked entry.
1. *CellEntry* - here we store certain Cells that were chosen by the marker. The class, therefore, stores reference to Cell class and comment to the Criterion this cell references. Note that because Cell stores reference to Criterion and Band, we do not need to create CriterionEntry class as it would store no additional information.

Note that the system stores exactly the information it needs and nothing more (i.e. no CriterionEntry). Moreover, changes to the first part of the database do not affect the Entry storing system (of course unless the related Coursework/Category/Cell is deleted).

Dynamic UML
-----------

![Dynamic UML](/Files/Documents/FINAL_PORTFOLIO/Dynamic_UML.png)

The Dynamic UML chart above shows how the mark is inserted into the database. It starts in the review page after the teacher has reviewed what he/she inserted in the coursework page. Now let's go step by step in detail with what's going on:
1. Admin click "Submit" button on review page which activates submitting the mark into the database. This activates submitCoursework() method in the MainController - this method is responsible for inserting CourseworkEntry, CategoryEntries and CellEntries.
1. The client set a requirement to check whether a student exists in the database. That's what search function in studentService does - if Student object is in the database, return it. Otherwise, search function throws Exception and Controller returns "/error" which activates error page.
1. We will first create and insert the courseworkEntry, then all categoryEntries and cellEntries. As mentioned in the previous section, there are specific requirements for a number of entries which can be inserted for the pair (Student, Coursework). The method addCourseworkEntry in courseworkEntryService checks whether it fits these requirements. If not, then as in Student search, an error page will be shown. If yes, then the services calls save method in the CourseworkEntryRepository.
1. After saving the courseworkEntry, submitCoursework() then add all CategoryEntries in the same manner with one difference - there are no additional requirements, so there are no alternatives.
1. After saving all CategoryEntries, all CellEntries are added in the same way.
1. Then controller redirects to result page, which is shown to the teacher.

**Additional information**

Note that all communication between MainController and repositories is done through services. This gives a better separation between front-end and back-end. Moreover, you can see from the diagram that the system checks all object requirements in the services, not in their respective classes. We did this because most of the validation rules relate to a number of specific entries in the database.
