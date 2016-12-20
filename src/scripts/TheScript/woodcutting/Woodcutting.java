
package scripts.TheScript.woodcutting;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Inventory.DROPPING_PATTERN;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.api.methods.Bank;
import scripts.TheScript.api.methods.InteractObject;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.enums.TreeAreas;
import scripts.TheScript.enums.TreeTypes;
import scripts.TheScript.variables.Variables;

public class Woodcutting {

	public enum Tree {
		NORMAL, OAK, WILLOW, MAPLE, YEW;
	};

	public static Tree getTree() {
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

	public static String getAxe() {
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

	private static String getLog() {
		for (TreeTypes tree : TreeTypes.values()) {
			if (tree.getName().equals(getTree())) {
				return tree.getLogs();
			}
		}
		return "";
	}

	private static void handleHover(String tree) {
		if (Antiban.should_open_menu) {
			if (Variables.printDebug) {
				Methods.debugABC2("Right clicking next " + tree);
			}
			if (hovered(tree)) {
				Mouse.click(3);
			} else {
				if (hover(tree))
					Timing.waitCondition(Conditions.get().uptext_Contains(tree), General.random(4000, 6000));
			}
		} else {
			if (!hovered(tree)) {
				if (Variables.printDebug) {
					Methods.debugABC2("ABC2: Hovering over next " + tree);
				}
				if (hover(tree)) {
					Timing.waitCondition(Conditions.get().uptext_Contains(tree), General.random(4000, 6000));
				}
			}
		}
	}

	public static boolean hover(String tree) {
		RSObject[] trees = Objects.findNearest(6, tree);
		if (trees.length > 1) {
			for (RSObject t : trees) {
				if (t.getPosition().distanceTo(Player.getPosition()) > 2) {
					if (!t.isOnScreen())
						Camera.turnToTile(trees[1]);
					else
						return t.hover();
				}
			}
		}
		return false;
	}

	private static boolean hovered(String tree) {
		String uptext = Game.getUptext();
		if (uptext == null)
			return false;
		return !ChooseOption.isOpen() && uptext.contains(tree);
	}

	private static boolean selectOption() {
		return ChooseOption.select("Chop down");
	}

	private static boolean cut(String tree, RSArea treeArea) {
		return new InteractObject(false, null, treeArea, 30, tree, "Chop down").click();
	}

	private static void chop(String treeName, RSArea area) {
		if (isCutting()) {
			if (Antiban.should_hover) {
				handleHover(treeName);
			} else {
				Antiban.timedActions();
			}
			Antiban.generateTrackers(Antiban.getWaitingTime());
		} else {
			Antiban.getReactionTime();
			if (hovered(treeName)) {
				Mouse.click(1);
				Antiban.getABCUtil().moveMouse();
				Timing.waitCondition(Conditions.get().animating(), General.random(3000, 6000));
				Antiban.sleepReactionTime();
			} else {
				if (ChooseOption.isOpen()) {
					if (selectOption()) {
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

	private static int combatLevel() {
		RSPlayer p = Player.getRSPlayer();
		if (p != null) {
			return p.getCombatLevel();
		}
		return 0;
	}

	private static void handleTree() {
		Variables.locations.clear();
		if (Variables.locations.size() == 0) {
			Variables.locations.put(TreeAreas.DRAYNOR_NORMAL_SOUTH.getArea(),
					TreeAreas.DRAYNOR_NORMAL_SOUTH.getWalkTile());
			Variables.locations.put(TreeAreas.DRAYNOR_NORMAL_NORTH.getArea(),
					TreeAreas.DRAYNOR_NORMAL_NORTH.getWalkTile());
			Variables.locations.put(TreeAreas.VARROCK_NORMALS_WEST.getArea(),
					TreeAreas.VARROCK_NORMALS_WEST.getWalkTile());
			Variables.locations.put(TreeAreas.VARROCK_NORMALS_SOUTH.getArea(),
					TreeAreas.VARROCK_NORMALS_SOUTH.getWalkTile());
			if (combatLevel() >= 10) {
				Variables.locations.put(TreeAreas.LUMBRIDGE_NORMALS_WEST.getArea(),
						TreeAreas.LUMBRIDGE_NORMALS_WEST.getWalkTile());
			}
			Methods.debug("Adding normal trees");
		}
		Variables.treeName = TreeTypes.NORMAL.getName();
		Variables.logName = TreeTypes.NORMAL.getLogs();
		doWoodcutting(Variables.treeName);
	}

	private static void handleOak() {
		Variables.locations.clear();
		if (Variables.locations.size() == 0) {
			Variables.locations.put(TreeAreas.DRAYNOR_NORMAL_SOUTH.getArea(),
					TreeAreas.DRAYNOR_NORMAL_SOUTH.getWalkTile());
			Variables.locations.put(TreeAreas.DRAYNOR_NORMAL_NORTH.getArea(),
					TreeAreas.DRAYNOR_NORMAL_NORTH.getWalkTile());
			Variables.locations.put(TreeAreas.VARROCK_NORMALS_WEST.getArea(),
					TreeAreas.VARROCK_NORMALS_WEST.getWalkTile());
			if (combatLevel() >= 10) {
				Variables.locations.put(TreeAreas.LUMBRIDGE_NORMALS_WEST.getArea(),
						TreeAreas.LUMBRIDGE_NORMALS_WEST.getWalkTile());
			}
			Methods.debug("Adding oak trees");
		}
		Variables.treeName = TreeTypes.NORMAL.getName();
		Variables.logName = TreeTypes.NORMAL.getLogs();
		doWoodcutting(Variables.treeName);
	}

	private static void handleWillow() {
		Variables.locations.clear();
	}

	private static void handleMaple() {
		Variables.locations.clear();
	}

	private static void handleYew() {
		Variables.locations.clear();
	}

	public static void doWoodcutting(String treeName) {

		if (ready()) {
			if (Methods.checkInventory(getAxe())) {
				if (Methods.inArea(Variables.randomArea)) {
					Variables.miniState = "at trees";
					if (Inventory.isFull() && Variables.doPowerChop) {
						if (drop()) {
							Timing.waitCondition(Conditions.get().dontHaveItem(getLog()), General.random(3000, 6000));
						}
					}
					if (Inventory.isFull()) {
						Variables.miniState = "walking to bank";
						Bank.walkToBank();
					} else {
						Variables.miniState = "cutting trees";
						chop(treeName, Variables.randomArea);
					}
				} else if (Bank.isInBank()) {
					Variables.miniState = "in bank";
					if (Inventory.isFull()) {
						Variables.miniState = "doing bank";
						handleBank();
					} else {
						Variables.miniState = "walking to trees";
						Methods.walkToTile(Variables.randomAreaWalkTile);
					}

				} else {
					if (Inventory.isFull()) {
						Variables.miniState = "walking to bank";
						Bank.walkToBank();
					} else {
						Variables.miniState = "walking to trees";
						Methods.walkToTile(Variables.randomAreaWalkTile);
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
		} else if (Variables.locations.size() > 0) {
			Methods.getRandomLocation();
		}

	}

	public static void handleBank() {
		if (Banking.isBankScreenOpen()) {
			Antiban.getReactionTime();
			if (Methods.checkInventory(getAxe())) {
				Banking.depositAllExcept(getAxe());
				Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 6000));
				Antiban.sleepReactionTime();
			} else {
				Antiban.sleepReactionTime();
				Banking.depositAll();
				Timing.waitCondition(Conditions.get().dontHaveItem(getLog()), General.random(4000, 6000));
			}
			Antiban.generateTrackers(Antiban.getWaitingTime());
		} else {
			Antiban.getReactionTime();
			if (Banking.openBank()) {
				Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 6000));
				Antiban.sleepReactionTime();
			}
		}
		Antiban.generateTrackers(Antiban.getWaitingTime());
	}

	public static void doInitialBank() {
		Variables.miniState = "Initial Bank";

		if (Bank.isInBank()) {
			if (Bank.depositBankAll() && Banking.depositEquipment()) {
				Bank.withdrawItem(1, getAxe());
				Variables.initialBank = true;
			}
		} else {
			Bank.walkToBank();
		}
	}

	private static boolean ready() {
		return Variables.randomArea != null && Variables.randomAreaWalkTile != null;
	}

	public static void handleWoodcutting() {
		if (Variables.initialBank != false) {
			doInitialBank();
		} else {
			switch (getTree()) {
			case NORMAL:
				Variables.miniState = "Normal";
				handleTree();
				break;
			case OAK:
				Variables.miniState = "Oak";
				handleOak();
				break;
			case WILLOW:
				Variables.miniState = "Willow";
				handleWillow();
				break;
			case MAPLE:
				Variables.miniState = "Maple";
				handleMaple();
				break;
			case YEW:
				handleYew();
				Variables.miniState = "Yew";
				break;
			default:
				break;
			}
		}
	}
}