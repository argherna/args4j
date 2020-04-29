package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.EnumAttribute.Animal;


public class EnumArgumentTest {

	private CmdLineParser parser;

	private EnumArgument enumArg;

	@BeforeEach
	public void setup() {
		enumArg = new EnumArgument();
		parser = new CmdLineParser(enumArg);
	}

	@Test
	public void testSetEnum() {
		assertNull(enumArg.myAnimal);
		try {
			parser.parseArgument(new String[] {"HORSE"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(Animal.HORSE, enumArg.myAnimal);
	}

	@Test
	public void testSetEnumCaseInsensitive() {
		assertNull(enumArg.myAnimal);
		try {
			parser.parseArgument(new String[] {"horse"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(Animal.HORSE, enumArg.myAnimal);
	}

	@Test
	public void testIllegalArgumentOption() {
		var e = assertThrows(CmdLineException.class,
				() -> parser.parseArgument(new String[] {"ILLEGAL_ANIMAL"}));
		assertTrue(e.getMessage().startsWith(String.format("\"%s\" is not a valid value for \"",
				(new String[] {"ILLEGAL_ANIMAL"})[new String[] {"ILLEGAL_ANIMAL"}.length - 1])));
	}
}
