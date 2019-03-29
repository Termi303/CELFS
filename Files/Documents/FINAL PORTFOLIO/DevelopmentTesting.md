Development Testing
===================

Back End
--------

We test our application's functionality with JUnit. All code written is abiding to our coding standards.

**Marking**

The marking system can be tested by comparing the input with the output. The marks can only be one of 0, 7, 15, 22, 29, 36, 42, 45, 48, 52, 55, 58, 62, 65, 68, 72, 75, 78, 83, 89 or 94, so whatever the program outputs it has to be rounded to one of those.

The grades will be calculated by assigning the median mark to each band then taking the average of all the criteria. This process can be
tested with simple maths. Multiple grades will be produced: one for each category, and an overall grade. The overall grade should be taken
as an average of each individual criterium, not the average of the rounded grades for each category.

There is still ambiguity in how the work is marked, however. We will need to confirm with the client whether our automated marking system
is accurate.

**Database**

The functions for communicating with the database is unit tested. We're storing the criteria for each piece of work in tables, so we
know what each query should return and can assert whether it actually does what it's supposed to.

**Standalone IP Address**

By default the IP addresses for Oracle Cloud Virtual Machines are assigned automatically when they are created and are lost when they are terminated. This causes problems when there is a problem with the virtual machine which cannot be easily resolved and a new one is needed. Those problems are exacerbated by having a domain name, the routing for which must be changed every time there is change in the IP address.

That is why we use reserved IP addresses. In this case the IP address is created separately from the VM, and then assigned to a VM. This makes it possible to change VM instances without the need to change the IP.

**Continuous Integration**

Continuous Integration is done through Circle Ci. Every commit on every branch creates an auto-testing cycle, which is used to determine whether or not the code is correct.

**Continuous Deployment**

Continuous Deployment is achieved by collaboration of Circle Ci and the Oracle server. Every commit on the master branch triggers a cycle. Firstly the code is automatically tested (as on every other branch). After that an ssh connection to the oracle server is established. Firstly the instance of the website on the oracle server is terminated. After that the folder of the oracle server website copy is deleted. After this the whole folder is sent to the oracle server, and then executed.

Front End
---------

**User Interface**

We will use integration tests on the user interface, working with the client as we go. We've been given the official CELFS branding, which
we will incorporate into the design.

Coding standards
----------------
To organize code style and organization, coding standards were introduced. Their coverage include:
+ Source file basics and structure
+ Formatting:
    1. 'One per' rules (i.e. one statement per line)
    1. Whitespace usage
    1. Braces
+ Naming conventions:
    1. CamelCase definition
    1. Class and methods names
    1. Package names
    1. Constant names
+ General programming practices (i.e. call static method through a class, not an object)
