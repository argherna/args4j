package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class InheritanceTest {

    private CmdLineParser parser;

    private Inheritance testObject;

    @BeforeEach
    public void setup() {
        testObject = new Inheritance();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testMyself() {
        Inheritance bo = testObject;
        try {
            parser.parseArgument(new String[] {"-m", "Thats me"});
            assertEquals("Thats me", bo.me);
        } catch (CmdLineException e) {
            fail("This exception should not occur");
        }
    }

    @Test
    public void testFather() {
        Inheritance bo = testObject;
        try {
            parser.parseArgument(new String[] {"-f", "My father"});
            assertEquals("My father", bo.father);
        } catch (CmdLineException e) {
            fail("This exception should not occur");
        }
    }

    @Test
    public void testGrandfather() {
        Inheritance bo = testObject;
        try {
            parser.parseArgument(new String[] {"-g", "My fathers father"});
            assertEquals("My fathers father", bo.grandpa);
        } catch (CmdLineException e) {
            fail("This exception should not occur");
        }
    }

    @Test
    public void testMother() {
        Inheritance bo = testObject;
        try {
            parser.parseArgument(new String[] {"-mom", "Hi Mom"});
            assertNull(bo.mom);
        } catch (CmdLineException e) {
            // no-op
        }
    }

}
