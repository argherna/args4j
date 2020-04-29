package org.kohsuke.args4j.spi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: kmahoney
 * Date: 6/7/13
 * Time: 6:09 AM
 */

public class PathOptionHandlerTest {
    static final String TEST_1 = "/path/test";
    static final String TEST_2 = "bad/path/\0";

    private PathOptionHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new PathOptionHandler(null, null, null);
    }

    @Test
    public void testParseSuccess() throws Exception {
        Path expectedPath = Paths.get(TEST_1);
        Path path = handler.parse(TEST_1);

        assertEquals(expectedPath, path);
    }

    @Test
    public void testParseFailure() throws Exception {
        assertThrows(CmdLineException.class, () -> handler.parse(TEST_2));
    }

    @Test
    public void testNullParseFailure() throws Exception {
        assertThrows(CmdLineException.class, () -> handler.parse(null));
    }
}
