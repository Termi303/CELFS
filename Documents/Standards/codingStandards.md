<h1>Coding standards - CELFS marking system</h1>
<p>Based on <a href="http://google.github.io/styleguide/javaguide.html">Google standards</a></p>
<ol>
<h2><li>Breaking rules</li></h2>
If you think that breaking rules listed below improves code readability, break them. However, you should put a comment in code & report it to the team (as it probably means that we need to update this document).

<h2><li>Source file basics</li></h2>
    <ol>
        <li><b>File name</b></li>
        The source file name consists of the case-sensitive name of the top-level class it contains (of which there is exactly one), plus the <code>.java</code> extension

        <li><b>File encoding</b></li>
        Source files are encoded in UTF-8

        <li><b>Whitespace characters</b></li>
        Aside from the line terminator sequence, the ASCII horizontal space character <code>0x20</code> is the only whitespace character that appears anywhere in a source file

        <li><b>Tab character</b></li>
        Tab should be equal to 4 spaces

        <li><b>Non-ASCII characters</b></li>
        For the remaining non-ASCII characters, either the actual Unicode character (e.g. ∞) or the equivalent Unicode escape (e.g. `\u221e`) is used - use whatever is easier to read/understand (examples: <a href="http://google.github.io/styleguide/javaguide.html#s2.3.3-non-ascii-characters">Google standards</a>)
    </ol>
<h2><li>Source file structure</li></h2>
    <ol>
        <li><b>Order</b></li>
        A source file consists of, <u>in order</u>:<br>
            1. License or copyright information, if present<br>
            2. Package statement<br />
            3. Import statements<br />
            4. Exactly one top-level class<br>

        Exactly one blank line separates each section that is present

        <li><b>Package & import statements</b></li>
        Package & import statement are not line-wrapped.

        <li><b>Class declaration</b></li>
        <ul>
            <li>Each top-level class resides in a source file of its own</li>
            <li>Never split constructors (if there are multiple), or multiple methods with the same name, these appear sequentially, with no other code in between (not even private members)</li>
        </ul>
    </ol>
<h2><li>Formatting</li></h2>
    <ol>
        <li><b>One statement per line</b></li>
        Each statement is followed by a line break.
        <code>INCORRECT: a++;b++;</code>

        <li><b>Braces</b></li>
        <ul>
            <li>Braces are used where optional</li>
            Braces are used with <code>if</code>, <code>else</code>, <code>for</code>, <code>do</code> and <code>while</code> statements, even when the body is empty or contains only a single statement.
            <li>No line break before the opening brace</li>
            <li>Line break after the opening brace</li>
            <li>Line break before the closing brace</li>
            <li>Line break after the closing brace, only if that brace terminates a statement or terminates the body of a method, constructor, or named class. For example, there is no line break after the brace if it is followed by <code>else</code> or a comma.</li>
            <br>
            Examples:<br>
            ```
                public class MyClass() {
                    public void method() {
                        if (condition()) {
                            try {
                                something();
                            } catch (Exception e) {
                                recover();
                            }
                        } else if (otherCondition()) {
                            somethingElse();
                        } else {
                            lastThing();
                        }
                    }
                }
            ```
        </ul>
    </ol>
</ol>
