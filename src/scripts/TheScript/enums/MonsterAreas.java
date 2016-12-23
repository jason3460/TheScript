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
		public RSTile getTile() {
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
		public RSTile getTile() {
			return new RSTile(3181, 3290, 0);
		}
	},

	CHICKENS_LUMBRIDGE_EAST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile[] { new RSTile(3236, 3288, 0), new RSTile(3237, 3301, 0),
					new RSTile(3224, 3301, 0), new RSTile(3225, 3295, 0), new RSTile(3231, 3287, 0) });
		}

		@Override
		public RSTile getTile() {
			return new RSTile(3236, 3295, 0);
		}
	},

	CHICKENS_FALADOR_SOUTH {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile[] { new RSTile(3020, 3298, 0), new RSTile(3020, 3282, 0),
					new RSTile(3014, 3282, 0), new RSTile(3014, 3295, 0) });
		}

		@Override
		public RSTile getTile() {
			return new RSTile(3020, 3293, 0);
		}
	},

	COWS_LUMBRIDGE_WEST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile[] { new RSTile(3193, 3283, 0), new RSTile(3193, 3302, 0),
					new RSTile(3210, 3302, 0), new RSTile(3212, 3284, 0) });
		}

		@Override
		public RSTile getTile() {
			return new RSTile(3198, 3282, 0);
		}
	},

	COWS_LUMBRIDGE_EAST {
		@Override
		public RSArea getArea() {
			return new RSArea(
					new RSTile[] { new RSTile(3253, 3255, 0), new RSTile(3265, 3255, 0), new RSTile(3265, 3297, 0),
							new RSTile(3240, 3298, 0), new RSTile(3244, 3279, 0), new RSTile(3251, 3275, 0) });
		}

		@Override
		public RSTile getTile() {
			return new RSTile(3253, 3266, 0);
		}
	},
	
	COWS_FALADOR_SOUTH {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile[] { new RSTile(3021, 3297, 0), new RSTile(3021, 3313, 0), new RSTile(3043, 3313, 0), new RSTile(3043, 3297, 0) });
		}

		@Override
		public RSTile getTile() {
			return new RSTile(3031, 3312, 0);
		}
	};

	public abstract RSArea getArea();

	public abstract RSTile getTile();
}
