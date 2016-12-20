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
		for (String name : names) {
			int i = 0;
			if (i != names.length - 1 && !Equipment.isEquipped(name)) {
				return false;
			}
		}
		return true;
	}

	private static boolean haveItem(String name) {
		return Inventory.getCount(name) > 0 || Equipment.isEquipped(name);
	}

	private static boolean haveItems(String[] names) {
		for (String name : names) {
			int i = 0;
			if (i != names.length - 1 && haveItem(name)) {
				return Inventory.getCount(name) > 0;
			}
		}
		return false;
	}

	public static boolean getGear(String[] names) {
		Variables.miniState = "Getting Task Gear";

		if (!haveItems(names) || isAllEquiped(names)) {
			if (Bank.isInBank()) {
				for (String name : names) {
					Methods.debug(name);
					Bank.withdrawItem(1, name);
				}
			} else {
				Bank.walkToBank();
			}
		} else {
			if (equipGear(names)) {
				return true;
			}

		}
		return false;
	}

	public static boolean equipGear(String[] names) {

		Variables.miniState = "equipping gear";

		if (!isAllEquiped(names)) {
			if (!Banking.isBankScreenOpen()) {
				if (GameTab.getOpen().equals(TABS.INVENTORY)) {
					for (String name : names) {
						if (Inventory.find(name).length > 0) {
							Inventory.find(name)[0].click("W");
							Timing.waitCondition((Conditions.get().dontHaveItem(name)), General.random(4000, 6000));
						}
					}
				} else {
					GameTab.open(TABS.INVENTORY);
				}
			} else {
				Banking.close();
			}
		} else {
			return true;
		}

		return false;
	}
}
