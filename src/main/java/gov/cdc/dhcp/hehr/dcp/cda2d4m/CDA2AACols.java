package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import java.util.ArrayList;
import java.util.List;

import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.AssignedEntity;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Component4;
import org.openhealthtools.mdht.uml.cda.Encounter;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.LabeledDrug;
import org.openhealthtools.mdht.uml.cda.Material;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Organization;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.Patient;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Performer2;
import org.openhealthtools.mdht.uml.cda.Procedure;
import org.openhealthtools.mdht.uml.cda.RegionOfInterest;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.SubstanceAdministration;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.ADXP;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVL_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ON;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.rim.ActRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDA2AACols {

	private static final Logger LOG = LoggerFactory.getLogger(CDA2AACols.class);

	public static final String PATH_DELIM = ".";

	public static final String VALUE_DELIM = ":";

	public List<String> build(ClinicalDocument el) {
		List<String> list = new ArrayList<String>();
		LOG.trace("ClinicalDocument==>");
		list.addAll(build(el.getId()));
		list.addAll(build(el.getEffectiveTime(), "effectiveTime"));
		list.addAll(build(el.getCode()));
		list.addAll(build(el.getLanguageCode()));
		list.addAll(buildPatientRoles(el.getPatientRoles()));
		list.addAll(build(el.getSections()));
		LOG.trace("<==ClinicalDocument");
		return list;
	}

	public List<String> build(List<Section> el) {
		List<String> list = new ArrayList<String>();
		for (Section item : el) {
			LOG.trace("Sections==>");
			list.addAll(build(item));
			LOG.trace("<==Sections");
		}
		return list;
	}

	public List<String> buildPatientRoles(List<PatientRole> el) {
		List<String> list = new ArrayList<String>();
		for (PatientRole item : el) {
			list.addAll(build(item));
		}
		return list;
	}

	public List<String> build(Section el) {
		List<String> list = new ArrayList<String>();
		String title = el.getTitle().getText().replaceAll(" ", "_");
		LOG.trace("Section==>" + el);
		LOG.trace("Section.title=" + title);
		for (Entry item : el.getEntries()) {
			List<String> list1 = build(item);
			for (String s : list1) {
				list.add(String.format("%s%s%s", title, PATH_DELIM, fillInTheBlanks(s)));
			}
		}
		LOG.trace("<==Section " + list);
		return list;
	}

	public List<String> build(Entry el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Entry==>" + el);
			list.addAll(build(el.getObservation()));
			list.addAll(build(el.getEncounter()));
			list.addAll(build(el.getProcedure()));
			list.addAll(build(el.getRegionOfInterest()));
			list.addAll(build(el.getOrganizer()));
			list.addAll(build(el.getSubstanceAdministration()));
			list.addAll(build(el.getAct()));
			LOG.trace("<==Entry " + list);
		}
		return list;
	}

	public List<String> build(Organizer el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Organizer==>" + el);
			list.addAll(build(el.getCode()));
			for (Component4 el1 : el.getComponents()) {
				list.addAll(build(el1.getObservation()));
			}
			LOG.trace("<==Organizer " + list);
		}
		return list;
	}

	public List<String> build(Observation el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Observation==>" + el);
			for (String s : build(el.getCode())) {
				list.add(String.format("%s%s%s", el.eClass().getName(), PATH_DELIM, fillInTheBlanks(s)));
			}
			LOG.trace("<==Observation " + list);
		}
		return list;
	}

	public List<String> build(SubstanceAdministration el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("SubstanceAdministration==>" + el);
			for (String s : build(el.getCode())) {
				list.add(String.format("%s%s%s", el.eClass().getName(), PATH_DELIM, s));
			}
			list.addAll(build(el.getConsumable().getManufacturedProduct().getManufacturedMaterial()));
			list.addAll(build(el.getConsumable().getManufacturedProduct().getManufacturedLabeledDrug()));
			list.addAll(build(el.getConsumable().getManufacturedProduct().getManufacturerOrganization()));
			LOG.trace("<==SubstanceAdministration " + list);
		}
		return list;
	}

	public List<String> build(Organization el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", el.eClass().getName(), PATH_DELIM, el.getNames().get(0).getText()));
			LOG.trace("Organization==>" + el);
			for (ON el1 : el.getNames()) {
				list.addAll(build(el1));
			}
			LOG.trace("<==Organization " + list);
		}
		return list;
	}

	public List<String> build(LabeledDrug el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", el.eClass().getName(), PATH_DELIM, el.getName().getText()));
			LOG.trace("LabeledDrug==>" + el);
			for (String s : build(el.getCode())) {
				list.add(String.format("%s%s%s", el.eClass().getName(), PATH_DELIM, fillInTheBlanks(s)));
			}
			LOG.trace("<==LabeledDrug " + list);
		}
		return list;
	}

	public List<String> build(Material el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%sTitle%s%s", el.eClass().getName(), PATH_DELIM, VALUE_DELIM,
					el.getName().getText()));
			LOG.trace("Material==>" + el);
			for (String s : build(el.getCode())) {
				list.add(String.format("%s%s%s", el.eClass().getName(), PATH_DELIM, fillInTheBlanks(s)));
			}
			LOG.trace("<==Material " + list);
		}
		return list;
	}

	public List<String> build(Encounter el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Encounter==>" + el);
			for (II el1 : el.getIds()) {
				list.addAll(build(el1));
			}
			list.addAll(build(el.getCode()));
			list.addAll(build(el.getPriorityCode()));
			list.addAll(build(el.getEffectiveTime()));
			LOG.trace("<==Encounter " + list);
		}
		return list;
	}

	public List<String> build(Procedure el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Procedure==>" + el);
			list.addAll(build(el.getCode()));
			list.addAll(build(el.getPriorityCode()));
			LOG.trace("<==Procedure " + list);
		}
		return list;
	}

	public List<String> build(RegionOfInterest el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(build(el.getCode()));
		}
		return list;
	}

	public List<String> build(ActRelationship el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(build((Act) el.getTarget()));
		}
		return list;
	}

	public List<String> build(Act el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Act==>" + el);
			list.addAll(build(el.getCode()));
			for (Observation el1 : el.getObservations()) {
				list.addAll(build(el1));
			}
			for (Encounter el1 : el.getEncounters()) {
				list.addAll(build(el1));
			}
			for (Procedure el1 : el.getProcedures()) {
				list.addAll(build(el1));
			}
			for (EntryRelationship el1 : el.getEntryRelationships()) {
				list.addAll(build(el1));
			}
			LOG.trace("<==Act " + list);
		}
		return list;
	}

	public List<String> build(EntryRelationship el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("EntryRelationship==>" + el);
			list.addAll(build(el.getObservation()));
			list.addAll(build(el.getEncounter()));
			list.addAll(build(el.getProcedure()));
			list.addAll(build(el.getSubstanceAdministration()));
			LOG.trace("<==EntryRelationship " + list);
		}
		return list;
	}

	public List<String> build(Performer2 el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", "Performer2", PATH_DELIM, el.getAssignedEntity()));
		}
		return list;
	}

	public List<String> build(AssignedEntity el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", "AssignedEntity", PATH_DELIM, el.getIds()));
			list.add(String.format("%s%s%s", "AssignedEntity", el.getParticipations().get(0)));
		}
		return list;
	}

	public List<String> build(PatientRole el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(build(el.getPatient()));
			
			list.addAll(build(el.getAddrs().get(0)));
		}
		return list;
	}

	public List<String> build(Patient el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", "Patient.RaceCode", VALUE_DELIM, el.getRaceCode().getDisplayName()));
			list.add(String.format("%s%s%s", "Patient.AdministrativeGenderCode", VALUE_DELIM, el.getAdministrativeGenderCode().getCode()));
			list.add(String.format("%s%s%s", "Patient.birthTime", VALUE_DELIM, el.getBirthTime().getValue()));
			LOG.info("birthTime==>" + "Patient.birthTime" + "=" + el.getBirthTime().getValue());
		}
		return list;
	}

	public List<String> build(AD el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(build(el.getPostalCodes().get(0)));
		}
		return list;
	}

	public List<String> build(ADXP el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", "Address", VALUE_DELIM, el.getText()));
		}
		return list;
	}

	public List<String> build(II el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", "id", VALUE_DELIM, el.getExtension()));
		}
		return list;
	}

	public List<String> build(IVL_TS el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(build(el.getLow(), "low"));
			list.addAll(build(el.getHigh(), "high"));
		}
		return list;
	}

	public List<String> build(IVXB_TS el, String highlow) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("effectiveTime%s%s%s%s", PATH_DELIM, highlow, VALUE_DELIM, el.getValue()));
		}
		return list;
	}

	public List<String> build(ON el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", "OrgainizationName", VALUE_DELIM, el.getText()));
		}
		return list;
	}

	public List<String> build(TS el, String name) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.info("effectiveTime==>" + name + "=" + el.getValue());
			list.add(String.format("%s%s%s", name, VALUE_DELIM, el.getValue()));
		}
		return list;
	}

	public List<String> build(CD el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(checkFirst(el));
		}
		return list;
	}

	public List<String> build(CE el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(checkFirst(el));
		}
		return list;
	}

	public List<String> build(CS el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(checkFirst(el));
		}
		return list;
	}

	public List<String> checkFirst(CD el) {

		List<String> list = new ArrayList<String>();
		if (el != null) {
			if (el.getCode() != null) {
				list.add(String.format("%s%s%s", "code", VALUE_DELIM, fillInTheBlanks(el.getCode())));
			}
			if (el.getCodeSystemName() != null) {
				list.add(String.format("%s%s%s", "codeSystem", VALUE_DELIM, fillInTheBlanks(el.getCodeSystemName())));
			}
			if (el.getDisplayName() != null) {
				list.add(String.format("%s%s%s", "codeDisplay", VALUE_DELIM, fillInTheBlanks(el.getDisplayName())));
			}
		}
		return list;
	}
	
	public String fillInTheBlanks(String s) {
		return s.replaceAll(" ", "_");
	}
}