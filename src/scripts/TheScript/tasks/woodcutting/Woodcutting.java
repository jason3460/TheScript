package scripts.TheScript.tasks.woodcutting;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Inventory.DROPPING_PATTERN;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.api.methods.Bank;
import scripts.TheScript.api.methods.Hover;
import scripts.TheScript.api.methods.InteractObject;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.enums.Tree;
import scripts.TheScript.enums.TreeAreas;
import scripts.TheScript.variables.Variables;

public class Woodcutting {

	private static Tree getTree() {
		int wcLevel = Skills.getActualLevel(SKILLS.WOODCUTTING);
		if (wcLevel < 15)
			return Tree.NORMAL;
		if (wcLevel >= 15 && wcLevel < 30)
			return Tree.OAK;
		if (wcLevel >= 30 && wcLevel < 60)
			return Tree.WILLOW;
		if (wcLevel >= 60)
			return Tree.YEW;
		return Tree.NORMAL;
	}

	private static String getAxe() {
		int wcLevel = Methods.getLevel(SKILLS.WOODCUTTING);

		if (wcLevel < 6)
			return "Iron axe";
		if (wcLevel >= 6 && wcLevel < 21)
			return "Steel axe";
		if (wcLevel >= 21 && wcLevel < 31)
			return "Mithril axe";
		if (wcLevel >= 31 && wcLevel < 41)
			return "Adamant axe";
		if (wcLevel >= 41)
			return "Rune axe";
		return "Bronze axe";
	}

	private static boolean cut(String tree, RSArea treeArea) {
		return new InteractObject(false, null, treeArea, 30, tree, "Chop down").click();
	}

	private static void chop(String treeName, RSArea area) {
		if (isCutting()) {
			if (Antiban.should_hover) {
				Hover.handleHover(treeName);
			} else {
				Antiban.timedActions();
			}
			Antiban.generateTrackers(Antiban.getWaitingTime());
		} else {
			Antiban.getReactionTime();
			if (Hover.hovered(treeName)) {
				Mouse.click(1);
				Antiban.getABCUtil().moveMouse();
				Timing.waitCondition(Conditions.get().animating(), General.random(3000, 6000));
				Antiban.sleepReactionTime();
			} else {
				if (ChooseOption.isOpen()) {
					if (Methods.selectOption("Chop down")) {
						Antiban.getABCUtil().moveMouse();
						Timing.waitCondition(Conditions.get().animating(), General.random(3000, 6000));
						Antiban.sleepReactionTime();
					}
					Antiban.generateTrackers(Antiban.getWaitingTime());
				} else {
					if (cut(treeName, area)) {
						Antiban.getABCUtil().moveMouse();
						Timing.waitCondition(Conditions.get().animating(), General.random(3000, 6000));
						Antiban.sleepReactionTime();
					}
				}
			}
			Antiban.generateTrackers(Antiban.getWaitingTime());
		}

	}

	private static boolean isCutting() {
		return Player.getAnimation() != -1;
	}

	private static boolean drop() {
		Variables.miniState = "dropping logs";

		DROPPING_PATTERN[] patterns = DROPPING_PATTERN.values();
		if (patterns.length > 0) {
			Inventory.setDroppingPattern(patterns[General.random(0, patterns.length - 1)]);
		}

		Antiban.getReactionTime();
		if (Inventory.dropAllExcept(new String[] { getAxe() }) > 0) {
			Antiban.sleepReactionTime();
			return true;
		}
		Antiban.generateTrackers(Antiban.getWaitingTime());
		return false;
	}

