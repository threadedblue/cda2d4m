package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.cda.AssignedEntity;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.Encounter;
import org.openhealthtools.mdht.uml.cda.Entry;
import org.openhealthtools.mdht.uml.cda.EntryRelationship;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Patient;
import org.openhealthtools.mdht.uml.cda.PatientRole;
import org.openhealthtools.mdht.uml.cda.Performer2;
import org.openhealthtools.mdht.uml.cda.Procedure;
import org.openhealthtools.mdht.uml.cda.RegionOfInterest;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.hl7.datatypes.AD;
import org.openhealthtools.mdht.uml.hl7.datatypes.ADXP;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CE;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.rim.ActRelationship;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDA2D4M implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(CDA2D4M.class);

	public static final String ACCUMULO_CREDS_FILE = "accumulo-creds.yml";

	private CmdLineParser CLI = new CmdLineParser(this);

	@Option(name = "-i", aliases = "--input", required = true, usage = "Path to the input directory or file.")
	private String input;

	@Option(name = "-c", aliases = "--config", required = false, usage = "Path to hadoop config directory.")
	private String configFilePath = "/usr/local/hadoop/etc/hadoop";

	@Option(name = "-fs", aliases = "--filesystem", required = false, usage = "URL to the hadoop file system as a string.")
	private String fileSystem;

	@Option(name = "-l", aliases = "--instance", required = false, usage = "Name of Accumulo instance.")
	private String accumuloInstance;

	@Option(name = "-zk", aliases = "--zookeeper", required = true, usage = "URL to zookeeper instance as a string.")
	private String zookeeperURI;

	@Option(name = "-ow", aliases = "--overwrite", required = false, usage = "Overwrite output if exists.")
	private boolean overwrite;

	@Option(name = "-r", aliases = "--recurse", required = false, usage = "Set to reacuse throught directories. default = false;")
	private boolean recurse;

	@Option(name = "-o", aliases = "--output", required = true, usage = "Base name of the table set for D4M.")
	private String output;

	public static final String DELIM = ":";
	
	public CDA2D4M() {
	}

	public CDA2D4M(String[] args) throws CmdLineException {
		super();
		try {
			CLI.parseArgument(args);
		} catch (CmdLineException e) {
			LOG.error("", e);
		}
	}

	public void run() {
		try {
			Set<String> files = listFiles(input, 1);
			for (String file : files) {
				InputStream is;
				Path fileIn = Paths.get(input, file);
				is = Files.newInputStream(fileIn, StandardOpenOption.READ);
				ClinicalDocument cda = CDAUtil.load(is);
			}
		} catch (IOException e) {
			LOG.error("IO==>", e);
		} catch (Exception e) {
			LOG.error("Ex==>", e);
		}
	}

	public List<String> extract(ClinicalDocument el) {
		List<String> list = new ArrayList<String>();
		LOG.trace("ClinicalDocument==>");
		list.addAll(extract(el.getId()));
		list.addAll(extract(el.getEffectiveTime()));
		list.addAll(extract(el.getCode()));
		list.addAll(extract(el.getLanguageCode()));
		list.addAll(extractPatientRoles(el.getPatientRoles()));
		list.addAll(extract(el.getSections()));
		LOG.trace("<==ClinicalDocument");
		return list;
	}

	public List<String> extract(List<Section> el) {
		List<String> list = new ArrayList<String>();
		for (Section item : el) {
			LOG.trace("Section==>");
			list.addAll(extract(item));
			LOG.trace("<==Section");
		}
		return list;
	}

	public List<String> extractPatientRoles(List<PatientRole> el) {
		List<String> list = new ArrayList<String>();
		for (PatientRole item : el) {
			list.addAll(extract(item));
		}
		return list;
	}

//	public List<String> extract(ImmunizationsSection el) {
//		List<String> list = new ArrayList<String>();
//		list.add(String.format("%s.%s", el.eClass().getName(), el.getSectionId()));
//		return list;
//	}

//	public List<String> extract(List<Observation> el, int pkg) {
//		List<String> list = new ArrayList<String>();
//		for (Observation item : el) {
//			extract(item);
//		}
//		return list;
//	}

