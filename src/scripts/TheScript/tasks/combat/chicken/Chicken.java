package scripts.TheScript.tasks.combat.chicken;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.api.methods.Bank;
import scripts.TheScript.api.methods.Killing;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.enums.npcAreas;
import scripts.TheScript.variables.Variables;

public class Chicken {

	public static void doChickens() {
		if (ready()) {
			if (Killing.checkCombatStance(2)) {
				if (Methods.inArea(Variables.randomArea)) {
					Variables.miniState = "at chickens";
					if (Inventory.isFull()) {
						Variables.miniState = "walking to bank";
						Bank.walkToBank();
					} else {
						if ((Methods.pickUpGroundItem("Feather") && Combat.isUnderAttack()) == false) {
							Variables.miniState = "Killing";
							Killing.combat("Chicken", Variables.randomArea);
						}
					}
				} else if (Bank.isInBank()) {
					Variables.miniState = "in bank";
					if (Inventory.isFull()) {
						Variables.miniState = "doing bank";
						handleBank();
					} else {
						Variables.miniState = "walking to chicken area";
						Methods.walkToTile(Variables.randomAreaWalkTile);
					}

				} else {
					if (Inventory.isFull()) {
						Variables.miniState = "walking to bank";
						Bank.walkToBank();
					} else {
						Variables.miniState = "walking to chicken area";
						Methods.walkToTile(Variables.randomAreaWalkTile);
					}

				}
			}
		} else if (Variables.locations.size() > 0) {
			Methods.getRandomLocation();
		}

	}

	public static void handleChickens() {
		Variables.locations.clear();
		if (Variables.locations.size() == 0) {
			Variables.locations.put(npcAreas.CHICKENS_LUMBRIDGE_WEST.getArea(),
					npcAreas.CHICKENS_LUMBRIDGE_WEST.getWalkTile());
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
		return Variables.randomArea != null && Variables.randomAreaWalkTile != null;
	}

}
