package scripts.TheScript.api.methods;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Walking;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Filters;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSNPCDefinition;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;

public class Killing {

	public boolean checkHealth() {
		General.println("checking health");
		if (Skills.getActualLevel(SKILLS.HITPOINTS) - Skills.getCurrentLevel(SKILLS.HITPOINTS) >= 12) {
			return true;
		}
		return false;
	}

	public static boolean checkCombatStance(int stanceID) {
		if (Combat.getSelectedStyleIndex() != stanceID) {
			Antiban.getReactionTime();
			setCombatStance(stanceID);
			Antiban.sleepReactionTime();
			Inventory.open();
			return true;
		}
		return true;
	}

	public static boolean setCombatStance(int stanceID) {
		return Combat.selectIndex(stanceID);
	}

	public static boolean haveFood() {
		RSItem[] food = Inventory.find(Filters.Items.actionsContains("Eat"));
		if (food.length > 0) {
			return true;
		}
		return false;
	}

	public static boolean eatFood() {
		if (haveFood() && (Combat.getHP() <= Antiban.eat_at)) {
			RSItem[] food = Inventory.find(Filters.Items.actionsContains("Eat"));
			food[0].click("Eat");
			return true;
		}
		return false;
	}

	public static boolean inCombat() {
		if (Player.getRSPlayer().isInCombat() || Player.getAnimation() != -1 || Combat.isUnderAttack()) {
			return true;
		}
		return false;
	}

	public static RSNPC[] findCombatNPCs(String npcName) {
		return NPCs.findNearest(new Filter<RSNPC>() {
			@Override
			public boolean accept(RSNPC a) {
				RSNPCDefinition def = a.getDefinition();
				if (def == null)
					return false;
				return def.getName().equals(npcName) && !a.isInCombat();
			}
		});
	}

	public static RSNPC getCombatNPC(String npcName) {
		RSNPC[] objects = findCombatNPCs(npcName);
		if (objects.length > 0) {
			final RSNPC target = Antiban.selectNextTarget(objects);
			return target;
		}
		return null;
	}

	public static void combat(String name) {
		RSNPC target = getCombatNPC(name);

		if (!eatFood()) {
			if (!Killing.inCombat()) {
				if (target != null) {
					Antiban.getReactionTime();
					Antiban.sleepReactionTime();
					if (!target.isOnScreen() && PathFinding.canReach(target, false)) {
						Walking.blindWalkTo(target);
					}
					if (!target.isOnScreen() || !PathFinding.canReach(target, false)) {
						WebWalking.walkTo(target);
					}
					if (!target.isClickable()) {
						Camera.turnToTile(target);
					}
					if (DynamicClicking.clickRSNPC(target, "Attack")) {
						Antiban.generateTrackers(Antiban.getWaitingTime());
						Timing.waitCondition(Conditions.get().playerInCombat(), General.random(4000, 7000));
					}

				}
			} else {

				Antiban.timedActions();
				General.sleep(1000, 2000);
			}
		}
	}
}
