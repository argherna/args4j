package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

public class ArgumentTest {
	protected static class MultiValueHolder {
		@Argument
		public List<String> things;
	}

	protected static class SingleValueHolder {
		@Argument(metaVar = "thing", required = true)
		public String thing;
	}

	protected static class BooleanValueHolder {
		@Argument(metaVar = "thing", required = true)
		public boolean b;
	}

	@Test
	public void testMultiValue() throws Exception {
		var holder = new MultiValueHolder();
		var parser = new CmdLineParser(holder);
		parser.parseArgument(new String[] {"one", "two"});

		assertEquals(2, holder.things.size());
		assertEquals("one", holder.things.get(0));
		assertEquals("two", holder.things.get(1));
	}

	@Test
	public void testTooFew() throws Exception {
		var holder = new SingleValueHolder();
		var parser = new CmdLineParser(holder);
		var e = assertThrows(CmdLineException.class, () -> parser.parseArgument(new String[] {}));
		assertEquals("Argument \"thing\" is required", e.getMessage());
	}

	@Test
	public void testBoolean() throws Exception {
		var holder = new BooleanValueHolder();
		var parser = new CmdLineParser(holder);
		parser.parseArgument(new String[] {"true"});
		assertTrue(holder.b);
	}

	@Test
	public void testIllegalBoolean() throws Exception {
		var holder = new BooleanValueHolder();
		var parser = new CmdLineParser(holder);
		var e = assertThrows(CmdLineException.class, () -> parser.parseArgument(new String[] {"xyz"}));
		assertEquals("\"xyz\" is not a legal boolean value", e.getMessage());
	}
}
