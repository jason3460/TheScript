package scripts.TheScript.tasks.combat;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.api.methods.Bank;
import scripts.TheScript.api.methods.Gear;
import scripts.TheScript.api.methods.Killing;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.enums.Monster;
import scripts.TheScript.variables.Variables;

public class Combat {

	private static Monster getMonster() {
		int attLevel = Methods.getLevel(SKILLS.ATTACK);
		int strLevel = Methods.getLevel(SKILLS.STRENGTH);
		int defLevel = Methods.getLevel(SKILLS.DEFENCE);

		if ((attLevel & strLevel & defLevel) < 10)
			return Monster.CHICKEN;
		if ((attLevel & strLevel & defLevel) >= 10 && (attLevel & strLevel & defLevel) < 20)
			return Monster.CHICKEN;
		if ((attLevel & strLevel & defLevel) >= 20 && (attLevel & strLevel & defLevel) < 30)
			return Monster.CHICKEN;
		if ((attLevel & strLevel & defLevel) >= 30 && (attLevel & strLevel & defLevel) < 40)
			return Monster.CHICKEN;
		return Monster.CHICKEN;
	}

	public static void handleCombat() {

		switch (getMonster()) {
		case CHICKEN:
			if (ready()) {
				doCombat(Monster.CHICKEN.getName(), Variables.randomArea, Variables.randomAreaWalkTile,
						Monster.CHICKEN.getLoot(), Monster.CHICKEN.getGear());
			} else {
				Methods.getRandomLocation(Monster.CHICKEN.getAreas(), Monster.CHICKEN.getTiles());
			}
			break;

		default:
			break;
		}
	}

	private static boolean ready() {
		return Variables.randomArea != null && Variables.randomAreaWalkTile != null;
	}

	public static void doCombat(String name, RSArea area, RSTile tile, String[] loot, String[] gear) {
		if (Variables.initialBank) {
			if (Gear.isAllEquipped(gear)) {
				if (Killing.checkCombatStance(2)) {
					if (Methods.inArea(area)) {
						Variables.miniState = "at chickens";
						if (Inventory.isFull()) {
							Variables.miniState = "walking to bank";
							Bank.walkToBank();
						} else {
							Variables.miniState = "Killing";
							Killing.combat(name, area, loot);
						}
					} else if (Bank.isInBank()) {
						Variables.miniState = "in bank";
						if (Inventory.isFull()) {
							Variables.miniState = "doing bank";
							handleBank();
						} else {
							Variables.miniState = "walking to chicken area";
							Methods.walkToTile(tile);
						}
					} else {
						if (Inventory.isFull()) {
							Variables.miniState = "walking to bank";
							Bank.walkToBank();
						} else {
							Variables.miniState = "walking to chicken area";
							Methods.walkToTile(tile);
						}

					}
				}
			} else {
				Gear.getTaskGear(gear);
			}
		} else {
			doInitialBank();
		}

	}

	private static void doInitialBank() {
		Variables.miniState = "Initial Bank";
		if (Bank.isInBank()) {
			if (Inventory.getAll().length > 0) {
				Bank.depositAllInventory();
			} else if (Equipment.getItems().length > 0) {
				Bank.depositAllEquipment();
			} else {
				Variables.initialBank = true;
			}
		} else {
			Bank.walkToBank();
		}
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
	}
}
