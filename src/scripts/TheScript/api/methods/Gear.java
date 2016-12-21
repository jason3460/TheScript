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

	public static boolean isAllEquipped(String[] names) {
		for (int i = 0; i <= names.length - 1; i++) { //String name : names
			if (i != names.length - 1 && !Equipment.isEquipped(names[i])) {
				return false;
			}
		}
		return true;
	}

	private static boolean haveItem(String name) {
		return Inventory.getCount(name) > 0 || Equipment.isEquipped(name);
	}

	private static boolean haveItems(String[] names) {
		for (int i = 0; i <= names.length - 1; i++) {
			if (i != names.length - 1 && haveItem(names[i])) {
				return Inventory.getCount(names[i]) > 0;
			}
		}
		return false;
	}

	public static boolean getTaskGear(String[] names) {
		Variables.miniState = "Getting Task Gear";

		if (!haveItems(names)) {
			if (Bank.isInBank()) {
				if(Banking.isBankScreenOpen()){
					for (int i = 0; i <= names.length - 1; i++) {
					Bank.withdrawItem(1, names[i]);
				}
				} else {
					Banking.openBank();
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

		if (!isAllEquipped(names)) {
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
