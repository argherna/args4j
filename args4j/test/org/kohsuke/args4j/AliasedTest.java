package org.kohsuke.args4j;

import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AliasedTest {

    private CmdLineParser parser;

    private Aliased aliased;

    @BeforeEach
    public void setup() {
        aliased = new Aliased();
        parser = new CmdLineParser(aliased);
    }

    @Test
    public void testMissingParameter() {
        var e = assertThrows(CmdLineException.class, () -> parser.parseArgument("-str"));
        var expectedError = "Option \"-str (--long-str)\" takes an operand";
        assertTrue(e.getMessage().startsWith(expectedError));

        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length, "Wrong amount of lines in usage message");
        var expectedUsage = " -str (--long-str) METAVAR : set a string";
        assertEquals(expectedUsage, usageLines[0]);
    }

    @Test
    public void testAlias() throws Exception {
        parser.parseArgument("--long-str", "something");
        assertEquals("something", aliased.str);
    }

    private static class Aliased {
        @Option(name = "-str", aliases = {"--long-str"}, usage = "set a string",
                metaVar = "METAVAR")
        public String str;
    }
}
