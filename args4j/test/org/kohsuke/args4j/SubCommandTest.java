package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.spi.SubCommand;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;

/**
 * @author Kohsuke Kawaguchi
 */
public class SubCommandTest {

    public static class Foo {
        @Argument(handler = SubCommandHandler.class)
        @SubCommands({@SubCommand(name = "cmd1", impl = Cmd1.class),
                @SubCommand(name = "cmd2", impl = Cmd2.class)})
        SubCmd value;

        @Option(name = "-r")
        String globalOption1;
    }

    public static abstract class SubCmd {
    }

    public static class Cmd1 extends SubCmd {
        @Option(name = "-r")
        String localOption;
    }
    public static class Cmd2 extends SubCmd {
    }

    private Foo testObject;

    private CmdLineParser parser;

    @BeforeEach
    public void setup() {
        testObject = new Foo();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testCmd1() throws Exception {
        parser.parseArgument("-r", "a", "cmd1", "-r", "b");
        assertEquals("a", testObject.globalOption1);
        assertEquals("b", ((Cmd1) testObject.value).localOption);
    }

    @Test
    public void testCmd2() throws Exception {
        parser.parseArgument("cmd2");
        assertTrue(testObject.value instanceof Cmd2);
    }
}

