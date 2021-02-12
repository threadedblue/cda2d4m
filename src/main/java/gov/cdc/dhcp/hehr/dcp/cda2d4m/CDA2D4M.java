package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.openhealthtools.mdht.uml.cda.ClinicalDocument;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CDA2D4M implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(CDA2D4M.class);

	private CmdLineParser CLI = new CmdLineParser(this);

	@Option(name = "-i", aliases = "--input", required = true, usage = "Path to the input directory or file.")
	private String input;

	@Option(name = "-c", aliases = "--config", required = false, usage = "Path to hadoop config directory.")
	private String configFilePath = "/usr/local/hadoop/etc/hadoop";

	@Option(name = "-a", aliases = "--instance", required = false, usage = "Name of Accumulo instance.")
	private String accumuloInstance;

	@Option(name = "-zk", aliases = "--zookeeper", required = true, usage = "URL to zookeeper instance as a string.")
	private String zookeeperURI;

	@Option(name = "-ow", aliases = "--overwrite", required = false, usage = "Overwrite output if exists.")
	private boolean overwrite;

	@Option(name = "-fo", aliases = "--fileout", required = false, usage = "Output to a file no DB")
	private boolean fileOut;

	@Option(name = "-r", aliases = "--recurse", required = false, usage = "Set to reacuse throught directories. default = false;")
	private boolean recurse;

	@Option(name = "-o", aliases = "--output", required = true, usage = "Base name of the table set for D4M.")
	private String output;

	AccumuloComponent acc;
	CDA2D4MSwitch ccdSwitch;

	public CDA2D4M() {
	}

	public CDA2D4M(String[] args) throws CmdLineException {
		super();
		try {
			CLI.parseArgument(args);
//			acc = new AccumuloComponent(input, output, configFilePath, accumuloInstance, zookeeperURI, overwrite,
//					recurse);
			ccdSwitch = new CDA2D4MSwitch();
		} catch (CmdLineException e) {
			LOG.error("", e);
		}
	}

	public void run() {

		int cnt = 0;
//		if (fileOut) {
//			fileOut(input);
//			return;
//		}
//		if (!acc.connect()) {
//			return;
//		}
//		acc.initAccumulo();
		try {
			Set<String> files = listFiles(input, 1);
			boolean init = true;
			for (String xmlFileName : files) {
				LOG.info("xmlFileName=" + xmlFileName);
				Path fileIn = Paths.get(input, xmlFileName);
				InputStream is = Files.newInputStream(fileIn, StandardOpenOption.READ);
				ClinicalDocument cda = CDAUtil.load(is);
				List<String> listWithDuplicates = ccdSwitch.doSwitch(cda);
			    List<String> listWithoutDuplicates = listWithDuplicates.stream()
			    	     .distinct()
			    	     .collect(Collectors.toList());
				if (init) {
					init = false;
				}
				LOG.info("before=" + listWithDuplicates.size());
				LOG.info("after=" + listWithoutDuplicates.size());
				LOG.info("delta=" + (listWithDuplicates.size() - listWithoutDuplicates.size()));
//				acc.doList(cols);
				Path fileOut = Paths.get(input, "d4m");
				Files.createDirectories(Paths.get(fileOut.toUri()));
				
				String[] ss = xmlFileName.split("\\.");
				String d4mFileName = ss[0] + ".d4m";
				BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileOut.toFile(), d4mFileName)));
				for (String line : listWithoutDuplicates) {
					LOG.trace(line);
					writer.append(line + System.lineSeparator());
				}
				writer.close();
				cnt++;
				break;
			}
		} catch (IOException e) {
			LOG.error("IO==>", e);
		} catch (Exception e) {
			LOG.error("Ex==>", e);
		}
	}
	
//	List<String> doIndexing(List<String> list) {
//		final int KEY = 0;
//		final int VALUE = 1;
//		String insert = null;
//		String prev = list.get(0).split(Switch.VALUE_DELIM)[KEY];
//		String curr = null;
//		int k = 1;
//		for (int i = 1; i < list.size(); i++) {
//			String s = list.get(i);
//			String[] ss = s.split(Switch.VALUE_DELIM);
//			curr = ss[KEY];
//			if (prev.equals(curr)) {
//				insert = String.format("%s[%02d]%s%s", prev, k, Switch.VALUE_DELIM, ss[VALUE]);	
//				k++;
//			} else {
//				insert = String.format("%s%s%s", prev, Switch.VALUE_DELIM, ss[VALUE]);
//				k = 1;
//			}
//			list.set(i - 1, insert);
//			prev = curr;
//		}
//		return list;
//	}

	public void fileOut(String file) {
		try {
			Set<String> files = listFiles(input, 1);
			Object[] oo = files.toArray();
			Path fileIn = Paths.get(input, oo[0].toString());
			InputStream is = Files.newInputStream(fileIn, StandardOpenOption.READ);
			ClinicalDocument cda = CDAUtil.load(is);
			Path here = Path.of("out.txt");
			Files.deleteIfExists(here);
			Files.createFile(here);
//			for (String s : cols) {
//				Files.writeString(here, s + System.lineSeparator(), StandardOpenOption.APPEND);
//			}
		} catch (IOException e) {
			LOG.error("IO==>", e);
		} catch (Exception e) {
			LOG.error("Ex==>", e);
		}
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
