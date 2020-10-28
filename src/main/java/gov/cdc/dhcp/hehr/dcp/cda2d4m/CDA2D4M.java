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

	AccumuloComponent acc;
	CDA2AACols flat;

	public CDA2D4M() {
	}

	public CDA2D4M(String[] args) throws CmdLineException {
		super();
		try {
			CLI.parseArgument(args);
			acc = new AccumuloComponent(input, output, configFilePath, accumuloInstance, zookeeperURI, overwrite,
					recurse);
			flat = new CDA2AACols();
		} catch (CmdLineException e) {
			LOG.error("", e);
		}
	}

	public void run() {
		int cnt = 0;
		if(!acc.connect()) {
			return;
		}
		acc.initAccumulo();
		try {
			Set<String> files = listFiles(input, 1);
			for (String file : files) {
				Path fileIn = Paths.get(input, file);
				InputStream is = Files.newInputStream(fileIn, StandardOpenOption.READ);
				ClinicalDocument cda = CDAUtil.load(is);
				List<String> cols = flat.build(cda);
				acc.doList(cols);
				cnt++;
			}
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
