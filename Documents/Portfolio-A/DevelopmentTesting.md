Development Testing
===================
![Architecture](/Documents/Portfolio-A/Architecture.png)

Back End
--------

We will unit test our application's functionality with JUnit. All code will be written with reference to our coding standards.

**Marking**

The marking system can be tested by comparing the input with the output. The marks can only be one of 0, 7, 15, 22, 29, 36, 42, 45, 48, 52, 55, 58, 62, 65, 68, 72, 75, 78, 83, 89 or 94, so whatever the program outputs it has to be rounded to one of those.

The grades will be calculated by assigning the median mark to each band then taking the average of all the criteria. This process can be
tested with simple maths. Multiple grades will be produced: one for each category, and an overall grade. The overall grade should be taken
as an average of each individual criterium, not the average of the rounded grades for each category.

There is still ambiguity in how the work is marked, however. We will need to confirm with the client whether our automated marking system
is accurate.

**Database**

The functions for communicating with the database will be unit tested. We're storing the criteria for each piece of work in tables, so we
know what each query should return and can assert whether it actually does what it's supposed to.

Front End
---------

**User Interface**

We will use integration tests on the user interface, working with the client as we go. We've been given the official CELFS branding, which
we will incorporate into the design.
