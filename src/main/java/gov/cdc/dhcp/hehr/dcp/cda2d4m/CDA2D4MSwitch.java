package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Encounter;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Organizer;
import org.openhealthtools.mdht.uml.cda.Patient;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Procedure;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.SubstanceAdministration;
import org.openhealthtools.mdht.uml.cda.util.CDASwitch;
import org.openhealthtools.mdht.uml.hl7.datatypes.DatatypesFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDA2D4MSwitch extends CDASwitch<List<String>> implements Switch {

	private static final Logger LOG = LoggerFactory.getLogger(CDA2D4MSwitch.class);

	RIM2D4MSwitch rim;

	ElementFormatter formatter;

	String docEffectiveTime;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyymmdd");
	
	public CDA2D4MSwitch() {
		super();
		rim = new RIM2D4MSwitch();
	}

	public List<String> caseActs(EList<Act> acts) {
		List<String> list = new ArrayList<String>();
		for (Act act : acts) {
			this.doSwitch(act);
		}
		return list;
	}

	@Override
	public List<String> caseAct(Act act) {
		List<String> list = new ArrayList<String>();
		try {
			rim.setTemp("moodCode");
			list.addAll(rim.doSwitch(DatatypesFactory.eINSTANCE.createST(act.getMoodCode().getName())));
			list.addAll(rim.caseIIs(act.getIds()));
			list.addAll(rim.doSwitch(act.getCode()));
			rim.setTemp("effectiveTime");
			list.addAll(rim.doSwitch(act.getEffectiveTime()));
			list.addAll(caseObservations(act.getObservations()));
		} catch (NullPointerException e) {
			LOG.error("Mood code name is null.");
		}
		return insertPathing(list, "Act");
	}

//	@Override
//	public T caseContinuityOfCareDocument(ContinuityOfCareDocument ccd) {
//		List<String> list = new ArrayList<String>();
//		LOG.trace("ClinicalDocument==>");
//		list.addAll(rim.doSwitch(ccd.getId()));
//		rim.setTemp("effectiveTime");
//		list.addAll(rim.doSwitch(ccd.getEffectiveTime()));
//		list.addAll(rim.doSwitch(ccd.getCode()));
//		list.addAll(rim.doSwitch(ccd.getLanguageCode()));
//		list.addAll(this.doSwitch(ccd.getPatientRoles()));
//		list.addAll(this.doSwitch(ccd.getSections()));
//		LOG.trace("<==ClinicalDocument");
//		return list;
//	}

	@Override
	public List<String> caseClinicalDocument(ClinicalDocument cda) {
		List<String> list = new ArrayList<String>();
		LOG.trace("ClinicalDocument==>");
		list.addAll(rim.doSwitch(cda.getId()));
		rim.setTemp("title");
		list.addAll(rim.doSwitch(cda.getTitle()));
		rim.setTemp("effectiveTime");
		List<String> effectiveTimeList = rim.doSwitch(cda.getEffectiveTime());
		this.docEffectiveTime = effectiveTimeList.get(0);
		list.addAll(effectiveTimeList);
		list.addAll(rim.doSwitch(cda.getCode()));
		list.addAll(rim.doSwitch(cda.getLanguageCode()));
		list.addAll(this.casePatientRoles(cda.getPatientRoles()));
		list.addAll(this.caseSections(cda.getSections()));
		LOG.trace("<==ClinicalDocument");
		return list;
	}

	public List<String> caseEncounters(EList<Encounter> encounters) {
		List<String> list = new ArrayList<String>();
		for (Encounter encounter : encounters) {
			list.addAll(this.doSwitch(encounter));
		}
		return list;
	}
	
	@Override
	public List<String> caseEncounter(Encounter encounter) {
		List<String> list = new ArrayList<String>();
		if (encounter != null) {
			list.addAll(rim.caseIIs((encounter.getIds())));
			rim.setTemp("effectiveTime");
			list.addAll(rim.doSwitch((encounter.getEffectiveTime())));
			rim.setTemp("effectiveTime.low");
			list.addAll(rim.doSwitch((encounter.getEffectiveTime().getLow())));
			rim.setTemp("effectiveTime.high");
			list.addAll(rim.doSwitch((encounter.getEffectiveTime().getHigh())));
			list.addAll(rim.doSwitch((encounter.getCode())));
			list.addAll(rim.doSwitch((encounter.getText())));
			list.addAll(this.caseActs(encounter.getActs()));
			list.addAll(this.caseObservations(encounter.getObservations()));
		}
		return insertPathing(list, "Encounter");
	}

	public List<String> caseEntries(EList<Entry> entries) {
		List<String> list = new ArrayList<String>();
		LOG.trace("entries=" + entries.size());
		for (Entry entry : entries) {
			list.addAll(this.doSwitch(entry));
		}
		return list;
	}

	@Override
	public List<String> caseEntry(Entry entry) {
		List<String> list = new ArrayList<String>();
		if (entry.getAct() != null) {
			LOG.trace("entry.getAct()=" + entry.getAct());
			list.addAll(this.doSwitch(entry.getAct()));
		}
		if (entry.getEncounter() != null) {
			LOG.trace("entry.getEncounter()=" + entry.getEncounter());
			list.addAll(this.doSwitch(entry.getEncounter()));
		}
		if (entry.getObservation() != null) {
			LOG.trace("entry.getObservation()=" + entry.getObservation());
			list.addAll(this.doSwitch(entry.getObservation()));
		}
		if (entry.getOrganizer() != null) {
			LOG.trace("entry.getOrganizer()=" + entry.getOrganizer());
			list.addAll(this.doSwitch(entry.getOrganizer()));
		}
		if (entry.getProcedure() != null) {
			LOG.trace("entry.getProcedure()=" + entry.getProcedure());
			list.addAll(this.doSwitch(entry.getProcedure()));
		}
		if (entry.getSubstanceAdministration() != null) {
			LOG.trace("entry.getSubstanceAdministration()=" + entry.getSubstanceAdministration());
			list.addAll(this.doSwitch(entry.getSubstanceAdministration()));
		}
		return list;
	}

	public List<String> casePatientRoles(EList<PatientRole> roles) {
		List<String> list = new ArrayList<String>();
		LOG.trace("roles=" + roles.size());
		for (PatientRole role : roles) {
			list.addAll(this.doSwitch(role));
		}
		return list;
	}

	public List<String> caseObservations(EList<Observation> observations) {
		List<String> list = new ArrayList<String>();
		for (Observation observation : observations) {
			list.addAll(this.doSwitch(observation));
		}
		return list;
	}

	@Override
	public List<String> caseObservation(Observation observation) {
		List<String> list = new ArrayList<String>();
		if (observation != null) {
			list.addAll(rim.caseIIs((observation.getIds())));
			list.addAll(rim.doSwitch((observation.getCode())));
			list.addAll(rim.doSwitch((observation.getEffectiveTime())));
			list.addAll(rim.doSwitch(observation.getStatusCode()));
			list.addAll(rim.doSwitch(observation.getText()));
		}
		return insertPathing(list, "Observation");
	}

	@Override
	public List<String> caseOrganizer(Organizer organizer) {
		List<String> list = new ArrayList<String>();
		if (organizer != null) {
			list.addAll(rim.doSwitch(organizer.getCode()));
			list.addAll(rim.doSwitch(organizer.getEffectiveTime()));
			list.addAll(caseEncounters(organizer.getEncounters()));
			list.addAll(caseObservations(organizer.getObservations()));
		}
		return list;
	}

	public List<String> casePatient(Patient patient) {
		List<String> list = new ArrayList<String>();
		if (patient != null) {
			list.add(String.format("%s%s%s", "Patient.RaceCode", VALUE_DELIM, patient.getRaceCode().getDisplayName()));
			list.add(String.format("%s%s%s", "Patient.AdministrativeGenderCode", VALUE_DELIM,
					patient.getAdministrativeGenderCode().getCode()));
			rim.setTemp("birthTime");
			List<String> patientBirthTimeList = rim.doSwitch(patient.getBirthTime());
			list.add(String.format("%s%s%s", "Patient.age", VALUE_DELIM, calculateAge(patientBirthTimeList.get(0), docEffectiveTime)));
			list.addAll(patientBirthTimeList);
			LOG.info("birthTime==>" + "Patient.birthTime" + "=" + patient.getBirthTime().getValue());
		}
		return insertPathing(list, "Patient");
	}

	@Override
	public List<String> caseProcedure(Procedure procedure) {
		List<String> list = new ArrayList<String>();
		if (procedure != null) {
			list.addAll(rim.caseIIs((procedure.getIds())));
			list.addAll(rim.doSwitch((procedure.getCode())));
			list.addAll(rim.doSwitch((procedure.getEffectiveTime())));
			list.addAll(rim.doSwitch(procedure.getStatusCode()));
			list.addAll(rim.doSwitch(procedure.getText()));
		}
		return insertPathing(list, "Procedure");
	}

	public List<String> casePatientRole(PatientRole role) {
		List<String> list = new ArrayList<String>();
		LOG.trace("role=" + role);
		if (role != null) {
			list.addAll(this.doSwitch((role).getPatient()));
		}
		return insertPathing(list, "PatientRole");
	}

	public List<String> caseSections(EList<Section> sections) {
		List<String> list = new ArrayList<String>();
		LOG.trace("sections=" + sections.size());
		for (Section section : sections) {
			list.addAll(this.doSwitch(section));
		}
		return list;
	}

	@Override
	public List<String> caseSection(Section section) {
		List<String> list = new ArrayList<String>();
		if (section.getId() != null) {
			list.addAll(rim.doSwitch(section.getId()));
		}
		try {
			if (section.getTitle() != null) {
				list.add(String.format("title%s%s", VALUE_DELIM, fillInTheBlanks(section.getTitle().getText())));
			}
			list.addAll(rim.doSwitch(section.getCode()));
		} catch (NullPointerException e) {
			LOG.error("Section title is null.");
		}
		list.addAll(this.caseEntries(section.getEntries()));
		return insertPathing(list, "Section");
	}

	@Override
	public List<String> caseSubstanceAdministration(SubstanceAdministration substanceAdministration) {
		List<String> list = new ArrayList<String>();
		LOG.trace("substanceAdministration=" + substanceAdministration);
		if (substanceAdministration != null) {
			list.addAll(checkFirst((substanceAdministration.getAdministrationUnitCode())));
			list.addAll(checkFirst((substanceAdministration.getCode())));
			rim.setTemp("effectiveTime");
			list.addAll(rim.caseSXCM_TSs(substanceAdministration.getEffectiveTimes()));
			list.addAll(checkFirst(substanceAdministration.getPriorityCode()));
			list.addAll(checkFirst(substanceAdministration.getRouteCode()));
			list.addAll(checkFirst(substanceAdministration.getStatusCode()));
			list.addAll(checkFirst(substanceAdministration.getText()));
		}
		return insertPathing(list, "SubstanceAdministration");
	}

	public List<String> defaultCase(EObject eObject) {
		return rim.doSwitch(eObject);
	}

	public String calculateAge(String birthTime, String effectiveTime) {
	    return Long.toString(ChronoUnit.YEARS.between(parseDate(birthTime), parseDate(effectiveTime)));
	}
	
	public LocalDate parseDate(String time) {
		int y = Integer.parseInt(time.substring(0, 4));
		int m = Integer.parseInt(time.substring(4, 6));
		int d = Integer.parseInt(time.substring(6, 8));
		return LocalDate.of(y, m, d);
	}
	
	public List<String> checkFirst(EObject eObject) {
		List<String> list = new ArrayList<String>();
		if (eObject != null) {
			list.addAll(rim.doSwitch(eObject));
		}
		return list;
	}

	public List<String> insertPathing(List<String> list, String insert) {
		for (int i = 0; i < list.size(); i++) {
			list.set(i, String.format("%s%s%s", insert, PATH_DELIM, list.get(i)));
		}
		return list;
	}

	public String fillInTheBlanks(String s) {
		return s.replaceAll(" ", "_");
	}
}
