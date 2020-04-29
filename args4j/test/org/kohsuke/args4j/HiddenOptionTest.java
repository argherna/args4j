package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HiddenOptionTest {

    private CmdLineParser parser;

    private HiddenOption testObject;

    @BeforeEach
    public void setup() {
        testObject = new HiddenOption();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testSettingUsage() {
        var expectedError = "\"-wrong-usage\" is not a valid option";
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-wrong-usage"}));
        assertTrue(e.getMessage().startsWith(expectedError));
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertEquals("", usageLines[0]);
    }

    private static class HiddenOption {
        @Option(name = "-str", usage = "set a string", metaVar = "METAVAR", hidden = true)
        public String str;
    }
}
