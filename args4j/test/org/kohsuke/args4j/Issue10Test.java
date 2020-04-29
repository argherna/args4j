package org.kohsuke.args4j;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

/**
 * @see https://args4j.dev.java.net/issues/show_bug.cgi?id=10
 */
public class Issue10Test {
	@Option(name="-enum", required=false, usage="Enum2")
	private Enum crash;
	
	enum Enum { 
		THIS, ENUM, HAS, A, VERY, LONG, USAGE, LINE,
		BECAUSE, OF, ITS, HUGE, LIST, Of, VALUES,
		SO, IT, WILL, CRASH
	}

	// The bug should be fixed with changing from manual printing to printf.
	@Test
	public void testIssue10() {
		CmdLineParser parser = new CmdLineParser(this);
		assertDoesNotThrow(() -> parser.printUsage(new ByteArrayOutputStream()));
		// occurred error: StringIndexOutOfBoundsException with index < 0
	}
	
}
