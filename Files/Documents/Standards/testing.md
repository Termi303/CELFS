This is a short introduction to how to do some testing in Java.

Where should the test be located?
Create test in: 'src/test/java/PACKAGE_PATH'
In Netbeans you can just right-click on "Test packages".

We can divide tests into few categories:
<ul>
<li> Unit tests - they test functionality inside one module (i.e. class) - this could be, for example, test one of the methods </li>
<li> Integration tests - they aim to test the behavior of a component or the integration between a set of components </li>
<li> Performance tests - they test system's performance given some large data </li>
</ul>

For now, we don't have to care about performance tests.
Always develop unit tests first before integration tests.

How to do it in Spring Boot?
Create a java file in a package, whose name (if unit test) is the same as the tested class' package name.

Now, I will show some code and go line by line through it.
This is for unit tests, when you gain the skill of developing unit tests, integration ones will be easy to learn.

<pre><code>
//CalculateMarksTest.java
//created in CELFS/src/test/java/uk/ac/bris/celfs/website

package uk.ac.bris.celfs.website;

import org.junit.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class CalculateMarksTest {
    @Test
    public void testApplyMarkForExactMark() {
        assertEquals(0, CalculateMarks.applyMark(0));
        assertEquals(36, CalculateMarks.applyMark(36));
        assertEquals(55, CalculateMarks.applyMark(55));
    }
}</code>
</pre>

<ol>
<li>Package name - same as tested class' package name</li>
<li> Name of the class - refer to coding standards, but basically it's [Name of class you're testing]+"Test"</li>
<li> Path - src/test/java/PACKAGE_PATH</li>
<li> Import statements:
    <ul>
    <li><code>import org.junit.*</code> - this enables us to use JUnit flags (like @Test), but also other ones - <a href="http://www.vogella.com/tutorials/JUnit/article.html#usingjunit_annotations">Further details</a></li>
    <li><pre><code>import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;</code></pre>
    I will explain it in a minute.</li>
    <li><code>import static org.junit.Assert.assertEquals;</code> - this is to use assertEquals function in test method</li>
    </ul>
</li>
<li><code>@RunWith(SpringRunner.class)</code> - Spring needs to know that you want to run this test class (two imports are used to perform this)</li>
<li>Test method
    <ol>
        <li><code>@Test</code> - this gives signal to JUnit that this is a test method</li>
        <li>In this method I don't have to initialize anything because I test the static method, but remember (IMPORTANT) - you have to initialize all classes that you're going to use</li>
        <li><code>assertEquals</code> - there are many assert functions in JUnit, but this one is most frequently used. First argument is expected value, second is value returned by tested piece of code. If they equal, nothing happens. Otherwise, test reports failure and stops execution.</li>
        <li>If JUnit reaches the end of test, it is marked as successful :)</li>
    </ol>
</li>
</ol>

To run tests, execute <code>mvn clean test</code>. This command will give you some details about execution (especially when something fails).

One more thing - test coverage.
Test coverage is the percentage of code which is tested by unit tests. Basically, it's how much code JUnit will "visit" while executing tests. Aim for the highest possible percentage (minimum 75%). This often means writing multiple tests for even one method. I will try to configure test coverage analysis tool in a couple of days.

If you need more help with this, hmu.
Igor
