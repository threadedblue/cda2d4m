package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import org.openhealthtools.mdht.uml.cda.Act;

public interface Switch<T> {

	public static final String PATH_DELIM = ".";

	public static final String VALUE_DELIM = ":";

	T caseAct(Act act);
}
