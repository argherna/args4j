package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// a 'custom' exception
import javax.management.InvalidAttributeValueException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CustomExceptionTest {

    private static final String ERR_MSG_X = "this is a usual CLI exception";
    private static final String ERR_MSG_Y = "this is a 'custom' exception";

    private CmdLineParser parser;

    @BeforeEach
    public void setup() {
        parser = new CmdLineParser(this);
    }

    @Option(name="-x")
    public void setX(String x) {
        throw new IllegalArgumentException(ERR_MSG_X);
    }
    
    @Option(name="-y")
    public void setY(String y) throws InvalidAttributeValueException {
        throw new InvalidAttributeValueException(ERR_MSG_Y);
    }
    
    @Test
    public void testRuntimeException() throws Exception {
        var e = assertThrows(IllegalArgumentException.class, () -> parser.parseArgument("-x", "value"));
        assertEquals(ERR_MSG_X, e.getMessage());
    }
    
    @Test
    public void testCustomException() throws Exception {
        // var e = assertThrows(InvalidAttributeValueException.class, () -> parser.parseArgument("-y", "value"));
        var e = assertThrows(CmdLineException.class, () -> parser.parseArgument("-y", "value"));
        var cause = e.getCause();
        assertTrue(cause.getClass() == InvalidAttributeValueException.class);
        assertEquals(ERR_MSG_Y, cause.getMessage());
    }
}