package org.kohsuke.args4j.spi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.Argument;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Test for {@link StringArrayOptionHandler}.
 * 
 * This test is for a possible design problem in the old version of {@link ArrayFieldSetter} that I
 * discovered today. The fix could break existing code, I think it will need some discussion.
 * 
 * @author Stephan Fuhrmann
 */
public class StringArrayOptionHandlerTest {

    private static class TestBean {

        @Option(name = "-opt")
        private String stringArray[] = new String[] {"def1", "def2", "def3"};

        @Argument
        private String rest[];
    }

    @Test
    public void testParseWithDefault() throws Exception {

        TestBean bean = new TestBean();

        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "test3");

        assertEquals(Arrays.asList("def1", "def2", "def3"), Arrays.asList(bean.stringArray));
        assertEquals(Arrays.asList("test1", "test2", "test3"), Arrays.asList(bean.rest));
    }

    @Test
    public void testParseWithOneParam() throws Exception {

        TestBean bean = new TestBean();

        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "-opt", "test3");

        assertEquals(Arrays.asList("test3"), Arrays.asList(bean.stringArray));
        assertEquals(Arrays.asList("test1", "test2"), Arrays.asList(bean.rest));
    }

    @Test
    public void testParseWithTwoParams() throws Exception {

        TestBean bean = new TestBean();

        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "-opt", "test3", "-opt", "test4");

        assertEquals(Arrays.asList("test3", "test4"), Arrays.asList(bean.stringArray));
        assertEquals(Arrays.asList("test1", "test2"), Arrays.asList(bean.rest));
    }

    @Test
    public void testParseWithNoDefault() throws Exception {

        TestBean bean = new TestBean();
        bean.stringArray = null; // remove

        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("test1", "test2", "-opt", "test3", "-opt", "test4");

        assertEquals(Arrays.asList("test3", "test4"), Arrays.asList(bean.stringArray));
        assertEquals(Arrays.asList("test1", "test2"), Arrays.asList(bean.rest));
    }
}
