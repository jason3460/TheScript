package scripts.TheScript.api.methods;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Camera;
import org.tribot.api2007.ChooseOption;
import org.tribot.api2007.Game;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSObject;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.conditions.Conditions;
import scripts.TheScript.variables.Variables;

public class Hover {
	public static void handleHover(String object) {
		if (Antiban.should_open_menu) {
			if (Variables.printDebug) {
				Methods.debugABC2("Right clicking next " + object);
			}
			if (hovered(object)) {
				Mouse.click(3);
			} else {
				if (hover(object))
					Timing.waitCondition(Conditions.get().uptext_Contains(object), General.random(4000, 6000));
			}
		} else {
			if (!hovered(object)) {
				if (Variables.printDebug) {
					Methods.debugABC2("ABC2: Hovering over " + object);
				}
				if (hover(object)) {
					Timing.waitCondition(Conditions.get().uptext_Contains(object), General.random(4000, 6000));
				}
			}
		}
	}

	private static boolean hover(String object) {
		RSObject[] objects = Objects.findNearest(6, object);
		if (objects.length > 1) {
			for (RSObject o : objects) {
				if (o.getPosition().distanceTo(Player.getPosition()) > 2) {
					if (!o.isOnScreen())
						Camera.turnToTile(objects[1]);
					else
						return o.hover();
				}
			}
		}
		return false;
	}

	public static boolean hovered(String object) {
		String uptext = Game.getUptext();
		if (uptext == null)
			return false;
		return !ChooseOption.isOpen() && uptext.contains(object);
	}

}
