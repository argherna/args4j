package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PropsTest {

    private CmdLineParser parser;

    private Props testObject;

    @BeforeEach
    public void setup() {
        testObject = new Props();
        parser = new CmdLineParser(testObject);
    }

    @Test
    public void testNoValues() {
        try {
            parser.parseArgument(new String[] {});
            Map<String, String> map = testObject.props;
            if (map == null) {
                // as expected
                return;
            }
            assertTrue(map.isEmpty());
        } catch (CmdLineException e) {
            fail("Call without parameters is valid! " + e.getMessage());
        }
    }

    @Test
    public void testHasEqual() {
        try {
            parser.parseArgument(new String[] {"-T", "key1=value1=more", "-T", "key2=value2=more"});
            Map<String, String> map = testObject.props;
            assertTrue(map.containsKey("key1"));
            assertTrue(map.containsKey("key2"));
            assertEquals(2, map.size());
            assertEquals("value1=more", map.get("key1"));
            assertEquals("value2=more", map.get("key2"));
        } catch (CmdLineException e) {
            fail("Caught an invalid exception: " + e.getMessage());
        }
    }

    @Test
    public void testSinglePair() {
        try {
            parser.parseArgument(new String[] {"-T", "key1=value1"});
            Map<String, String> map = testObject.props;
            assertTrue(map.containsKey("key1"));
            assertEquals(1, map.size());
            assertEquals("value1", map.get("key1"));
        } catch (CmdLineException e) {
            fail("Caught an invalid exception: " + e.getMessage());
        }
    }

    @Test
    public void testMultiplePairs() {
        try {
            parser.parseArgument(
                    new String[] {"-T", "key1=value1", "-T", "key2=value2", "-T", "key3=value3"});
            Map<String, String> map = testObject.props;
            assertTrue(map.containsKey("key1"));
            assertTrue(map.containsKey("key2"));
            assertTrue(map.containsKey("key3"));
            assertEquals(3, map.size());
            assertEquals("value1", map.get("key1"));
            assertEquals("value2", map.get("key2"));
            assertEquals("value3", map.get("key3"));
        } catch (CmdLineException e) {
            fail("Caught an invalid exception: " + e.getMessage());
        }
    }

    @Test
    public void testDuplicateKey() {
        try {
            parser.parseArgument(new String[] {"-T", "key1=one", "-T", "key1=two"});
            Map<String, String> map = testObject.props;
            assertTrue(map.containsKey("key1"));
            assertEquals(1, map.size());
            assertEquals("two", map.get("key1"));
        } catch (CmdLineException e) {
            fail("Caught an invalid exception: " + e.getMessage());
        }
    }

    @Test
    public void testInitialisation() {
        try {
            testObject.props = null;
            parser.parseArgument(new String[] {"-T", "key1=value1"});
            Map<String, String> map = testObject.props;
            assertNotNull(map);
            assertEquals("value1", map.get("key1"));
        } catch (CmdLineException e) {
            fail("Caught an invalid exception: " + e.getMessage());
        }
    }

    @Test
    public void testNoValue() {
        try {
            parser.parseArgument(new String[] {"-T", "key="});
            Map<String, String> map = testObject.props;
            assertNull(map.get("key"));
        } catch (CmdLineException e) {
            fail("Caught an invalid exception: " + e.getMessage());
        }
    }

    @Test
    public void testNoKey() {
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-T", "=value"}));
        assertEquals("A key must be set.", e.getMessage());
    }

    @Test
    public void testNoSplitCharacter() {
        var e = assertThrows(CmdLineException.class,
                () -> parser.parseArgument(new String[] {"-T", "keyvalue"}));
        assertEquals("An argument for setting a Map must contain a \"=\"", e.getMessage());
    }

    private static class Props {

        @Option(name = "-T", usage = "sets a key-value-pair")
        public Map<String, String> props = new HashMap<String, String>();

    }
}
