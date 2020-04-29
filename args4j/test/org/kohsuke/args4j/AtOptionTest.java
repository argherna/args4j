package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the AT sign that reads options from an external file.
 * 
 * @author Stephan Fuhrmann
 */
public class AtOptionTest {

    private AtOption atOption;

    private CmdLineParser parser;

    @BeforeEach 
    public void setup() {
        atOption = new AtOption();
        parser = new CmdLineParser(atOption);
    }

    @Test
    public void testSimpleAt() throws IOException, CmdLineException {

        var tmp = File.createTempFile("atoption", null);
        var printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();

        var args = new String[] {"@" + tmp.getAbsolutePath()};
        parser.parseArgument(args);

        assertEquals("foo", atOption.str);
        assertNull(atOption.noUsage);
        assertNull(atOption.arguments);

        tmp.delete();
    }

    @Test
    public void testAtAfterOpts() throws IOException, CmdLineException {

        var tmp = File.createTempFile("atoption", null);
        var printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();

        var args = new String[] {"-noUsage", "lala", "@" + tmp.getAbsolutePath()};
        parser.parseArgument(args);

        assertEquals("foo", atOption.str);
        assertEquals("lala", atOption.noUsage);
        assertNull(atOption.arguments);

        tmp.delete();
    }

    @Test
    public void testAtBeforeOpts() throws IOException, CmdLineException {

        var tmp = File.createTempFile("atoption", null);
        var printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();

        var args = new String[] {"@" + tmp.getAbsolutePath(), "-noUsage", "lala"};
        parser.parseArgument(args);

        assertEquals("foo", atOption.str);
        assertEquals("lala", atOption.noUsage);
        assertNull(atOption.arguments);

        tmp.delete();
    }

    @Test
    public void testAtOptsWithBeingDisabled() throws IOException, CmdLineException {

        parser.getProperties().withAtSyntax(false);

        var tmp = File.createTempFile("atoption", null);
        var printWriter = new PrintWriter(tmp);
        printWriter.println("-string\nfoo");
        printWriter.close();

        // this time the @-option gets not interpreted because
        // it's disabled
        var args = new String[] {"-noUsage", "foo", "@" + tmp.getAbsolutePath()};
        parser.parseArgument(args);

        assertEquals("default", atOption.str);
        assertEquals("foo", atOption.noUsage);
        assertEquals(Arrays.asList(new String[] {"@" + tmp.getAbsolutePath()}),
                Arrays.asList(atOption.arguments));

        tmp.delete();
    }

    private static class AtOption {
        @Option(name = "-string", usage = "set a string")
        public String str = "default";

        @Option(name = "-noUsage")
        public String noUsage;

        @Argument
        public String arguments[];
    }
}
