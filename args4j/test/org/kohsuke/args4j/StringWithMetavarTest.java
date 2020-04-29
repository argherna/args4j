package org.kohsuke.args4j;

import java.io.StringWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

public class StringWithMetavarTest {

    private CmdLineParser parser;

    private StringWithMetavar testObject;

    @BeforeEach
    public void getTestObject() {
        testObject = new StringWithMetavar();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testSettingUsage() {
        var e = assertThrows(CmdLineException.class, () -> parser.parseArgument("-wrong-usage"));
        assertTrue(e.getMessage().startsWith("\"-wrong-usage\" is not a valid option"));
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertEquals(" -str METAVAR : set a string", usageLines[0]);
        assertEquals(" [-str METAVAR]", getSingleLineUsage());
    }

    @Test
    public void testMissingParameter() {
        var e = assertThrows(CmdLineException.class, () -> parser.parseArgument("-str"));
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertTrue(e.getMessage().startsWith("Option \"-str\" takes an operand"));
        assertEquals(" -str METAVAR : set a string", usageLines[0]);
    }

    private String getSingleLineUsage() {
        var buffer = new StringWriter();
        parser.printSingleLineUsage(buffer, null);
        buffer.flush();
        return buffer.toString();
    }

    @Test
    public void testEqualsSeparator() {
        parser.getProperties().withOptionValueDelimiter("=");
        assertThrows(CmdLineException.class, () -> parser.parseArgument("-wrong-usage"));
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertEquals(" -str=METAVAR : set a string", usageLines[0]);
        assertEquals(" [-str=METAVAR]", getSingleLineUsage());
    }

    @Test
    public void testExplicitNoEqualsSeparator() {
        parser.getProperties().withOptionValueDelimiter(" ");
        assertThrows(CmdLineException.class, () -> parser.parseArgument("-wrong-usage"));
        var usageLines = getUsageLines(parser);
        assertEquals(1, usageLines.length);
        assertEquals(" -str METAVAR : set a string", usageLines[0]);
        assertEquals(" [-str METAVAR]", getSingleLineUsage());
    }

    private static class StringWithMetavar {
        @Option(name = "-str", usage = "set a string", metaVar = "METAVAR")
        public String str;
    }
}
