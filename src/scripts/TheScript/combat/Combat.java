package scripts.TheScript.combat;

import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills.SKILLS;

import scripts.TheScript.api.methods.Gear;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.combat.chicken.Chicken;
import scripts.TheScript.variables.Variables;

public class Combat {

	public enum Monster {
		CHICKEN;
	}

	private static boolean initialBank;

	public static Monster getMonster() {
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
				Variables.miniState = "chickens till 10";
				if (Gear.isAllEquiped(Variables.IRON_GEAR) && Inventory.getAll().length > 0) {
					Chicken.handleChickens();
				} else {
					Variables.miniState = "getting gear";
					Gear.getGear(Variables.IRON_GEAR);
				}
				break;
			default:
				break;
			}
		}
	
	private static void doInitialBank() {
		// TODO Auto-generated method stub

	}
}
