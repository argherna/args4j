package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.EnumAttribute.Animal;

public class EnumAttributeTest {

	private CmdLineParser parser;

	private EnumAttribute enumAttr;

	@BeforeEach
	public void setup() {
		enumAttr = new EnumAttribute();
		parser = new CmdLineParser(enumAttr);
	}

	@Test
	public void testSetEnum() {
		try {
			parser.parseArgument(new String[] {"-animal", "HORSE"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(Animal.HORSE, enumAttr.myAnimal);
	}

	@Test
	public void testSetEnumCaseInsensitive() {
		try {
			parser.parseArgument(new String[] {"-animal", "horse"});
		} catch (CmdLineException e) {
			fail(e.getMessage());
		}
		assertEquals(Animal.HORSE, enumAttr.myAnimal);
	}

	@Test
	public void testIllegalEnum() {
		assertThrows(CmdLineException.class,
				() -> parser.parseArgument(new String[] {"-animal", "ILLEGAL_ANIMAL"}));
	}

	@Test
	public void testUsage() {
		assertThrows(CmdLineException.class, () -> parser.parseArgument(new String[] {"-wrong"}));
		var usageLines = getUsageLines(parser);
		var containsHorse = false;
		for (var usageLine : usageLines) {
			containsHorse = usageLine.contains(EnumAttribute.Animal.HORSE.name());
			if (containsHorse) {
				break;
			}
		}
		assertTrue(containsHorse);
		var containsDuck = false;
		for (var usageLine : usageLines) {
			containsDuck = usageLine.contains(EnumAttribute.Animal.DUCK.name());
			if (containsDuck) {
				break;
			}
		}
		assertTrue(containsDuck);
	}

}
