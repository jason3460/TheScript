package scripts.TheScript.api.methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.Inventory;

import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.variables.Variables;

public class Gear {

	public static boolean isAllEquiped(String[] names) {
		for (String s : names) {
			int i = 0;
			if (i != names.length - 1 && !Equipment.isEquipped(s)) {
				return false;
			}
		}
		return true;
	}

	static boolean haveItem(String name) {
		return Inventory.getCount(name) > 0 || Equipment.isEquipped(name);
	}

	public static boolean haveItems(String[] names) {
		for (String s : names) {
			int i = 0;
			if (i != names.length - 1 && haveItem(s)) {
				return Inventory.getCount(s) > 0;
			}
		}
		return false;
	}

	public static void getGear(String[] names) {
		Variables.miniState = "Getting Task Gear";

		if (!haveItems(names)) {
			if (Bank.isInBank()) {
				for (String s : names) {
					int i = 0;
					if (i != names.length - 1 && !haveItem(s)) {
						final int count = Inventory.getAll().length;
						Bank.withdrawItem(1, s);
						Timing.waitCondition((Conditions.get().inventoryCount(count)), General.random(4000, 6000));
					}
				}
			} else {
				Bank.walkToBank();
			}
		} else {
			equipGear(names);
		}
	}

	public static void equipGear(String[] names) {

		Variables.miniState = "equipping gear";

		if (!Banking.isBankScreenOpen()) {
			if (GameTab.getOpen().equals(TABS.INVENTORY)) {
				for (String s : names) {
					if (Inventory.find(s).length > 0) {
						Inventory.find(s)[0].click("W");
						Timing.waitCondition((Conditions.get().dontHaveItem(s)), General.random(4000, 6000));
					}
				}

			} else {
				GameTab.open(TABS.INVENTORY);
			}
		} else {
			Banking.close();
		}
	}
}
