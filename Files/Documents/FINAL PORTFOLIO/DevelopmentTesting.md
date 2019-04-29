Development Testing
===================

Back End
--------

**Database**

The functions for communicating with the database is unit tested. We're storing the criteria for each piece of work in tables, so we
know what each query should return and can assert whether it actually does what it's supposed to.

**Continuous Integration**

Continuous Integration is done through CircleCI.
Every time there is a commit on any branch, automatic testing cycle is invoked. It pulls those changes from the repository, builds the project and runs all the tests. This makes it easy to keep track of whether our code is correct.

**Continuous Deployment**

Continuous Deployment is achieved by collaboration of CircleCI and a virtual machine, which is being run on the Oracle Cloud.
Every commit on the master branch triggers an extended cycle, which has the following steps:
1. The code is automatically pulled, built and tested (as on every other branch).
1. An ssh connection to the Oracle server is established.
1. The script "script.sh" on the virtual machine is run. It terminates all processes running on port 443, 80 and 8080. It also takes into account that there might not necessarily be any processes running on either of those ports, for example if the virtual machine was recently restarted. That's why both functions to kill the processes are run using "scriptfunction || true". This makes sure that even if the function fails, the CircleCI cycle would not stop.
1. The project folder on the Oracle VM is deleted.
1. The whole project folder from Circle Ci is sent to the oracle server, in the same place where the deleted folder used to be.
1. The project is then executed, redirecting all the output to "/dev/null", so that the cycle can safely proceed.
1. Then the IP tables are flushed.

**Standalone IP Address**

By default the IP addresses for Oracle Cloud Virtual Machines are assigned automatically when they are created and are lost when they are terminated. This means that if there is a problem with the virtual machine which cannot be easily resolved and a new one is needed, we would lose out IP address.

This causes at least two problems. Firstly, the Continuous Deployment would have to be updated every time. Secondly, the routing for the domain name must be changed every time.

That is why we use reserved IP addresses. In this case the IP address is created separately from the VM, and then assigned to a VM. This makes it possible to change VM instances without the need to change the IP.

**Testing of the Marking Algorithm**

The marking system can be tested by comparing the input with the output. The marks can only be one of 0, 7, 15, 22, 29, 36, 42, 45, 48, 52, 55, 58, 62, 65, 68, 72, 75, 78, 83, 89 or 94. Therefore whatever the program outputs has to be rounded to one of those numbers.

The grades are calculated by assigning the median mark to each band then taking the average of all the criteria. Multiple grades will be produced: one for each category, and an overall grade. The overall grade should be taken as an average of each individual criterion, not the average of the rounded grades for each category.

Front End
---------

For the front end tests, we used the Selenium WebDriver framework to automate browswer actions. Due to continuous integration, we have to check what operating system the tests are running on and provide the appropriate Chrome driver. However, this has lead to a more robust implementation.

We have implemented very basic front end tests. The first test runs on the index page and asserts that the hyperlinks redirect to the appropriate pages. The second test runs on the login page. Several cases are run: the form is submitted with both correct and incorrect data, and the corresponding redirect is compared to the desired result.

Throughout the development process, we conferred with the client and her colleagues to make sure they were satisfied with the system. We have been very careful to take their preferences into consideration, and designed the system to their specifications.
