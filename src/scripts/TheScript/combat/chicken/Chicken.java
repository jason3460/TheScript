package scripts.TheScript.combat.chicken;

import java.util.LinkedHashMap;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.api.methods.Bank;
import scripts.TheScript.api.methods.Killing;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.enums.npcAreas;
import scripts.TheScript.variables.Variables;

public class Chicken {

	public static RSArea randomArea = null;
	public static RSTile randomAreaWalkTile = null;
	public static LinkedHashMap<RSArea, RSTile> locations = new LinkedHashMap<RSArea, RSTile>();

	private static void getRandomLocation() {
		Methods.debug("Getting random area");
		int random = General.random(0, locations.size() - 1);
		RSArea[] areas = locations.keySet().toArray(new RSArea[locations.size()]);
		randomArea = areas[random];
		if (randomArea != null)
			randomAreaWalkTile = locations.get(randomArea);
	}

	public static void doChickens() {
		if (ready()) {
			Methods.debug("ready is true");
			if (Killing.checkCombatStance(2)) {
				if (Methods.inArea(randomArea)) {
					Variables.miniState = "at chickens";
					if (Inventory.isFull()) {
						Variables.miniState = "walking to bank";
						Bank.walkToBank();
					} else {
						if ((Methods.pickUpGroundItem("Feather") && Combat.isUnderAttack()) == false) {
							Variables.miniState = "Killing";
							Killing.combat("Chicken");
						}
					}
				} else if (Bank.isInBank()) {
					Variables.miniState = "in bank";
					if (Inventory.isFull()) {
						Variables.miniState = "doing bank";
						handleBank();
					} else {
						Variables.miniState = "walking to chicken area";
						Methods.walkToTile(randomAreaWalkTile);
					}

				} else {
					if (Inventory.isFull()) {
						Variables.miniState = "walking to bank";
						Bank.walkToBank();
					} else {
						Variables.miniState = "walking to chicken area";
						Methods.walkToTile(randomAreaWalkTile);
					}

				}
			}
		} else if (locations.size() > 0) {
			Methods.debug("getting random location");
			getRandomLocation();
		}

	}

	public static void handleChickens() {
		if (locations.size() == 0) {
			locations.put(npcAreas.CHICKENS_LUMBRIDGE_WEST.getArea(), npcAreas.CHICKENS_LUMBRIDGE_WEST.getWalkTile());
			Methods.debug("Adding chicken areas");
		}
		doChickens();
	}

	private static void handleBank() {
		if (Banking.isBankScreenOpen()) {
			Banking.depositAll();
			Timing.waitCondition(Conditions.get().inventoryEmpty(), General.random(4000, 6000));
		} else {
			if (Banking.openBank()) {
				Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 6000));
			}
		}
		Antiban.generateTrackers(Antiban.getWaitingTime());
	}

	private static boolean ready() {
		return randomArea != null && randomAreaWalkTile != null;
	}

}
