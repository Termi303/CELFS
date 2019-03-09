Stakeholders
============
- Ms Hannah Gurr: Hannah is the one who proposed the project, and is our primary source of contact with the CELFS. She will ulitmately dictate what we will implement, and as a teacher she has the most knowledge about how the website will be used to mark courseworks and exams.
- Other Teaching Staff: They will use the website to mark coursework and exams as well, so it is imperative that it meets their requirements.
- Admin Staff: They are responsible for handling the marks once they have been submitted and releasing them to the student cohort. Additionally, the admin staff handle double marking, late penalties and such, and they liase with the wider University. As such, it is important we build a system they can use.
- Students: The website will be used to mark students' courseworks and exams, so they will be heavily invested in the robustness and reliability of the system.
- The University of Bristol: The University will admit students onto its courses based on the results of their foundation year. Additionally, as the database will store student information, we will have to comply with the University data protection regulations.

Use Cases
=====

![Use Cases](/Files/Documents/Portfolio-A/UseCases.png)

Mark Work
---------
Basic Flow:
1. Receive exam transcript
1. Login to software
1. Enter student id
1. Read transcript
1. For each criterium, select which band it falls into
1. Add comments
1. Review the bands chosen and the overall grade
1. Submit grade

Alternative Flow #1:
1. Receive exam transcript
1. Read transcript
1. Login to software
1. Enter student id
1. For each criterium, select which band it falls into
1. Add comments
1. Review the bands chosen and the overall grade
1. Submit grade

Alternative Flow #2:
1. Receive exam transcript
1. Login to software
1. Enter student id
1. Read transcript
1. For each criterium, select which band it falls into
1. Add comments
1. Review the bands chosen and the overall grade
1. Edit bands chosen
1. Edit comments
1. Review the bands chosen and the overall grade
1. Submit grade

Exceptional Flow:
1. Login to software
1. Enter student id
1. Select random bands
1. Add random comments
1. Review the bands chosen and the overall grade
1. Submit grade

PDF of Results
--------------
Basic Flow:
1. Submit grade to database
1. Download PDF summary of grade
1. Send to admin staff

Alternative Flow:
1. Download PDF summary of grade
1. Submit grade to database
1. Send to admin staff

Exceptional Flow:
1. Submit grade to database
1. Download PDF summary of grade

Access Database
---------------
Basic Flow:
1. Teachers fill in every student's grade to database
1. Admin staff pulls the database through to an Excel spreadsheet
1. Admin staff executes a query to import the spreadsheet to their Access database
1. The Access database calculates whether each student passed the course
1. The grades are passed on to the University
1. Admin staff releases the grades and PDFs to the students
1. The University either accepts or rejects students

Alternative Flow:
1. Teachers fill in some students' grades to database
1. Admin staff pulls the database through to an Excel spreadsheet
1. Admin staff executes a query to import the spreadsheet to their Access database
1. Teachers fill in the rest of the students' grades to database
1. Admin staff pulls the database through to update the spreadsheet
1. Admin staff imports the new spreadsheet to the Access database
1. The Access database calculates whether each student passed the course
1. The grades are passed on to the University
1. Admin staff releases the grades and PDFs to the students
1. The University either accepts or rejects students

Exceptional Flow:
1. Teachers fill in some students' grades to database
1. Admin staff pulls the database through to an Excel spreadsheet
1. Admin staff executes a query to import the spreadsheet to their Access database
1. The Access database calculates whether each student passed the course
1. The grades are passed on to the University - some students are missing grades
1. Admin staff releases the grades and PDFs to the students - some students have the wrong grade
1. The University either accepts or rejects students

Atomic Requirements
===================
**Goal:** Mark Work

Functional Requirements
-----------------------

- AUTH 1.0: The user has to be logged in to access the page.
- AUTH 1.1: The user has to have the TEACHER authority level to access the page.
- AUTH 2.0: The user's ID will be automatically submitted with the marks.

- CATEG 1.0: The correct name for each category must be displayed.
- CATEG 1.1: Each category for the type of work must be displayed.

- COMMENT 1.0: For each row of criteria, the user must be able to anter a comment.
- COMMENT 1.1: When the user clicks on 'Add Comment', the textarea for that row must appear.
- COMMENT 1.2: When the user clicks on 'Add Comment', the text link for that row must change to 'Hide Comment'.
- COMMENT 1.3: When the user clicks on 'Hide Comment', the textarea for that row must disappear.
- COMMENT 1.4: When the user clicks on 'Hide Comment', the text link for that row must change to 'Edit Comment'.
- COMMENT 2.0: The user must be able to enter an overall comment for the work.

- CRIT 1.0: Each row of criteria for the type of work must be displayed.
- CRIT 1.1: Each row of criteria must have the correct name.
- CRIT 1.2: Each criteria name must be displayed in bold.
- CRIT 2.0: Each criterium must have its corresponding description displayed.
- CRIT 2.1: For criterium must be displayed in the table according to its band.
- CRIT 2.2: If a row of criteria does not have a criterium for a certain band, 'N/A' must be displayed.
- CRIT 3.0: The user must select a criterium for each row.
- CRIT 3.1: If the user hasn't selected a criterium for each row, they must not be allowed to submit the work.

- INTERFACE 1.0: Each category must be displayed in a unique colour.
- INTERFACE 2.0: Each colour must contrast.

- KEYW 1.0: All keywords must be underlined.
- KEYW 1.1: All keywords must be displayed in red.
- KEYW 2.0: When the user hovers over a keyword, its definition must be displayed.
- KEYW 2.1: When the user hovers over a keyword, the correct definition must be displayed.
- KEYW 3.0: All different forms of a keyword must be highlighted the same.
- KEYW 3.1: All different cases of a keyword must be highlighted the same.

- NAME 1.0: The correct name for the type of work must be displayed in the heading.

- STUDENT 1.0: The user must input a student ID to submit.
- STUDENT 1.1: The student ID must be valid.
- STUDENT 1.2: The student ID must correspond to an existing student.
- STUDENT 1.3: The user must not be allowed to submit the work if they haven't inputted a student ID.

- SUBMIT 1.0: When the user clicks on the 'Review' button, they must be redirected to the review page.

Non-functional Requirements
---------------------------

1.  Requirements for the Database

    As our database will hold student information, it must comply with both the GDPR and the University's data protection regulations. 
    CELFS already have an existing MS Access database which they use, and they pull the data from an Excel spreadsheet. The admin staff 
    must be able to export our own database to a spreadsheet at any time, regardless of whether every student's mark has been submitted.
