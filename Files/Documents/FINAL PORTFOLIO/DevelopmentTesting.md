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

**Continuous Integration**

Continuous Integration is done through Circle Ci.
Every time there is a commit on any branch, automatic testing cycle is invoked. It pulls those changes from the repository, builds the project and runs all the tests. This makes it easy to keep track of whether our code is correct.

**Continuous Deployment**

Continuous Deployment is achieved by collaboration of Circle Ci and a virtual machine, which is being run on the Oracle Cloud.
Every commit on the master branch triggers an extended cycle, which has the folowing steps:
1. The code is automatically pull, built and tested (as on every other branch).
1. An ssh connection to the oracle server is established.
1. The script "script.sh" on the virtual machine is run. It terminates all processes running on port 80 or 8080. It also takes into account that there might not necessarily be any processes running on either of those ports, for example if the virtual machine was recently restarted. That's why both functions to kill the processes are run using "scriptfunction || true". This makes sure that even if the function fails, the Circle Ci cycle would not stop.
1. The project folder on the Oracle VM is deleted.
1. The whole project folder from Circle Ci is sent to the oracle server, in the same place where the deleted folder used to be.
1. The project is then executed, making redirecting all the output to "/dev/null", so that the cycle can safely proceed.
1. Then the IP tables are flushed.

**Standalone IP Address**

By default the IP addresses for Oracle Cloud Virtual Machines are assigned automatically when they are created and are lost when they are terminated. This means that if there is a problem with the virtual machine which cannot be easily resolved and a new one is needed, we would lose out IP address.

This causes at least two problems. Firstly, the Continuous Deployment would have to be updated every time. Secondly, the routing for the domain name must be changed every time.

That is why we use reserved IP addresses. In this case the IP address is created separately from the VM, and then assigned to a VM. This makes it possible to change VM instances without the need to change the IP.

<!---
**SSL/TLS Certificate**

SSL/TLS Certificate was obtained through the "Let’s Encrypt" free service. This allowed us to use https properly.

-->

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
