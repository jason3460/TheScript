package scripts.TheScript.tasks.moneyMaking;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Banking;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.api.methods.Bank;
import scripts.TheScript.api.methods.Hover;
import scripts.TheScript.api.methods.InteractObject;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.enums.ObjectAreas;
import scripts.TheScript.enums.Object;
import scripts.TheScript.variables.Variables;

public class ObjectPicker {

	private static void handleBank(String item) {
		if (Banking.isBankScreenOpen()) {
			Banking.depositAll();
			Timing.waitCondition(Conditions.get().dontHaveItem(item), General.random(4000, 6000));
		} else if (Banking.openBank()) {
			Timing.waitCondition(Conditions.get().bankOpen(), General.random(4000, 6000));
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

	private static boolean clickObject(String object, String action, RSArea area) {
		return new InteractObject(false, null, area, 30, object, action).click();
	}

	private static boolean isPicking() {
		return Player.getAnimation() != -1;
	}

	private static void pick(String object, String action, RSArea area) {
		
		if(Inventory.getCount(object) == Variables.before){
			if (isPicking()) {
				if (Antiban.should_hover) {
					Hover.handleHover(object);
				} else {
					Antiban.timedActions();
				}
				Antiban.generateTrackers(Antiban.getWaitingTime());
			} else {
				Antiban.getReactionTime();
				if (Hover.hovered(object)) {
					Mouse.click(1);
					Antiban.getABCUtil().moveMouse();
					Timing.waitCondition(Conditions.get().animating(), General.random(2000, 4000));
					Antiban.sleepReactionTime();
				} else {
					if (ChooseOption.isOpen()) {
						if (Methods.selectOption(action)) {
							Antiban.getABCUtil().moveMouse();
							Timing.waitCondition(Conditions.get().animating(), General.random(2000, 4000));
							Antiban.sleepReactionTime();
						}
						Antiban.generateTrackers(Antiban.getWaitingTime());
					} else {
						if (clickObject(object, action, area)) {
							Antiban.getABCUtil().moveMouse();
							Timing.waitCondition(Conditions.get().animating(), General.random(2000, 4000));
							Antiban.sleepReactionTime();
						}
					}
				}
				Antiban.generateTrackers(Antiban.getWaitingTime());
			}
		} else {
			Variables.count++;
			Variables.before = Inventory.getCount(object);
		}

	}

	private static void doPicking(String object, String action, RSArea area, RSTile tile) {

		if (Methods.inArea(area)) {
			Variables.miniState = "at " + object;
			if (Inventory.isFull()) {
				Variables.miniState = "Walking to bank";
				Bank.walkToBank();
			} else {
				Variables.miniState = "Picking " + object;
				pick(object, action, area);
			}
		} else if (Bank.isInBank()) {
			Variables.miniState = "In bank";
			if (Inventory.isFull()) {
				Variables.miniState = "Doing bank";
				handleBank(object);
			} else {
				Variables.miniState = "Walking to " + object;
				Methods.walkToTile(tile);
			}

		} else {
			if (Inventory.isFull()) {
				Variables.miniState = "Walking to bank";
				Bank.walkToBank();
			} else {
				Variables.miniState = "Walking to " + object;
				Methods.walkToTile(tile);
			}

		}
	}

	private static boolean ready() {
		return Variables.randomArea != null && Variables.randomAreaWalkTile != null;
	}

	public static void handlePicking(Object name) {
		if (Variables.initialBank) {
			switch (name) {
			case POTATO:
				Variables.miniState = "Potato";
				if (ready()) {
					doPicking(name.getName(), name.getAction(), Variables.randomArea, Variables.randomAreaWalkTile);

				} else {
					Methods.getRandomLocation(new RSArea[] { ObjectAreas.POTATO_LUMBRIDGE.getArea() },
							new RSTile[] { ObjectAreas.POTATO_LUMBRIDGE.getTile() });
				}
				break;
			default:
				break;
			}
		} else {
			doInitialBank();
		}
	}
}
