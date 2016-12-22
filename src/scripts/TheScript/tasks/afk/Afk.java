package scripts.TheScript.tasks.afk;

import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.variables.Variables;

public class Afk {

	public static void doAfk() {
		Variables.miniState = "AFK";

		if (Variables.printDebug) {
			Methods.debug("Getting new task, need to write code for Afk");
		}
		Variables.timer.endTimer();
	}

	public static void handleAfk() {

	}

}
