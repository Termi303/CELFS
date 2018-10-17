<h1>Coding standards - CELFS marking system</h1>
<ol>
<h2><li>Breaking rules</li></h2>
If you think that breaking rules listed below improves code readability, break them. However, you should put a comment in code & report it to the team (as it probably means that we need to update this document).

<h2><li>Source file basics</li></h2>
    <ol>
        <li><b>File name</b></li>
        The source file name consists of the case-sensitive name of the top-level class it contains (of which there is exactly one), plus the <code>.java</code> extension.

        <li><b>File encoding</b></li>
        Source files are encoded in UTF-8.

        <li><b>Whitespace characters</b></li>
        Aside from the line terminator sequence, the ASCII horizontal space character <code>0x20</code> is the only whitespace character that appears anywhere in a source file.

        <li><b>Tab character</b></li>
        Tab should be equal to 4 spaces

        <li><b>Non-ASCII characters</b></li>
        For the remaining non-ASCII characters, either the actual Unicode character (e.g. ∞) or the equivalent Unicode escape (e.g. `\u221e`) is used - use whatever is easier to read/understand

        
    </ol>
</ol>
