OO Design and UML
=================
Architecture
------------

![Architecture](/Documents/FINAL PORTFOLIO/Architecture.png)

Each user is certified through the University's Single Sign-on. Once done, their information is passed on
to our application, and they interact with web pages to input students' marks. This data is then stored in
our database.

Our database can be pushed through into an Excel spreadsheet which CELFS can then pull into their internal
MS Access database.

Context
-------

The most important part of the system has from the beginning been marking system for courseworks. Each coursework is represented by four types of elements:
1. Category (at least one per Coursework)
1. Criterion (at least one per Category)
1. Cell (look below for number)
1. Band

Bands represent quality of work (i.e. "Exceptional", "Very good"). Bands are the same for all courseworks. For each pair (Criterion, Band), there is up to one Cell, which holds a detailed description of what student must have achieved to receive that band.

Moreover, the client set the requirement to do "double marking" - two teachers could mark the same coursework for the same student and then could use admin's support to insert the final mark.

Database
--------

![Database schema](/Documents/FINAL PORTFOLIO/Final_database_UML.png)

Database for MVP includes one coursework type - micro research report. Bands for every coursework type will be same - these will be stored in bands table.

Each coursework type will have 4 tables to represent it: categories, criteria, cells (sum of these 3 will represent CELFS marking table) and major table to store crucial coursework data - this includes student ID, marker ID and overall score.

To store the major table, two helper tables will be used - students and teachers. Both will have attributes necessary to identify teacher/student (i.e. ID, first and last name, seat, etc.).

OO design
---------
![OO design](/Documents/FINAL PORTFOLIO/OO_Design.png)

For Minimal Viable Product, three webpages are planned: SelectWork, MarkWork and ReviewWork. For each of these pages, related controller will be implemented to present adapted content. This includes showing right mark table in MarkWork webpage.

Class Mark will be implemented to store temporary version of Mark. After successful review (in ReviewWork), data from the object will be inserted in database. Another purpose of the class is to restore the MarkWork form efficiently if user decides to go back from ReviewWork page.

Class WorkType will be a helper class with two purposes. First, SelectWork page will use list of WorkType objects to present coursework and exam types (with links to MarkWork page with proper attributes). Second, WorkType object will be an attribute in class Mark to insert data into right table.

Class MarkTuple will be a simple helper class for Mark class. This will store comments (for a certain category).
