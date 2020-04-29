package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FieldParserTest {

	private void parse(Object bean, String... args) throws CmdLineException, ClassNotFoundException {
        CmdLineParser p = new CmdLineParser(new Object());
		new FieldParser().parse(p, bean);
		p.parseArgument(args);
    }

	@Test
	public void testNoArgs() throws CmdLineException, ClassNotFoundException {
		Bean bean = new Bean();
		parse(bean);
		assertEquals("default", bean.text);
		assertEquals(-1, bean.number);
	}

	@Test
	public void testFields() throws CmdLineException, ClassNotFoundException {
		Bean bean = new Bean();
		parse(bean, "-text", "newText", "-number", "42");
		assertEquals("newText", bean.text);
		assertEquals(42, bean.number);
	}

	@Test
	public void testInheritedFields() throws CmdLineException, ClassNotFoundException {
		InheritedBean bean = new InheritedBean();
		parse(bean, "-text", "newText", "-number", "42", "-text2", "newText");
		assertEquals("newText", bean.text2);
		assertEquals("newText", bean.text);
		assertEquals(42, bean.number);
	}

}
