package org.kohsuke.args4j.spi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.AnnotatedElement;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

/**
 * Created with IntelliJ IDEA. User: kmahoney Date: 6/7/13 Time: 6:09 AM
 */

public class MultiPathOptionHandlerTest {
    private static final String PSPRTR = System.getProperty("path.separator");
    private static final String PATH_1 = "/tmp/path/test/1";
    private static final String PATH_2 = "/tmp/path/test/2.log";
    private static final String PATH_3 = "3.tst";

    private static final String TEST_1 = PATH_1 + PSPRTR + PATH_2;
    private static final String TEST_2 = PATH_2 + PSPRTR + PATH_3;

    @SuppressWarnings("unused")
    private MultiPathOptionHandler handler;

    @BeforeEach
    public void setUp() {
        handler = new MultiPathOptionHandler(null, null, null);
    }

    @Test
    public void testParseSuccess() throws Exception {
        testParse(PathOptionHandlerTest.TEST_1, PathOptionHandlerTest.TEST_1);
    }

    @Test
    public void testSeperatedPathSuccess() throws Exception {
        testParse(TEST_1, PATH_1, PATH_2);
    }

    @Test
    public void testDualParseSeperatedPathSuccess() throws Exception {
        CollectorSetter collectorSetter = new CollectorSetter();
        MultiPathOptionHandler handler = new MultiPathOptionHandler(null, null, collectorSetter);
        handler.parseArguments(makeSingleParam(TEST_1));
        handler.parseArguments(makeSingleParam(TEST_2));
        assertCollectedMatch(collectorSetter, PATH_1, PATH_2, PATH_2, PATH_3);
    }

    @Test
    public void testParseFailure() throws Exception {

        @SuppressWarnings("unused")
        var e = assertThrows(CmdLineException.class,
                () -> testParse(PathOptionHandlerTest.TEST_2, PathOptionHandlerTest.TEST_2));
    }

    private static void testParse(final String arg, String... paths) throws CmdLineException {
        CollectorSetter collectorSetter = new CollectorSetter();
        MultiPathOptionHandler handler = new MultiPathOptionHandler(null, null, collectorSetter);
        handler.parseArguments(makeSingleParam(arg));
        assertCollectedMatch(collectorSetter, paths);
    }

    private static void assertCollectedMatch(CollectorSetter collectorSetter, String... paths) {
        assertEquals(paths.length, collectorSetter.getCollection().size());
        for (int i = 0; i < paths.length; i++) {
            assertEquals(collectorSetter.getCollection().get(i), Paths.get(paths[i]));
        }
    }

    private static Parameters makeSingleParam(final String path) {
        return new Parameters() {
            public String getParameter(int idx) throws CmdLineException {
                if (idx == 0)
                    return path;
                throw new ArrayIndexOutOfBoundsException(idx);
            }

            public int size() {
                return 1;
            }
        };
    }

    static class CollectorSetter implements Setter<Path> {

        private List<Path> collector = new LinkedList<Path>();

        public void addValue(Path value) throws CmdLineException {
            collector.add(value);
        }

        public Class<Path> getType() {
            return Path.class;
        }

        public boolean isMultiValued() {
            return true;
        }

        public FieldSetter asFieldSetter() {
            throw new UnsupportedOperationException();
        }

        public AnnotatedElement asAnnotatedElement() {
            throw new UnsupportedOperationException();
        }

        List<Path> getCollection() {
            return collector;
        }
    }
}
