package scripts.TheScript.enums;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum MonsterAreas {
	CHICKENS_LUMBRIDGE_WEST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile[] { new RSTile(3193, 3285, 0), new RSTile(3192, 3270, 0),
					new RSTile(3184, 3270, 0), new RSTile(3180, 3288, 0) });
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3189, 3281, 0);
		}
	},

	CHICKENS_LUMBRIDGE_WEST2 {
		@Override
		public RSArea getArea() {
			return new RSArea(
					new RSTile[] { new RSTile(3186, 3289, 0), new RSTile(3169, 3289, 0), new RSTile(3169, 3301, 0),
							new RSTile(3173, 3307, 0), new RSTile(3179, 3307, 0), new RSTile(3186, 3301, 0) });
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3181, 3290, 0);
		}
	};

	public abstract RSArea getArea();

	public abstract RSTile getWalkTile();
}
