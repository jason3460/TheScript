package scripts.TheScript.api.methods;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GroundItems;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSGroundItem;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSItemDefinition;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.variables.Variables;

public class Methods {

	public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
		int x = Variables.random.nextInt(enumClass.getEnumConstants().length);
		return enumClass.getEnumConstants()[x];
	}

	public static boolean checkLevel(SKILLS skill, int level) {
		int currentLvl = Skills.getActualLevel(skill);
		if (currentLvl <= level) {
			return true;
		}
		return false;
	}

	public static int checkCombatLevel() {
		RSPlayer p = Player.getRSPlayer();
		if (p != null) {
			return p.getCombatLevel();
		}
		return 0;
	}

	public static int getLevel(SKILLS skill) {
		int currentLvl = Skills.getActualLevel(skill);
		return currentLvl;
	}

	public static boolean checkInventory(String itemName) {
		RSItem[] item = Inventory.find(itemName);
		return item.length > 0;
	}

	public static boolean checkInventory(String[] itemName) {
		RSItem[] item = Inventory.find(itemName);
		return item.length > 0;
	}

	public static boolean checkEquipment(String itemName) {
		RSItem[] item = Equipment.find(itemName);
		return item.length > 0;
	}

	public static boolean checkEquipment(String[] itemName) {
		RSItem[] item = Equipment.find(itemName);
		return item.length > 0;
	}

	public static boolean inArea(RSArea area) {
		RSTile myPos = Player.getPosition();
		return area.contains(myPos);
	}

	public static boolean atTile(RSTile Location) {
		RSTile myPos = Player.getPosition();
		return myPos.distanceTo(Location) <= 5;
	}

	public static boolean walkToTile(RSTile tile) {
		if (!WebWalking.walkTo(tile)) {
			return false;
		}
		return true;
	}

	public static void getRandomLocation(RSArea[] areas, RSTile[] tiles) {
		Variables.miniState = "Getting random location";
		int random = Variables.random.nextInt(areas.length);
		Variables.randomArea = areas[random];
		if (Variables.randomArea != null) {
			if (tiles[random] != null) {
				Variables.randomAreaWalkTile = tiles[random];
			}
		}
	}

	public static boolean pickUpGroundItems(String[] items, RSArea area) {
		RSGroundItem[] loot = GroundItems.findNearest(items);
		if (loot.length > 0 && loot[0] != null && (area.contains(loot[0].getPosition()))) {
			if (loot[0].isOnScreen() && !Player.isMoving() && PathFinding.canReach(loot[0], true)) {
				RSItemDefinition def = loot[0].getDefinition();
				if (def != null) {
					String name = def.getName();
					if (name != null) {
						final int before = Inventory.getCount(name);
						Antiban.getReactionTime();
						Antiban.sleepReactionTime();
						DynamicClicking.clickRSGroundItem(loot[0], "Take " + name);
						Timing.waitCondition(Conditions.get().inventoryItemCount(name, before),
								General.random(4000, 6000));
					}
				}
			} else if (!Player.isMoving()) {
				WebWalking.walkTo(loot[0]);
				long sleep = System.currentTimeMillis() + 2000;
				while (sleep > System.currentTimeMillis()) {
					if (Player.isMoving())
						sleep = System.currentTimeMillis() + 1000;
					if (Player.getPosition().distanceTo(loot[0]) <= 1)
						break;
					General.sleep(250, 500);
				}
			}
			return true;
		}
		return false;
	}

	public static boolean selectOption(String option) {
		return ChooseOption.select(option);
	}

	public static void debug(Object message) {
		System.out.println("[DEBUG] " + message);
	}

	public static void debugABC2(Object message) {
		System.out.println("[ABC2] " + message);
	}

}
