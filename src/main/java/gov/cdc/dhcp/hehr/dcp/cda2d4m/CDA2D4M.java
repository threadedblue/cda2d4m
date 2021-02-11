package gov.cdc.dhcp.hehr.dcp.cda2d4m;

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
			for (String file : files) {
				LOG.trace("file=" + file);
				Path fileIn = Paths.get(input, file);
				InputStream is = Files.newInputStream(fileIn, StandardOpenOption.READ);
				ClinicalDocument cda = CDAUtil.load(is);
//				ContinuityOfCareDocument ccd = (ContinuityOfCareDocument) cda;
				List<String> cols = ccdSwitch.doSwitch(cda);
				if (init) {
					init = false;
				}
//				acc.doList(cols);
				LOG.info("cols=" + cols.toString());
				cnt++;
				break;
			}
		} catch (IOException e) {
			LOG.error("IO==>", e);
		} catch (Exception e) {
			LOG.error("Ex==>", e);
		}
	}

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
