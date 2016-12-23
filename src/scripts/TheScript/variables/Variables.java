package scripts.TheScript.variables;

import java.security.SecureRandom;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.api.methods.Timer;
import scripts.TheScript.enums.ScriptState;

public class Variables {

	public static boolean printDebug = true;

	public static boolean GUI_COMPLETE = false;

	public static boolean runScript;

	public static ScriptState SCRIPT_STATE;

	public static boolean getNewTask = true;
	public static int tasksComplete = 0;

	public static String miniState = "";

	public static final SecureRandom random = new SecureRandom();

	public static Timer timer;
	
	// ----- . ----- \\

	// ----- . ----- \\

	// ----- BANKING ----- \\
	public static boolean initialBank = false;

	// ----- CHICKENS ----- \\

	// ----- WOODCUTTING ----- \\
	public static boolean doPowerChop;
	public static String logName;
	public static String treeName;

	// ----- Random Locations ----- \\
	public static RSArea randomArea = null;
	public static RSTile randomAreaWalkTile = null;

	public static int count = 0;
	public static int before = 0;

}
