package org.kohsuke.args4j.spi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.net.InetAddress;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kohsuke.args4j.CmdLineException;

public class InetAddressOptionHandlerTest {

	private InetAddressOptionHandler handler;

	@BeforeEach
	public void setUp() {
		handler = new InetAddressOptionHandler(null, null, null);
	}

	@Test
	public void testParseSuccess() throws Exception {
		InetAddress expectedIp =
				InetAddress.getByAddress(new byte[] {(byte) 1, (byte) 2, (byte) 3, (byte) 4});
		InetAddress ip = handler.parse("1.2.3.4");

		assertEquals(expectedIp, ip);
	}

	@Test
	public void testParseFailure() throws Exception {
		var e = assertThrows(CmdLineException.class,
				() -> handler.parse("bogus.ip.address.nosuch."));
		assertEquals("\"bogus.ip.address.nosuch.\" must be an IP address", e.getMessage());
	}
}
