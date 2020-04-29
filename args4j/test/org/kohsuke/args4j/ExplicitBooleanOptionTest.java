package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.spi.ExplicitBooleanOptionHandler;

public class ExplicitBooleanOptionTest {

    private CmdLineParser parser;

    private BooleanOptionHolder boh;

    public static class BooleanOptionHolder {
        @Option(name = "-booleanOpt", handler = ExplicitBooleanOptionHandler.class,
                usage = "Set a boolean value")
        boolean booleanOpt;

        @Option(name = "-nextArg")
        boolean nextArg;
    }

    @BeforeEach
    public void setup() {
        boh = new BooleanOptionHolder();
        parser = new CmdLineParser(boh);
    }

    @Test
    public void testSetBooleanTrue() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "true"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanOn() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "on"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanYes() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "yes"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanTrueCaseInsensitive() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "tRuE"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(boh.booleanOpt);
    }

    @Test
    public void testSetBoolean1() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "1"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanFalse() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "false"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanOff() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "off"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanNo() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "no"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(boh.booleanOpt);
    }

    @Test
    public void testSetBoolean0() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "0"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanFalseCaseInsensitive() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "FaLsE"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertFalse(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanLastArgIsTrue() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(boh.booleanOpt);
    }

    @Test
    public void testSetBooleanWithoutParamIsTrue() {
        try {
            parser.parseArgument(new String[] {"-booleanOpt", "-nextArg"});
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
        assertTrue(boh.booleanOpt);
    }

    @Test
    public void testIllegalBoolean() {
        assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-booleanOpt", "ILLEGAL_BOOLEAN"}));
    }

}
