package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SetterTest {

    private CmdLineParser parser;

    private Setter testObject;

    @BeforeEach
    public void setup() {
        testObject = new Setter();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testSettingStringNoValues() {
        Setter bo = testObject;
        try {
            parser.parseArgument(new String[] {});
            assertTrue("default".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Call without parameters is valid!");
        }
    }

    @Test
    public void testSettingString() {
        Setter bo = testObject;
        try {
            parser.parseArgument(new String[] {"-str", "test"});
            assertTrue("TEST".equals(bo.str));
        } catch (CmdLineException e) {
            fail("Setting a string value should be possible");
        }
    }

    @Test
    public void testSettingUsage() {
        var expectedError = "\"-wrong-usage\" is not a valid option";
        var expectedUsage = " -str VAL : set a string";
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-wrong-usage"}));
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertTrue(e.getMessage().startsWith(expectedError));
        assertEquals(expectedUsage, usageLines[0]);
    }

    @Test
    public void testMissingParameter() {
        var expectedError = "Option \"-str\" takes an operand";
        var expectedUsage = " -str VAL : set a string";
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertEquals(expectedUsage, usageLines[0]);
        var e = assertThrows(CmdLineException.class, () -> parser.parseArgument("-str"));
        assertTrue(e.getMessage().startsWith(expectedError));
    }

    private static class Setter {
        public String str = "default";
        @Option(name = "-str", usage = "set a string")
        public void setStr(String str) {
            this.str = str.toUpperCase();
        }

    }
}
