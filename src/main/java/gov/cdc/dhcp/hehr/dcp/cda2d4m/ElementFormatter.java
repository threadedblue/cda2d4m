package gov.cdc.dhcp.hehr.dcp.cda2d4m;

public class ElementFormatter {

	private static int i;
	
	public static final String ELEMENT_FORMATTER = "[%02d]";	
	
	private static ElementFormatter INSTANCE;
	
	public enum MODE {RESET, STOP, START};
	
	static MODE mode = MODE.START;
	
	public static ElementFormatter get() {
		if (INSTANCE == null) {
			INSTANCE = new ElementFormatter();
		}
		return INSTANCE;
	}
	
	public static String nextElement() {
		String s =  String.format(ELEMENT_FORMATTER, i + 1);
		i++;
		return s;
	}
	
	public static MODE getMode() {
		return mode;
	}
	
	public static void setMode(MODE mode1) {
		switch(mode1) {
		case RESET:
			i = 0;
			mode = MODE.START;
			break;
		case STOP:
			mode = mode1;
			break;
		case START:
			mode = mode1;
			i = 0;
			break;
		}
	}
}
