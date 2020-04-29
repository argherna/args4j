package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KeyValueTest {

  private CmdLineParser parser;

  private KeyValue testObject;

  @BeforeEach
  public void getTestObject() {
    testObject = new KeyValue();
    parser = new CmdLineParser(testObject);
  }

  @Test
  public void testDouble() {
    try {
      parser.parseArgument(new String[] {"--double=42.54"});
    } catch (CmdLineException e) {
      fail(e.getMessage());
    }
    assertEquals(42.54, testObject._double, 0);
  }

  @Test
  public void testDoubleShort() {
    try {
      parser.parseArgument(new String[] {"-d=42.54"});
    } catch (CmdLineException e) {
      fail(e.getMessage());
    }
    assertEquals(42.54, testObject._double, 0);
  }

  @Test
  public void testChar() {
    try {
      parser.parseArgument(new String[] {"--string=stringValue"});
    } catch (CmdLineException e) {
      fail(e.getMessage());
    }
    assertEquals("stringValue", testObject._string);
  }

  @Test
  public void testCharShort() {
    try {
      parser.parseArgument(new String[] {"-s=stringValue"});
    } catch (CmdLineException e) {
      fail(e.getMessage());
    }
    assertEquals("stringValue", testObject._string);
  }

  private static class KeyValue {

    @Option(name = "-s", aliases = "--string")
    public String _string;

    @Option(name = "-d", aliases = "--double")
    public double _double;

  }

}
