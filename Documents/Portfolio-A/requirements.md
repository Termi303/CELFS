Stakeholders
============
- Teaching staff: will use the software for marking exams and coursework
- Admin staff: will use the software for pulling the database into their internal Access database, as well as 
distributing marks to students
- Students: will receive PDFs of their results when the admin staff release it to them
- The University of Bristol: will admit students to courses dependent on whether they pass their exams

Goals
=====

![Use Cases](/Documents/Portfolio-A/useCases.png)

Mark Work
---------
Basic Flow:
1. Receive exam transcript
1. Login to software
1. Enter student id
1. Read transcript
1. For each criterium, select which band it falls into
1. Add comments
1. Review the bands chosen and overall grade
1. Submit grade

Alternative Flow #1:
1. Receive exam transcript
1. Read transcript
1. Login to software
1. Enter student id
1. For each criterium, select which band it falls into
1. Add comments
1. Review the bands chosen and overall grade
1. Submit grade

Alternative Flow #2:
1. Receive exam transcript
1. Login to software
1. Enter student id
1. Read transcript
1. For each criterium, select which band it falls into
1. Add comments
1. Review the bands chosen and overall grade
1. Edit bands chosen
1. Edit comments
1. Review the bands chosen and overall grade
1. Submit grade

Exceptional Flow:
1. Login to software
1. Enter student id
1. Select random bands
1. Add random comments
1. Review the bands chosen and overall grade
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

1. Staff must login via Single Sign-on
1. Staff must choose which type of work to mark
1. Staff must input a student number for each piece of work
1. Criteria appropriate to each type of work must be displayed
1. Key words must be highlighted
1. A key word's definition must popup when hovered over
1. Staff must choose a band for each criterium
1. Only a single band must be chosen for each criterium
1. The software must calculate the average mark from the bands inputted
1. The software must round the mark to the nearest in the list of possible marks
1. Staff must be able to add a comment to each criterium, to justify why they chose that particular band
1. Staff must be able to add an overall comment to the work
1. The software must summarise all the bands chosen, the comments and the overall grade before submitting, to 
reduce human error
1. Staff must then be given the option to edit the bands and comments, or to submit the grade
1. The student number, grade and comments should then be stored in a database
1. The software should produce a PDF of the results, available for download

Non-functional Requirements
---------------------------

1. The database must comply with the GDPR
1. Staff must not be able to change the username they're logged in with, for transparency
1. Admin staff must be able to pull the spreadsheet into a spreadsheet at any time