package scripts.TheScript.api.antiban;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.interfaces.Positionable;
import org.tribot.api.util.abc.ABCProperties;
import org.tribot.api.util.abc.ABCUtil;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Game;
import org.tribot.api2007.Options;

import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.variables.Variables;

public final class Antiban {

	private static final ABCUtil abc;

	private static int resources_won;

	private static int resources_lost;

	public static int run_at;

	public static int eat_at;

	public static boolean should_hover;

	public static boolean should_open_menu;

	public static long last_under_attack_time;

	static {
		abc = new ABCUtil();
		resources_won = 0;
		resources_lost = 0;
		run_at = abc.generateRunActivation();
		eat_at = abc.generateEatAtHP();
		should_hover = abc.shouldHover();
		should_open_menu = abc.shouldOpenMenu() && abc.shouldHover();
		last_under_attack_time = 0;
		General.useAntiBanCompliance(true);
	}

	private Antiban() {
	}

	public static void destroy() {
		abc.close();
	}

	public static ABCUtil getABCUtil() {
		return abc;
	}

	public static ABCProperties getProperties() {
		return getABCUtil().getProperties();
	}

	public static int getWaitingTime() {
		return getProperties().getWaitingTime();
	}

	public static int getReactionTime() {
		resetShouldHover();
		resetShouldOpenMenu();

		ABCProperties properties = getProperties();

		properties.setWaitingTime(getWaitingTime());
		properties.setHovering(should_hover);
		properties.setMenuOpen(should_open_menu);
		properties
				.setUnderAttack(Combat.isUnderAttack() || (Timing.currentTimeMillis() - last_under_attack_time < 2000));
		properties.setWaitingFixed(false);

		return getABCUtil().generateReactionTime();
	}

	public static int getResourcesWon() {
		return resources_won;
	}

	public static int getResourcesLost() {
		return resources_lost;
	}

	public static void setResourcesWon(int amount) {
		resources_won = amount;
	}

	public static void setResourcesLost(int amount) {
		resources_lost = amount;
	}

	public static void incrementResourcesWon() {
		resources_won++;
	}

	public static void incrementResourcesLost() {
		resources_lost++;
	}

	public static void setLastUnderAttackTime(long time_stamp) {
		last_under_attack_time = time_stamp;
	}

	public static void sleepReactionTime() {
		final int reaction_time = getReactionTime();
		if (Variables.printDebug) {
			Methods.debugABC2("Reaction time: " + reaction_time + "ms.");
		}
		try {
			getABCUtil().sleep(reaction_time);
		} catch (@SuppressWarnings("unused") InterruptedException e) {
			Methods.debugABC2("Background thread interrupted sleep");
		}
	}

	/**
	 * Generates the trackers for ABCUtil. Call this only after successfully
	 * completing an action that has a dynamic wait time for the next action.
	 *
	 * @param estimated_wait
	 *            The estimated wait time (in milliseconds) before the next
	 *            action occurs.
	 */
	public static void generateTrackers(int estimated_wait) {
		final ABCProperties properties = getProperties();

		properties.setWaitingTime(estimated_wait);
		properties.setUnderAttack(false);
		properties.setWaitingFixed(false);

		getABCUtil().generateTrackers();
	}

	/**
	 * Resets the should_hover bool to match the ABCUtil value. This method
	 * should be called after successfully clicking an entity.
	 */
	public static void resetShouldHover() {
		should_hover = getABCUtil().shouldHover();
	}

	/**
	 * Resets the should_open_menu bool to match the ABCUtil value. This method
	 * should be called after successfully clicking an entity.
	 */
	public static void resetShouldOpenMenu() {
		should_open_menu = getABCUtil().shouldOpenMenu() && getABCUtil().shouldHover();
	}

