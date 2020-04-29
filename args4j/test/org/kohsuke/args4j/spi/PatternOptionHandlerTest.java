package org.kohsuke.args4j.spi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Locale;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Simple test for the {@link PatternOptionHandler}.
 * 
 * @author Stephan Fuhrmann
 */
public class PatternOptionHandlerTest {

    private class TestBean {
        @Option(name = "-pattern")
        private Pattern pattern;
    }

    @Test
    public void testParseSuccess() throws Exception {

        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        parser.parseArgument("-pattern", ".*");

        assertEquals(Pattern.compile(".*").toString(), bean.pattern.toString());
    }


    @Test
    public void testParseFail() throws Exception {

        Locale old = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        TestBean bean = new TestBean();
        CmdLineParser parser = new CmdLineParser(bean);
        try {
            var e = assertThrows(CmdLineException.class,
                    () -> parser.parseArgument("-pattern", "*"));
            assertEquals("\"-pattern\" must be a regular expression", e.getMessage());
        } finally {
            Locale.setDefault(old);
        }
    }

}
