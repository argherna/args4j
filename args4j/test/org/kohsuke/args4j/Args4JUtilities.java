package org.kohsuke.args4j;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Locale;

public final class Args4JUtilities {

    private Args4JUtilities() {}

    public static String[] getUsageLines(CmdLineParser parser) {
        Locale oldDefault = Locale.getDefault();
        Locale.setDefault(Locale.ENGLISH);
        Stream2String s2s = new Stream2String();
        parser.printUsage(s2s);
        Locale.setDefault(oldDefault);
        return s2s.getString().split(System.getProperty("line.separator"));
    }

    /**
     * Utility class for capturing an OutputStream into a String.
     * @author Jan Materne
     */
    private static class Stream2String extends OutputStream {
        private StringBuffer sb = new StringBuffer();

        @Override
        public void write(int b) throws IOException {
            sb.append((char)b);
        }

        public String getString() {
            return sb.toString();
        }
    }
}