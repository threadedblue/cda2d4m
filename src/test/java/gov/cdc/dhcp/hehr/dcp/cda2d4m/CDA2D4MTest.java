package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CDA2D4MTest {

	private static final Logger LOG = LoggerFactory.getLogger(CDA2D4MTest.class);

	static CDA2D4M sut;
	static ClinicalDocument cda;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		sut = new CDA2D4M();
		InputStream is = CDA2D4M.class.getClassLoader()
				.getResourceAsStream("Carla633_Johns824_a093c28e-4a7b-47d5-960c-39a1182fb694.xml");
		InputStream is2 = CDA2D4M.class.getClassLoader()
				.getResourceAsStream("Carla633_Johns824_a093c28e-4a7b-47d5-960c-39a1182fb694.xml");
		cda = CDAUtil.load(is);
//		ClinicalDocument cda2 = CDAUtil.load(is2);
//		LOG.debug("cda=" + cda);
//		LOG.debug("cda2=" + cda2);
//		for (Section sec : cda.getSections()) {
//			LOG.debug("cdasec=" + sec.getTitle().getText());
//		}
//		for (Section sec : cda2.getSections()) {
//			LOG.debug("cda2sec=" + sec.getTitle().getText());
//		}
	}

	@Test
	void testClinicalDocument() {
//		assertEquals("II.a093c28e-4a7b-47d5-960c-39a1182fb694", sut.extract(cda.getId()).get(0));
		for(String s : sut.extract(cda)) {
			LOG.debug("s==>" + s);
		}
	}
	
//	@Test
	void testSection() {
//		assertEquals("II.a093c28e-4a7b-47d5-960c-39a1182fb694", sut.extract(cda.getId()).get(0));
		for(String s : sut.extract(cda.getSections())) {
			LOG.debug("s=" +s);
		}
	}

//	@Test
	void testExtractProblemSection() {
//		assertEquals("II.a093c28e-4a7b-47d5-960c-39a1182fb694", sut.extract(cda.getId()).get(0));
		LOG.debug(sut.extract(cda.getId()).get(0));
	}

//	@Test
	void testExtractImmunizationsSection() {
//		assertEquals("II.a093c28e-4a7b-47d5-960c-39a1182fb694", sut.extract(cda.getId()).get(0));
//		LOG.debug(sut.extract(cda.getId()).get(0));
	}

//	@Test
	void testExtractII() {
		assertEquals("II.a093c28e-4a7b-47d5-960c-39a1182fb694", sut.extract(cda.getId()).get(0));
	}

//	@Test
	void testExtractTS() {
		assertEquals("TS.20201006100351", sut.extract(cda.getEffectiveTime()).get(0));
	}

//	@Test
	void testExtractCE() {
		assertEquals("CE.34133-9", sut.extract(cda.getCode()).get(0));
		assertEquals("CE.LOINC", sut.extract(cda.getCode()).get(1));
	}

//	@Test
	void testExtractCS() {
		assertEquals("CS.en-US", sut.extract(cda.getLanguageCode()).get(0));
	}
}
