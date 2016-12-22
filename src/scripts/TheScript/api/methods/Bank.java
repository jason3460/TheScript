package scripts.TheScript.api.methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.variables.Variables;

public class Bank {

	public static boolean walkToBank() {
		if (!WebWalking.walkToBank()) {
			return false;
		}
		return Timing.waitCondition(new Condition() {
			@Override
			public boolean active() {
				General.sleep(200, 300);
				return isInBank();
			}
		}, General.random(8000, 9000));
	}

	public static boolean isInBank() {
		final RSObject[] booths = Objects.findNearest(20, "Bank booth");
		if (booths.length > 1) {
			if (booths[0].isOnScreen())
				return true;
		}

		final RSNPC[] bankers = NPCs.findNearest("Banker");
		if (bankers.length < 1)
			return false;

		return bankers[0].isOnScreen();
	}

	public static boolean depositAllInventory() {
		if (!Banking.isBankScreenOpen()) {
			if (Banking.openBank()) {
				Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 7000));
			}
		} else {
			General.sleep(500, 800);
			if (Banking.depositAll() > 0) {
				Timing.waitCondition(Conditions.get().inventoryEmpty(), General.random(4000, 7000));
				return true;
			}
		}
		return false;
	}

	public static boolean depositAllEquipment() {
		if (!Banking.isBankScreenOpen()) {
			if (Banking.openBank()) {
				Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 7000));
			}
		} else {
			General.sleep(500, 800);
			if (Banking.depositEquipment()) {
				Timing.waitCondition(Conditions.get().equipmentEmpty(), General.random(4000, 7000));
				return true;
			}
		}
		return false;
	}

	public static boolean withdrawItem(int amount, String itemName) {
		if (!Banking.isBankScreenOpen()) {
			if (Banking.openBank()) {
				Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 7000));
			}
		} else {
			General.sleep(500, 800);
			RSItem[] item = Banking.find(itemName);
			if (item.length == 0) {
				General.println("Ending Script. Reason: do not have required items: " + itemName);
				Variables.runScript = false;
				return false;
			}
			withdraw(amount, itemName);
			Timing.waitCondition(Conditions.get().haveItem(itemName, amount), General.random(4000, 6000));
		}
		return false;
	}

	private static void withdraw(int amount, String itemName) {
		if (!Inventory.isFull()) {
			if (Banking.withdraw(amount, itemName)) {
				Timing.waitCondition(Conditions.get().haveItem(itemName, amount), General.random(4000, 7000));
			}
		} else {
			Methods.debug("Inventory is full.");
		}

	}

	// private static int getCurrentBankSpace() {
	// RSInterface amount = Interfaces.get(12, 5);
	// if (amount != null && !amount.isHidden()) {
	// String text = amount.getText();
	// if (text != null) {
	// try {
	// int parse = Integer.parseInt(text);
	// if (parse > 0)
	// return parse;
	// } catch (@SuppressWarnings("unused") NumberFormatException e) {
	// return -1;
	// }
	// }
	// }
	// return -1;
	// }
	//
	// private static boolean isBankItemsLoaded() {
	// return getCurrentBankSpace() == Banking.getAll().length;
	// }

}
