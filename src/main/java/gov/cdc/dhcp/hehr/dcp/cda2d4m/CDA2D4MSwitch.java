package gov.cdc.dhcp.hehr.dcp.cda2d4m;

import org.openhealthtools.mdht.uml.cda.Encounter;
import org.openhealthtools.mdht.uml.cda.Observation;
import org.openhealthtools.mdht.uml.cda.Procedure;
import org.openhealthtools.mdht.uml.cda.Section;
import org.openhealthtools.mdht.uml.cda.ccd.ImmunizationsSection;
import org.openhealthtools.mdht.uml.cda.ccd.MedicationActivity;
import org.openhealthtools.mdht.uml.cda.ccd.ProblemSection;
import org.openhealthtools.mdht.uml.cda.ccd.util.CCDSwitch;

public class CDA2D4MSwitch<T> extends CCDSwitch<T> {

	@Override
	public T caseProblemSection(ProblemSection object) {
		// TODO Auto-generated method stub
		return super.caseProblemSection(object);
	}

	@Override
	public T caseImmunizationsSection(ImmunizationsSection object) {
		// TODO Auto-generated method stub
		return super.caseImmunizationsSection(object);
	}

	@Override
	public T caseMedicationActivity(MedicationActivity object) {
		// TODO Auto-generated method stub
		return super.caseMedicationActivity(object);
	}

	@Override
	public T caseSection(Section object) {
		// TODO Auto-generated method stub
		return super.caseSection(object);
	}

	@Override
	public T caseObservation(Observation object) {
		// TODO Auto-generated method stub
		return super.caseObservation(object);
	}

	@Override
	public T caseEncounter(Encounter object) {
		// TODO Auto-generated method stub
		return super.caseEncounter(object);
	}

	@Override
	public T caseProcedure(Procedure object) {
		// TODO Auto-generated method stub
		return super.caseProcedure(object);
	}

	
}
