package org.kohsuke.args4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.spi.StringArrayOptionHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

public class MultivaluedTest {

    private CmdLineParser parser;

    // The JavaBean part of this class as test object.

    @Option(name = "-list")
    List<String> list;

    @Option(name = "-string")
    String string;

    @Option(name = "-array")
    String[] array;

    @Option(name = "-multivalued-array", handler = StringArrayOptionHandler.class)
    String[] multiValuedArray;

    // The JUnit part of this class as tester.

    @BeforeEach
    public void setup() {
        parser = new CmdLineParser(this);
    }

    @Test
    public void testOnList() {
        // The 'command line invocation'.
        try {
            parser.parseArgument("-list", "one", "-list", "two", "-list", "three");
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        // Check the results.
        assertEquals(3, list.size());
        assertTrue(list.contains("one"));
        assertTrue(list.contains("two"));
        assertTrue(list.contains("three"));
    }

    /**
     * Specifying an option multiple times can get no-op, such as in the case when the field can
     * only retain one value.
     */
    @Test
    public void testOnString() {
        try {
            parser.parseArgument("-string", "one", "-string", "two", "-string", "three");
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertEquals("three", string);
    }

    @Test
    public void testOnArray() {
        try {
            parser.parseArgument("-array", "one", "-array", "two", "-array", "three");
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertEquals(3, array.length);
        assertEquals("one", array[0]);
        assertEquals("two", array[1]);
        assertEquals("three", array[2]);
    }

    @Test
    public void testOnMultiValuedArray() {
        try {
            parser.parseArgument("-multivalued-array", "one", "two", "-multivalued-array",
                    "three");
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertEquals(3, multiValuedArray.length);
        assertEquals("one", multiValuedArray[0]);
        assertEquals("two", multiValuedArray[1]);
        assertEquals("three", multiValuedArray[2]);
    }
}
