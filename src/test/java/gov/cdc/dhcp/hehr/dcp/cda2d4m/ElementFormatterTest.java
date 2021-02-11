package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ElementFormatterTest {



	@Test
	void testNextElement() {
		assertEquals("[01]", ElementFormatter.get().nextElement());
		assertEquals("[02]", ElementFormatter.get().nextElement());
		assertEquals("[03]", ElementFormatter.get().nextElement());
		ElementFormatter.get().reset();
		assertEquals("[01]", ElementFormatter.get().nextElement());
		assertEquals("[02]", ElementFormatter.get().nextElement());
		assertNotEquals("[01]", ElementFormatter.get().nextElement());
	}
}