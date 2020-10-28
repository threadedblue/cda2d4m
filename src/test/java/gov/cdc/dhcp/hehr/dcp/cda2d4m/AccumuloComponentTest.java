package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class AccumuloComponentTest {

	
	@Test
	void testConnect() {
		AccumuloComponent sut = new AccumuloComponent("", "ccd", AccumuloComponent.ACCUMULO_CREDS_FILE, "uno",
				"localhost:2181", true, true);
		assertTrue(sut.connect());
	}

}
