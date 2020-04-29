package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LongUsageTest {

    private CmdLineParser parser;

    private LongUsage testObject;

    @BeforeEach
    public void setup() {
        testObject = new LongUsage();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testUsageMessageWithNewWayToSet() {
        // set Widescreen otherwise a line wrapping must occur
        parser = new CmdLineParser(testObject, ParserProperties.defaults().withUsageWidth(120));
        assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-wrong-usage"}));
        var usageLines = getUsageLines(parser);
        assertEquals(2, usageLines.length);
        var expectedLine1 = " -LongNamedStringOption USE_A_NICE_STRING : set a string";
        assertEquals(expectedLine1, usageLines[0]);
        var expectedLine2 =
                " -i N                                     : set an int (default: 0)";
        assertEquals(expectedLine2, usageLines[1]);
    }

    private static class LongUsage {

        @Option(name="-LongNamedStringOption",usage="set a string",metaVar="USE_A_NICE_STRING")
        private String s;
    
        @Option(name="-i",usage="set an int")
        private int i;
    
    }
}
