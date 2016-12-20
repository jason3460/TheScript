package scripts.TheScript.enums;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum npcAreas {
	CHICKENS_LUMBRIDGE_WEST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3193, 3284, 0), new RSTile(3184, 3284, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3193, 3284, 0);
		}
	};

	public abstract RSArea getArea();

	public abstract RSTile getWalkTile();
}
