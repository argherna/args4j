package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.spi.ExplicitBooleanOptionHandler;

public class ExplicitBooleanArgumentTest {

    private CmdLineParser parser;

    private BooleanArgumentHolder bah;

    public static class BooleanArgumentHolder {
        @Argument(index = 0, handler = ExplicitBooleanOptionHandler.class,
                usage = "Set a boolean value")
        boolean booleanArg;
    }

    @BeforeEach
    public void setup() {
        bah = new BooleanArgumentHolder();
        parser = new CmdLineParser(bah);
    }

    @Test
    public void testSetBooleanTrue() {
        try {
            parser.parseArgument(new String[] {"true"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(bah.booleanArg);
    }

    @Test
    public void testSetBooleanOn() {
        try {
            parser.parseArgument(new String[] {"on"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(bah.booleanArg);
    }

    @Test
    public void testSetBooleanYes() {
        try {
            parser.parseArgument(new String[] {"yes"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(bah.booleanArg);
    }

    @Test
    public void testSetBooleanTrueCaseInsensitive() {
        try {
            parser.parseArgument(new String[] {"tRuE"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(bah.booleanArg);
    }

    @Test
    public void testSetBoolean1() {
        try {
            parser.parseArgument(new String[] {"1"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(bah.booleanArg);
    }

    @Test
    public void testSetBooleanFalse() {
        try {
            parser.parseArgument(new String[] {"false"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(bah.booleanArg);
    }

    @Test
    public void testSetBooleanOff() {
        try {
            parser.parseArgument(new String[] {"off"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(bah.booleanArg);
    }

    @Test
    public void testSetBooleanNo() {
        try {
            parser.parseArgument(new String[] {"no"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(bah.booleanArg);
    }

    @Test
    public void testSetBoolean0() {
        try {
            parser.parseArgument(new String[] {"0"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(bah.booleanArg);
    }

    @Test
    public void testSetBooleanFalseCaseInsensitive() {
        try {
            parser.parseArgument(new String[] {"FaLsE"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(bah.booleanArg);
    }

    @Test
    public void testSetBooleanNoValueIsFalse() {
        try {
            parser.parseArgument(new String[0]);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(bah.booleanArg);
    }

    @Test
    public void testIllegalBoolean() {
        assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"ILLEGAL_BOOLEAN"}));
    }

    @Test
    public void testUsage() {
        assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-wrong"}));
        var usageLines = getUsageLines(parser);
        var containsVal = false;
		for (var usageLine : usageLines) {
			containsVal = usageLine.contains("VAL");
			if (containsVal) {
				break;
			}
		}
        assertTrue(containsVal);
    }

}
