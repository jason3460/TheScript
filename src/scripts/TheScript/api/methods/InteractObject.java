package scripts.TheScript.api.methods;

import java.util.Arrays;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.types.generic.Filter;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSObjectDefinition;
import org.tribot.api2007.types.RSTile;

public class InteractObject {

	private String name;
	private int distance;
	private String action;
	private RSArea area;
	private boolean tileObject;
	private RSTile tile;

	public InteractObject(boolean tileObject, RSTile tile, RSArea area, int distance, String name, String action) {
		this.name = name;
		this.distance = distance;
		this.action = action;
		this.area = area;
		this.tileObject = tileObject;
		this.tile = tile;
	}

	private Filter<RSObject> objectFilter() {
		return new Filter<RSObject>() {
			@Override
			public boolean accept(RSObject o) {
				RSObjectDefinition def = o.getDefinition();
				if (def == null)
					return false;
				String[] actions = def.getActions();
				if (actions.length == 0)
					return false;
				if (!Arrays.asList(actions).contains(action))
					return false;
				return def.getName().equals(name);
			}
		};
	}

	private Filter<RSObject> areaFilter(RSArea area) {
		return org.tribot.api2007.ext.Filters.Objects.inArea(area);
	}

	private RSObject[] objects() {
		if (!this.tileObject) {
			return Objects.findNearest(distance, this.areaFilter(area).combine(this.objectFilter(), true));
		}
		return Objects.getAt(tile);

	}

	private RSObject target() {
		RSObject[] objects = this.objects();
		if (objects.length == 0)
			return null;
		return objects[0];
	}

	private boolean walkTo(RSObject object) {
		if (object == null)
			return false;
		return Walking.blindWalkTo(object);
	}

	private boolean setCameraAngle(RSObject object) {
		if (object == null)
			return false;
		Camera.setCameraAngle(General.random(50, 70));
		return object.isClickable();
	}

	public boolean click() {
		RSObject target = this.target();
		if (target == null)
			return false;
		if (!target.isOnScreen())
			return this.walkTo(target);
		if (!target.isClickable())
			return this.setCameraAngle(target);
		return DynamicClicking.clickRSObject(target, action);
	}

}