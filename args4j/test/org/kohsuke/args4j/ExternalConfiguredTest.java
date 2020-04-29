package org.kohsuke.args4j;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExternalConfiguredTest {

	private CmdLineParser parser;
	private boolean recursive = false;
	private File out;
	private String str = "default";
	private boolean data = false;
	
	@SuppressWarnings("unused")
	private List<String> arguments;

	/**
	 * Unannotated setter to test setting a value from an external configuration file.
	 */
	public void setStr(String value) {
		this.str = value.toUpperCase();
	}

	@BeforeEach
	public void setUp() {
		parser = new CmdLineParser(new Object());
		new XmlParser().parse(getClass().getResource("ExternalConfiguredTest.xml"), parser, this);
	}

	@Test
	public void testNoArgsGiven() {
		try {
			parser.parseArgument(new String[] {});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertTrue("default".equals(str));
		assertFalse(recursive);
		assertFalse(data);
	}

	@Test
	public void testFieldSetter() {
		try {
			parser.parseArgument(new String[] {"-o", "myfile.txt"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertTrue("default".equals(str));
		assertFalse(recursive);
		assertFalse(data);
		assertEquals("myfile.txt", out.getName());
	}

	@Test
	public void testMethodSetter() {
		try {
			parser.parseArgument(new String[] {"-str", "myvalue"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertFalse(recursive);
		assertFalse(data);
		assertEquals("MYVALUE", str);
	}

	@Test
	public void testCustomHandler() {
		try {
			parser.parseArgument(new String[] {"-custom"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertTrue("default".equals(str));
		assertFalse(recursive);
		assertTrue(data);
	}
}
