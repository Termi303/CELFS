Development Testing
===================

Continuous Integration
----------------------

Continuous Integration is done through CircleCI.
Every time there is a commit on any branch, automatic testing cycle is invoked. It pulls those changes from the repository, builds the project and runs all the tests. This makes it easy to keep track of whether our code is correct.

Continuous Deployment
---------------------

Continuous Deployment is achieved by collaboration of CircleCI and a virtual machine, which is being run on the Oracle Cloud.
Every commit on the master branch triggers an extended execution cycle, which has the following steps:
1. The code is automatically pulled, built and tested (as on every other branch).
1. An ssh connection to the Oracle server is established.
1. The script "script.sh" on the virtual machine is run. It terminates the process running on port 443. It also takes into account that there might not be any process running on this port, for example if the virtual machine was recently restarted. That's why the function to kill the process is run using "scriptfunction || true". This makes sure that even if the function fails because there is nothing running on this port, the CircleCI cycle would not stop.
1. The project folder on the Oracle VM is deleted.
1. The whole project folder from CircleCI is sent to the Oracle server, in the same place where the deleted folder used to be.
1. The project is then executed, redirecting all the output to "/dev/null", so that the cycle can safely proceed.
1. Finally, the IP tables are flushed.

**Standalone IP Address**

By default the IP addresses for Oracle Cloud Virtual Machines are assigned automatically when they are created and are lost when they are terminated. This means that if there is a problem with the virtual machine which cannot be easily resolved and a new one is needed, we would lose out IP address.

This causes at least two problems. Firstly, the Continuous Deployment would have to be updated every time. Secondly, the routing for the domain name must also be changed.

That is why we use reserved IP addresses. In this case the IP address is created separately, and then assigned to a VM. This makes it possible to change VM instances without the need to change the IP.

Back End
--------

**Database**

The functions for communicating with the database are unit tested. We're storing the criteria for each piece of work in tables, so we
know what each query should return and can assert whether it actually does what it's supposed to.

**Testing of the Marking Algorithm**

The marking system can be tested by comparing the input with the output. The marks can only be one of 0, 7, 15, 22, 29, 36, 42, 45, 48, 52, 55, 58, 62, 65, 68, 72, 75, 78, 83, 89 or 94. Therefore whatever the program outputs has to be rounded to one of those numbers.

The grades are calculated by assigning the median mark to each band then taking the average of all the criteria. Multiple grades will be produced: one for each category, and an overall grade. The overall grade should be taken as an average of each individual criterion, not the average of the rounded grades for each category.

Front End
---------

For the front end tests, we used the Selenium WebDriver framework to automate browser actions. We encountered some difficulty in getting the tests to work both locally and with CircleCI, due to different operating systems and discrepancies between the versions of the Chrome driver. To get round this, we added in checks to decide which version of the driver to run, and this has led to a more robust implementation.

We have implemented basic front end tests. The first test runs on the index page and asserts that the hyperlinks redirect to the appropriate pages. The second test runs on the login page. Several cases are run: the form is submitted with both correct and incorrect data, and the corresponding redirect is compared to the desired result.

Stress Testing
---------

We also performed stress testing using Jmeter. We found that our website can handle more than 400 users per second. According to our client, there are only up to 100 potential users, so we are confident that our system satisfies the client's needs.
