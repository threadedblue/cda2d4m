package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;

import org.apache.accumulo.core.client.Accumulo;
import org.apache.accumulo.core.client.AccumuloClient;
import org.apache.hadoop.io.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.mit.ll.graphulo.util.D4MTableWriter;
import edu.mit.ll.graphulo.util.D4MTableWriter.D4MTableConfig;

public class AccumuloComponent {

	private static final Logger LOG = LoggerFactory.getLogger(AccumuloComponent.class);

	public static final String ACCUMULO_CREDS_FILE = "accumulo-creds.yml";

	private String input;
	private String output;
	private String configFilePath = "/usr/local/hadoop/etc/hadoop";
	private String accumuloInstance;
	private String zookeeperURI;
	private boolean overwrite;
	private boolean recurse;

	AccumuloClient conn;
	D4MTableWriter writer;

	public AccumuloComponent(String input, String output, String configFilePath, String accumuloInstance,
			String zookeeperURI, boolean overwrite, boolean recurse) {
		super();
		this.input = input;
		this.output = output;
		this.configFilePath = configFilePath;
		this.accumuloInstance = accumuloInstance;
		this.zookeeperURI = zookeeperURI;
		this.overwrite = overwrite;
		this.recurse = recurse;
	}

	public boolean connect() {
		URL credsFile = getClass().getClassLoader().getResource(ACCUMULO_CREDS_FILE);
		LOG.debug("credsFile=" + credsFile);
		Properties props = new Properties();
		props.put("instance.name", accumuloInstance);
		props.put("instance.zookeepers", zookeeperURI);
		props.put("auth.type", "password");
		props.put("auth.principal", "gcr");
		props.put("auth.token", "$Hello1Hello1$");
		conn = Accumulo.newClient().from(props).build();
		return (conn != null);
	}

	public void initAccumulo() {
		LOG.debug("input=" + input);
		try {

			if (overwrite) {
				LOG.info("Overwrite was set.  Delete/create tables.");
				if (conn.tableOperations().exists(output)) {
					LOG.info("  Deleting " + output);
					conn.tableOperations().delete(output);
				}
				if (conn.tableOperations().exists(output + "T")) {
					LOG.info("  Deleting " + output + "T");
					conn.tableOperations().delete(output + "T");
				}
				if (conn.tableOperations().exists(output + "Deg")) {
					LOG.info("  Deleting " + output + "Deg");
					conn.tableOperations().delete(output + "Deg");
				}
				if (conn.tableOperations().exists(output + "DegT")) {
					LOG.info("  Deleting " + output + "DegT");
					conn.tableOperations().delete(output + "DegT");
				}
			}
			if (!conn.tableOperations().exists(output)) {
				LOG.info("  Creating " + output);
				conn.tableOperations().create(output);
			}
			if (!conn.tableOperations().exists(output + "T")) {
				LOG.info("  Creating " + output + "T");
				conn.tableOperations().create(output + "T");
			}
			if (!conn.tableOperations().exists(output + "Deg")) {
				LOG.info("  Creating " + output + "Deg");
				conn.tableOperations().create(output + "Deg");
			}
			if (!conn.tableOperations().exists(output + "DegT")) {
				LOG.info("  Creating " + output + "DegT");
				conn.tableOperations().create(output + "DegT");
			}
			LOG.debug("<==waitForCompletion");
		} catch (NullPointerException e) {
			LOG.error("", e.fillInStackTrace());
		} catch (Exception e) {
			LOG.error("", e.fillInStackTrace());
		}
		D4MTableConfig tconf = new D4MTableConfig();
		tconf.baseName = output;
		tconf.connector = conn;
		tconf.useTable = true;
		tconf.useTableT = true;
		tconf.useTableDeg = true;
		tconf.useTableDegT = true;
		writer = new D4MTableWriter(tconf);
	}

	public void doList(List<String> cols) {

		Path pathRoot = Path.of(input);

		Path pathInput = Path.of(input);
		LOG.info("pathInput=" + pathInput.toString());

		String row = null;
		for(String col : cols) {
			if (row == null) {
				String[] ss = col.split(CDA2AACols.VALUE_DELIM);
				row = ss[1];
			}
			writer.ingestRow(new Text(row), new Text(col));
		}

	}
}