//	public List<String> extract(List<Performers> el) {
//		List<String> list = new ArrayList<String>();
//		for (Observation item : el) {
//			extract(item);
//		}
//		return list;
//	}

	public List<String> extract(Section el) {
		List<String> list = new ArrayList<String>();
		String title = el.getTitle().getText().replaceAll(" ", "_");
		LOG.trace("Section.title=" + title);
		for (Entry item : el.getEntries()) {
			List<String> list1 = extract(item);
			for (String s : list1) {
				list.add(String.format("%s.%s", title, s));
			}
		}
		return list;
	}

	public List<String> extract(Entry el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Entry==>");
			list.addAll(extract(el.getObservation()));
			list.addAll(extract(el.getEncounter()));
			list.addAll(extract(el.getProcedure()));
			list.addAll(extract(el.getRegionOfInterest()));
			list.addAll(extract(el.getAct()));
			LOG.trace("<==Entry");
		}
		return list;
	}

	public List<String> extract(Observation el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Observation==>");
			for (String s : extract(el.getCode())) {
				list.add(String.format("%s.%s", el.eClass().getName(), s));
			}
			LOG.trace("<==Observation " + list);
		}
		return list;
	}

	public List<String> extract(Encounter el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Encounter==>");
			list.addAll(extract(el.getCode()));
			list.addAll(extract(el.getPriorityCode()));
			LOG.trace("<==Encounter");
		}
		return list;
	}

	public List<String> extract(Procedure el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Procedure==>");
			list.addAll(extract(el.getCode()));
			list.addAll(extract(el.getPriorityCode()));
			LOG.trace("<==Procedure");
		}
		return list;
	}

	public List<String> extract(RegionOfInterest el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(extract(el.getCode()));
		}
		return list;
	}

	public List<String> extract(ActRelationship el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(extract((Act) el.getTarget()));
		}
		return list;
	}

	public List<String> extract(Act el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("Act==>");
			list.addAll(extract(el.getCode()));
			for (Observation el1 : el.getObservations()) {
				list.addAll(extract(el1));
			}
			for (Encounter el1 : el.getEncounters()) {
				list.addAll(extract(el1));
			}
			for (Procedure el1 : el.getProcedures()) {
				list.addAll(extract(el1));
			}
			for (EntryRelationship el1 : el.getEntryRelationships()) {
				list.addAll(extract(el1));
			}
			LOG.trace("<==Act");
		}
		return list;
	}

	public List<String> extract(EntryRelationship el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			LOG.trace("EntryRelationship==>");
			list.addAll(extract(el.getObservation()));
			list.addAll(extract(el.getEncounter()));
			list.addAll(extract(el.getProcedure()));
			LOG.trace("<==EntryRelationship");
		}
		return list;
	}

	public List<String> extract(Performer2 el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s.%s", el.eClass().getName(), el.getAssignedEntity()));
		}
		return list;
	}

	public List<String> extract(AssignedEntity el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s.%s", el.eClass().getName(), el.getIds()));
//		list.add(String.format("%s.%s", el.eClass().getName(), el.getParticipations().get(0).));
		}
		return list;
	}

	public List<String> extract(PatientRole el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			extract(el.getPatient());
			extract(el.getAddrs().get(0));
		}
		return list;
	}

	public List<String> extract(Patient el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s.%s", el.eClass().getName(), el.getRaceCode()));
			list.add(String.format("%s.%s", el.eClass().getName(), el.getAdministrativeGenderCode()));
			list.add(String.format("%s.%s", el.eClass().getName(), el.getBirthTime()));
		}
		return list;
	}

	public List<String> extract(AD el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.addAll(extract(el.getPostalCodes().get(0)));
		}
		return list;
	}

	public List<String> extract(ADXP el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getText()));
		}
		return list;
	}

	public List<String> extract(II el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s.%s", el.eClass().getName(), el.getExtension()));
		}
		return list;
	}

	public List<String> extract(TS el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getValue()));
		}
		return list;
	}

	public List<String> extract(CD el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getCode()));
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getCodeSystemName()));
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getDisplayName()));
		}
		return list;
	}

	public List<String> extract(CE el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getCode()));
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getCodeSystemName()));
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getDisplayName().replaceAll(" ", "_")));
		}
		return list;
	}

	public List<String> extract(CS el) {
		List<String> list = new ArrayList<String>();
		if (el != null) {
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getCode()));
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getCodeSystemName()));
			list.add(String.format("%s%s%s", el.eClass().getName(), DELIM, el.getDisplayName()));
		}
		return list;
	}

	public Set<String> listFiles(String dir, int depth) throws IOException {
		try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
			return stream.filter(file -> !Files.isDirectory(file)).map(Path::getFileName).map(Path::toString)
					.collect(Collectors.toSet());
		}
	}

	public static void main(String[] args) {
		try {
			CDA2D4M app = new CDA2D4M(args);
			LOG.info("Start==>");
			app.run();
			LOG.info("<==Finish");
		} catch (CmdLineException e) {
			LOG.error("Soaping is wrong.", e);
		}
	}

}
