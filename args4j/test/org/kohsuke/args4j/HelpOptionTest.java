package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit test for checking whether {@link Option#help() works}.
 * 
 * @author Stephan Fuhrmann
 */
public class HelpOptionTest {

    private CmdLineParser parser;

    private HelpOption testObject;

    @BeforeEach
    public void setup() {
        testObject = new HelpOption();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testWithRequiredOptionMissing() {
        var expectedError = "Option \"-req\" is required";
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-opt", "operand"}));
        var actualError = e.getMessage();
        assertTrue(actualError.startsWith(expectedError));
        var usageLines = getUsageLines(parser);
        assertEquals(2, usageLines.length);
    }

    @Test
    public void testWithRequiredOptionExisting() {
        try {
            parser.parseArgument(new String[] {"-req", "operand"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }

        assertFalse(testObject.help);
        assertNull(testObject.optional1);
        assertEquals("operand", testObject.req1);
    }

    @Test
    public void testWithHelpExistingButRequiredOptionMissing() {
        try {
            parser.parseArgument(new String[] {"-help"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }

        assertTrue(testObject.help);
        assertNull(testObject.optional1);
        assertNull(testObject.req1);
    }

    private static class HelpOption {
        @Option(name = "-req", usage = "required option", required = true)
        public String req1;

        @Option(name = "-opt", usage = "optional option")
        public String optional1;

        @Option(name = "-help", help = true)
        public boolean help;
    }

}
