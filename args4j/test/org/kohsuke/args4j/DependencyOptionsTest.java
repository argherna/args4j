package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Nicolas Geraud
 */
public class DependencyOptionsTest {

    private CmdLineParser parser;

    private DependencyOptions depOpts;

    @BeforeEach
    public void setup() {
        depOpts = new DependencyOptions();
        parser = new CmdLineParser(depOpts);
    }

    @Test
    public void testEverybodyIsHere() {
        try {
            parser.parseArgument(new String[] {"-a", "4", "-b", "3", "-c", "2", "-d", "1"});
            assertEquals(4, depOpts.w);
            assertEquals(3, depOpts.x);
            assertEquals(2, depOpts.y);
            assertEquals(1, depOpts.z);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testEveryoneExceptRequires() {
        try {
            parser.parseArgument(new String[] {"-a", "4", "-b", "3"});
            assertEquals(4, depOpts.w);
            assertEquals(3, depOpts.x);
            assertEquals(0, depOpts.y);
            assertEquals(0, depOpts.z);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSingleRequires() {
        try {
            parser.parseArgument(new String[] {"-a", "4", "-c", "2"});
            assertEquals(4, depOpts.w);
            assertEquals(0, depOpts.x);
            assertEquals(2, depOpts.y);
            assertEquals(0, depOpts.z);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSingleRequiresFail() {
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-c", "2"}));
        assertTrue(e.getMessage().contains("requires the option(s) [--alpha]"));
    }

    @Test
    public void testMultipleRequiresFail1() {
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"--bravo", "3", "-d", "1"}));
        assertTrue(e.getMessage().contains("requires the option(s) [-b, -c]"));
    }

    @Test
    public void testMultipleRequiresFail2() {
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-a", "4", "-c", "2", "-d", "1"}));
        assertTrue(e.getMessage().contains("requires the option(s) [-b, -c]"));
    }

    @Test
    public void testForbidFail() throws Exception {
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-a", "5", "-b", "1", "-h", "5"}));
        assertTrue(e.getMessage().contains("cannot be used with the option(s) [-a, -b]"));
    }

    @Test
    public void testRecursive() {
        try {
            parser.parseArgument(new String[] {"-z", "4", "-y", "3"});
            assertEquals(4, depOpts.a);
            assertEquals(3, depOpts.b);
        } catch (CmdLineException e) {
            fail(e.getMessage());
        }
    }

    private static class DependencyOptions {
        @Option(name = "-z", depends = {"-y"})
        int a;

        @Option(name = "-y", depends = {"-z"})
        int b;

        @Option(name = "-a", aliases = "--alpha")
        int w;

        @Option(name = "-b", aliases = "--bravo")
        int x;

        @Option(name = "-c", depends = {"--alpha"})
        int y;

        @Option(name = "-d", depends = {"-b", "-c"})
        int z;

        @Option(name = "-h", forbids = {"-a", "-b"})
        int o;
    }
}