	private static void doWoodcutting(String tree, String log, RSArea area, RSTile tile) {

		if (Methods.checkInventory(getAxe())) {
			if (Methods.inArea(area)) {
				Variables.miniState = "at " + tree + "'s";
				if (Inventory.isFull() && Variables.doPowerChop) {
					if (drop()) {
						Timing.waitCondition(Conditions.get().dontHaveItem(log), General.random(3000, 6000));
					}
				}
				if (Inventory.isFull()) {
					Variables.miniState = "walking to bank";
					Bank.walkToBank();
				} else {
					Variables.miniState = "cutting " + tree + "'s";
					chop(tree, area);
				}
			} else if (Bank.isInBank()) {
				Variables.miniState = "in bank";
				if (Inventory.isFull()) {
					Variables.miniState = "doing bank";
					handleBank(log);
				} else {
					Variables.miniState = "walking to " + tree + "'s";
					Methods.walkToTile(tile);
				}

			} else {
				if (Inventory.isFull()) {
					Variables.miniState = "walking to bank";
					Bank.walkToBank();
				} else {
					Variables.miniState = "walking to " + tree + "'s";
					Methods.walkToTile(tile);
				}

			}
		} else {
			Methods.debug("getting axe");
			if (Bank.isInBank()) {
				if (!Banking.isBankScreenOpen()) {
					Banking.openBank();
				}
				Variables.miniState = "withdrawing axe";
				if (!Methods.checkInventory(getAxe()) || Inventory.getAll().length > 0) {
					Banking.depositAll();
				}
				Bank.withdrawItem(1, getAxe());
			} else {
				Variables.miniState = "going to get axe";
				Bank.walkToBank();
			}
		}
	}

	private static void handleBank(String log) {
		if (Banking.isBankScreenOpen()) {
			if (Methods.checkInventory(getAxe())) {
				Banking.depositAllExcept(getAxe());
				Timing.waitCondition(Conditions.get().dontHaveItem(log), General.random(4000, 6000));
			} else {
				Banking.depositAll();
				Timing.waitCondition(Conditions.get().dontHaveItem(log), General.random(4000, 6000));
			}
		} else {
			if (Banking.openBank()) {
				Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 6000));
			}
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
				Bank.withdrawItem(1, getAxe());
				Variables.initialBank = true;
			}
		} else {
			Bank.walkToBank();
		}
	}

	public static void handleWoodcutting() {

		if (Variables.initialBank) {
			switch (getTree()) {
			case NORMAL:
				Variables.miniState = "Normal";
				if (ready()) {
					doWoodcutting(Tree.NORMAL.getName(), Tree.NORMAL.getLogs(), Variables.randomArea,
							Variables.randomAreaWalkTile);

				} else {
					Methods.getRandomLocation(new RSArea[] { TreeAreas.DRAYNOR_NORMAL_NORTH.getArea(),
							TreeAreas.DRAYNOR_NORMAL_SOUTH.getArea(), TreeAreas.LUMBRIDGE_NORMALS_WEST.getArea(),
							TreeAreas.VARROCK_NORMALS_SOUTH.getArea(), TreeAreas.VARROCK_NORMALS_WEST.getArea() },

							new RSTile[] { TreeAreas.DRAYNOR_NORMAL_NORTH.getTile(),
									TreeAreas.DRAYNOR_NORMAL_SOUTH.getTile(),
									TreeAreas.LUMBRIDGE_NORMALS_WEST.getTile(),
									TreeAreas.VARROCK_NORMALS_SOUTH.getTile(),
									TreeAreas.VARROCK_NORMALS_WEST.getTile() });
				}
				break;

			case OAK:
				Variables.miniState = "Oak";
				if (ready()) {
					doWoodcutting(Tree.OAK.getName(), Tree.OAK.getLogs(), Variables.randomArea,
							Variables.randomAreaWalkTile);

				} else {
					Methods.getRandomLocation(new RSArea[] { TreeAreas.LUMBRIDGE_OAKS_WEST.getArea(), TreeAreas.VARROCK_OAKS_SOUTH.getArea() },

							new RSTile[] { TreeAreas.LUMBRIDGE_OAKS_WEST.getTile(), TreeAreas.VARROCK_OAKS_SOUTH.getTile() });
				}
				break;

			case WILLOW:
				Variables.miniState = "Willow";
				break;

			case MAPLE:
				Variables.miniState = "Maple";
				break;

			case YEW:
				Variables.miniState = "Yew";
				break;

			default:
				break;
			}
		} else {
			doInitialBank();
		}
	}

	private static boolean ready() {
		return Variables.randomArea != null && Variables.randomAreaWalkTile != null;
	}
}