Stakeholders
============
- Teaching staff: uses the software for marking exams and coursework
- Admin staff: uses the software for pulling the database into their internal Access database, as well as 
distributing marks to students
- Students: receive PDFs of their results when the admin staff releases it to them
- The University of Bristol: admits students to courses depending on whether they pass their exams

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

1.  Log in

    Currently, for each piece of work staff need to input their initials manually, to identify who marked what. Instead, our 
    software will require login via Single Sign-on, which will automatically retreive their username. This way, staff forgetting 
    to input their initials won't be a problem.
1.  Marking

    For each student, there are several different types of work that contribute to their final grade. Staff must input a student 
    number and choose which type of work to mark. Then, criteria appropriate to each type of work will be displayed, arranged by 
    bands. Staff must choose a single band for each criterium, and they will have the option to add comments next to each one to 
    explain why they've chosen a certain band. Additionally, they may enter an overall comment for the work.
1.  Criteria

    In each criterium, key words will be in a different colour and underlined for visibility. When staff hover over the word, 
    its definition will pop up. This is to aid in the marking process and to clarify any ambiguity in the criteria.
1.  Reviewing

    After filling out the criteria, staff will be taken to a review page which summarises the band for each criterium, any comments 
    made and the overall mark. This is to avoid human error. If they're satisfied, they will then submit the information. If not, 
    they can go back to the marking page and edit any of the information.
1.  Submitting

    After submitting, the information is sent to a database. Additionally, the software will produce a PDF of the review page, 
    available for download. This will then be passed on to the student when the admin staff release their marks.

Non-functional Requirements
---------------------------

1.  Requirements for the Database

    As our database will hold student information, it must comply with both the GDPR and the University's data protection regulations. 
    CELFS already have an existing MS Access database which they use, and they pull the data from an Excel spreadsheet. The admin staff 
    must be able to export our own database to a spreadsheet at any time, regardless of whether every student's mark has been submitted.
