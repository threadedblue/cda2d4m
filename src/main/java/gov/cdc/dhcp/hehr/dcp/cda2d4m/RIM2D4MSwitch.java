package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.openhealthtools.mdht.uml.cda.Act;
import org.openhealthtools.mdht.uml.hl7.datatypes.CD;
import org.openhealthtools.mdht.uml.hl7.datatypes.CS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ED;
import org.openhealthtools.mdht.uml.hl7.datatypes.II;
import org.openhealthtools.mdht.uml.hl7.datatypes.IVXB_TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.ST;
import org.openhealthtools.mdht.uml.hl7.datatypes.TS;
import org.openhealthtools.mdht.uml.hl7.datatypes.util.DatatypesSwitch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RIM2D4MSwitch extends DatatypesSwitch<List<String>> implements Switch {

	private static final Logger LOG = LoggerFactory.getLogger(RIM2D4MSwitch.class);

	String temp;

	@Override
	public List<String> caseCD(CD cd) {
		List<String> list = new ArrayList<String>();
		if (cd != null) {
			list.addAll(checkFirst(cd));
		}
		return list;
	}
	
	public List<String> checkFirst(CD cd) {

		List<String> list = new ArrayList<String>();
		if (cd != null) {
			if (cd.getCode() != null) {
				list.add(String.format("%s%s%s", "code", VALUE_DELIM, fillInTheBlanks(cd.getCode())));
			}
			if (cd.getCodeSystemName() != null) {
				list.add(String.format("%s%s%s", "codeSystem", VALUE_DELIM, fillInTheBlanks(cd.getCodeSystemName())));
			}
			if (cd.getDisplayName() != null) {
				list.add(String.format("%s%s%s", "codeDisplay", VALUE_DELIM, fillInTheBlanks(cd.getDisplayName())));
			}
		}
		return list;
	}
	
	@Override
	public List<String> caseCS(CS cs) {
		List<String> list = new ArrayList<String>();
		if (cs != null) {
			list.addAll(checkFirst(cs));
		}
		return list;
	}

	@Override
	public List<String> caseED(ED ed) {
		List<String> list = new ArrayList<String>();
		if (ed != null) {
			LOG.trace("ed.getText()=" + ed.getReference().getValue());
			list.add(String.format("text%s%s", Switch.VALUE_DELIM, ed.getReference().getValue()));
		}
		return list;
	}

	public List<String> caseIIs(EList<II> iis) {
		List<String> list = new ArrayList<String>();
		for (II ii : iis) {
			this.doSwitch(ii);
		}
		return list;
	}
	
	@Override
	public List<String> caseII(II ii) {
		List<String> list = new ArrayList<String>();
		if (ii != null) {
			list.add(String.format("%s%s%s", "id", VALUE_DELIM, ii.getExtension()));
		}
		return list;
	}

	@Override
	public List<String> caseIVXB_TS(IVXB_TS ts) {
		List<String> list = new ArrayList<String>();
		if (ts.getValue() != null) {
			LOG.trace("effectiveTime-LH==>" + getTemp() + "=" + ts.getValue());
			list.add(String.format("%s%s%s", getTemp(), VALUE_DELIM, ts.getValue()));
		}
		return list;
	}

	@Override
	public List<String> caseST(ST object) {
		return super.caseST(object);
	}

	@Override
	public List<String> caseTS(TS ts) {
		List<String> list = new ArrayList<String>();
		if (ts.getValue() != null) {
			LOG.trace("effectiveTime==>" + getTemp() + "=" + ts.getValue());
			list.add(String.format("%s%s%s", getTemp(), VALUE_DELIM, ts.getValue()));
		}
		return list;
	}

	public String getTemp() {
		// Can't tell if we need this or not.
//		if (temp == null) {
//			return "unk";
//		}
//		String rval = temp;
//		temp = null;
//		return rval;
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	public String fillInTheBlanks(String s) {
		return s.replaceAll(" ", "_");
	}
	
	@Override
	public Object caseAct(Act act) {
		// TODO Auto-generated method stub
		return null;
	}
}
