package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NativeTypesTest {

	private CmdLineParser parser;

	private NativeTypes testObject;

	@BeforeEach
	public void setup() {
		testObject = new NativeTypes();
		parser = new CmdLineParser(testObject);
	}

	@Test
	public void testBooleanTrue() {
		try {
			parser.parseArgument(new String[] {"-boolean"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertTrue(testObject._boolean);
	}

	@Test
	public void testBooleanFalse() {
		try {
			parser.parseArgument(new String[] {});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertFalse(testObject._boolean);
	}

	@Test
	public void testByte() {
		try {
			parser.parseArgument(new String[] {"-byte", "42"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(42, testObject._byte);
	}

	@Test
	public void testChar() {
		try {
			parser.parseArgument(new String[] {"-char", "a"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals('a', testObject._char);
	}

	@Test
	public void testDouble() {
		try {
			parser.parseArgument(new String[] {"-double", "42"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(42, testObject._double, 0);
	}

	@Test
	public void testFloat() {
		try {
			parser.parseArgument(new String[] {"-float", "42"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(42, testObject._float, 0);
	}

	@Test
	public void testInt() {
		try {
			parser.parseArgument(new String[] {"-int", "42"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(42, testObject._int);
	}

	@Test
	public void testLong() {
		try {
			parser.parseArgument(new String[] {"-long", "42"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(42, testObject._long);
	}

	@Test
	public void testShort() {
		try {
			parser.parseArgument(new String[] {"-short", "42"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(42, testObject._short);
	}

	private static class NativeTypes {

		@Option(name = "-boolean")
		public boolean _boolean;

		@Option(name = "-byte")
		public byte _byte;

		@Option(name = "-char")
		public char _char;

		@Option(name = "-double")
		public double _double;

		@Option(name = "-float")
		public float _float;

		@Option(name = "-int")
		public int _int;

		@Option(name = "-long")
		public long _long;

		@Option(name = "-short")
		public short _short;
	}
}
