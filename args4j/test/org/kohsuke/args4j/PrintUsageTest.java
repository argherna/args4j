package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.kohsuke.args4j.Args4JUtilities.getUsageLines;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrintUsageTest {

	private Bean testObject;

	private CmdLineParser parser;

	private static class Bean {
		@Option(name = "s", usage = "1234567890123456789012345678901234567890")
		public String s;
	}

	@BeforeEach
	public void setup() {
		testObject = new Bean();
		parser = new CmdLineParser(testObject);
	}

	@Test
	public void testEnoughLength() {
		assertThrows(CmdLineException.class, () -> parser.parseArgument(new String[] {"-wrong"}));
		var usageMessage = getUsageLines(parser);
		assertEquals(1, usageMessage.length);
	}

	@Test
	public void testTooSmallLength() {
		parser = new CmdLineParser(testObject, ParserProperties.defaults().withUsageWidth(30));
		assertThrows(CmdLineException.class, () -> parser.parseArgument(new String[] {"-wrong"}));
		var usageMessage = getUsageLines(parser);
		assertEquals(2, usageMessage.length);
	}

}