	/**
	 * Randomly moves the camera. Happens only if the time tracker for camera
	 * movement is ready.
	 *
	 * @return True if the action was performed, false otherwise.
	 */
	public static boolean moveCamera() {
		if (getABCUtil().shouldRotateCamera()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Rotated camera");
			}
			getABCUtil().rotateCamera();
			return true;
		}
		return false;
	}

	/**
	 * Checks the exp of the skill being trained. Happens only if the time
	 * tracker for checking exp is ready.
	 *
	 * @return True if the exp was checked, false otherwise.
	 */
	public static boolean checkXp() {
		if (getABCUtil().shouldCheckXP()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Checked xp");
			}
			getABCUtil().checkXP();
			return true;
		}
		return false;
	}

	/**
	 * Picks up the mouse. Happens only if the time tracker for picking up the
	 * mouse is ready.
	 *
	 * @return True if the mouse was picked up, false otherwise.
	 */
	public static boolean pickUpMouse() {
		if (getABCUtil().shouldPickupMouse()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Picked up mouse");
			}
			getABCUtil().pickupMouse();
			return true;
		}
		return false;
	}

	/**
	 * Navigates the mouse off game window and mimics de-focusing the window.
	 * Happens only if the time tracker for leaving the game is ready.
	 *
	 * @return True if the mouse left the game window, false otherwise.
	 */
	public static boolean leaveGame() {
		if (getABCUtil().shouldLeaveGame()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Left game window");
			}
			getABCUtil().leaveGame();
			return true;
		}
		return false;
	}

	/**
	 * Examines an entity near your player. Happens only if the time tracker for
	 * examining an entity is ready.
	 *
	 * @return True if an entity was examined, false otherwise.
	 */
	public static boolean examineEntity() {
		if (getABCUtil().shouldExamineEntity()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Examined entity");
			}
			getABCUtil().examineEntity();
			return true;
		}
		return false;
	}

	/**
	 * Right clicks the mouse. Happens only if the time tracker for right
	 * clicking the mouse is ready.
	 *
	 * @return True if a random spot was right clicked, false otherwise.
	 */
	public static boolean rightClick() {
		if (getABCUtil().shouldRightClick()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Right clicked");
			}
			getABCUtil().rightClick();
			return true;
		}
		return false;
	}

	/**
	 * Moves the mouse. Happens only if the time tracker for moving the mouse is
	 * ready.
	 *
	 * @return True if the mouse was moved to a random point, false otherwise.
	 */
	public static boolean mouseMovement() {
		if (getABCUtil().shouldMoveMouse()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Mouse moved");
			}
			getABCUtil().moveMouse();
			return true;
		}
		return false;
	}

	/**
	 * Opens up a game tab. Happens only if the time tracker for tab checking is
	 * ready.
	 *
	 * @return True if the combat tab was checked, false otherwise.
	 */
	public static boolean checkTabs() {
		if (getABCUtil().shouldCheckTabs()) {
			if (Variables.printDebug) {
				Methods.debugABC2("Tab checked");
			}
			getABCUtil().checkTabs();
		}
		return false;
	}

	/**
	 * Checks all of the actions that are performed with the time tracker; if
	 * any are ready, they will be performed.
	 */
	public static void timedActions() {
		moveCamera();
		checkXp();
		pickUpMouse();
		leaveGame();
		examineEntity();
		rightClick();
		mouseMovement();
		checkTabs();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Positionable> T selectNextTarget(T[] targets) {
		return (T) getABCUtil().selectNextTarget(targets);
	}

	public static boolean activateRun() {
		if (Game.getRunEnergy() >= run_at && !Game.isRunOn()) {
			if (Options.setRunOn(true)) {
				if (Variables.printDebug) {
					Methods.debugABC2("Turned run on at " + run_at + "%");
				}
				run_at = getABCUtil().generateRunActivation();
				return true;
			}
		}
		return false;
	}

	public static boolean shouldSwitchResources(int player_count) {
		double win_percent = ((double) (resources_won + resources_lost) / (double) resources_won);
		return win_percent < 50.0 && getABCUtil().shouldSwitchResources(player_count);
	}

}