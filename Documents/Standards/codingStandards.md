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
        A source file consists of, <u>in order</u>:
        <ol>
            <li>License or copyright information, if present</li>
            <li>Package statement</li>
            <li>Import statements</li>
            <li>Exactly one top-level class</li>
        </ol>
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
        <li><b>One variable per declaration</b></li>
        Every variable declaration (field or local) declares only one variable: declarations such as <code>int a, b;</code> are not allowed.
        <u>EXCEPTION:</u> Multiple variable declarations are acceptable in the header of a <code>for</code> loop.
        <li><b>No C-style array declarations</b></li>
        The square brackets form a part of the type, not the variable.
        Example: <code>String[] args //GOOD<br />String args[] //BAD</code>
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
            <code>
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
            </code>
        </ul>
        <li><b>Column limit: 100 characters</b></li>
        <li><b>Whitespace</b></li>
        <ol>
            <li>Vertical Whitespace (blank line)</li>
            Blank line always appears between consecutive members or initializers of a class: fields, constructors, methods, nested classes, static initializers, and instance initializers
            <li>Horizontal whitespace</li>
            Beyond where required by the language or other style rules (and JavaDoc), use it when:
            <ol>
                <li>Separating any reserved word, such as <code>if</code>, <code>for</code> or <code>catch</code>, from an open parenthesis (<code>(</code>) that follows it on that line</li>
                <li>Separating any reserved word, such as <code>else</code> or <code>catch</code>, from a closing curly brace (<code>}</code>) that precedes it on that line</li>
                <li>Before any open curly brace (<code>{</code>), with two exceptions:</li>
                    <ul>
                        <li><code>@SomeAnnotation({a, b})</code></li>
                        <li><code>String[][] x = {{"foo"}};</code> (no space required between <code>{{</code>)</li>
                    </ul>
                <li>On both sides of any binary or ternary operator. This also applies to the following "operator-like" symbols:</li>
                    <ul>
                        <li>the ampersand in a conjunctive type bound: <code><T extends Foo & Bar></code></li>
                        <li>the pipe for a catch block that handles multiple exceptions: <code>catch (FooException | BarException e)</code></li>
                        <li>the colon (<code>:</code>) in an enhanced for statement: <code>for (Integer i : integerList)</code></li>
                        <li>the arrow in a lambda expression: <code>(String str) -> str.length()</code></li>
                    </ul>
                    but NOT:
                    <ul>
                        <li>the two colons (<code>::</code>) of a method reference, which is written like <code>Object::toString</code></li>
                        <li>the dot separator (<code>.</code>), which is written like <code>object.toString()</code></li>
                    </ul>
                <li>After <code>,:;</code> or the closing parenthesis (<code>)</code>) of a cast</li>
                <li>On both sides of the double slash (<code>//</code>) that begins an end-of-line comment</li>
                <li>Between the type and variable of a declaration: <code>List<String\> list</code></li>
            </ol>
        </ol>
    </ol>
    <h2><li>Comments</li></h2>
    <ol>
        <li>All comment techniques are acceptable (<code>//, /\* \*/, etc.</code>)</li>
        <li>Do NOT insert line-comment (<code>//</code>) in the line after code</li>
        <li>Comment has to give more information than you can read from code.
        <h3>BAD example</h3> <code>//The spectators<br /> List<Spectator\> spectators;</code></li>
        <li>If a method or variable (especially private) needs a comment, rewrite the code. Code should be overall readable without comments, because only code tells the whole truth about what the program does.</li>
    </ol>
    <h2>Naming</h2>
    <ol>
        <li><u>Package names</u></li>
        Package names are all lowercase, with consecutive words simply concatenated together (no underscores). For example, <code>com.example.deepspace</code>
        <li><u>Class names</u></li>
        <ul>
            <li>Class names are written in <u>UpperCamelCase</u></li>
            <li>Class names are typically nouns or noun phrases. For example, <code>Character</code> or <code>ImmutableList</code></li>
            <li>Interface names may also be nouns or noun phrases (<code>List</code>), but may sometimes be adjectives or adjective phrases instead (<code>Readable</code>)</li>
            <li>Test classes are named starting with the name of the class they are testing, and ending with <code>Test</code>. For example, <code>HashTest</code> or <code>HashIntegrationTest</code></li>
        </ul>
        <li><u>Method names</u></li>
    </ol>
</ol>
