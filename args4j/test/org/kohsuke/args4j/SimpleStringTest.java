package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class SimpleStringTest {

    private CmdLineParser parser;

    private SimpleString testObject;

    @BeforeEach
    public void setup() {
        testObject = new SimpleString();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testSettingStringNoValues() {
        SimpleString bo = testObject;
        try {
            parser.parseArgument(new String[] {});
            assertTrue("default".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Call without parameters is valid!");
        }
    }

    @Test
    public void testSettingString() {
        SimpleString bo = testObject;
        try {
            parser.parseArgument(new String[] {"-str", "test"});
            assertTrue("test".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Setting a string value should be possible");
        }
    }

    @Test
    public void testSettingUsage() {
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        var expectedError = "\"-wrong-usage\" is not a valid option";
        var expectedUsage = " -str VAL : set a string (default: default)";
        var e = assertThrows(CmdLineException.class, () -> parser.parseArgument("-wrong-usage"));
        assertTrue(e.getMessage().startsWith(expectedError));
        assertEquals(expectedUsage, usageLines[0]);
    }

    @Test
    public void testMissingParameter() {
        var expectedError = "Option \"-str\" takes an operand";
        var expectedUsage = " -str VAL : set a string (default: default)";
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertEquals(expectedUsage, usageLines[0]);
        var e = assertThrows(CmdLineException.class, () -> parser.parseArgument("-str"));
        assertTrue(e.getMessage().startsWith(expectedError));
    }

    /*
     * Bug 5: Option without "usage" are hidden. TODO: it seems that this is intended:
     * http://weblogs.java.net/blog/kohsuke/archive/2005/05/parsing_command.html An @option without
     * "usage" should not be displayed? If there is no usage information, the
     * CmdLineParser.printOption() methods do explitely nothing.
     */
    @Test
    @Disabled
    public void _testUsage() {
        assertThrows(CmdLineException.class, () -> parser.parseArgument("-wrong"));
        var usageLines = getUsageLines(parser);
        var contains = false;
        for (var usageLine : usageLines) {
            contains = usageLine.contains("-nu");
            if (contains) {
                break;
            }
        }
        assertTrue(contains);
    }

    private static class SimpleString {
        @Option(name = "-str", usage = "set a string")
        public String str = "default";

        @Option(name = "-nu")
        public String noUsage;
    }
}
