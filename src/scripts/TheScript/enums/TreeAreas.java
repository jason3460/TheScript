package scripts.TheScript.enums;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum TreeAreas {
	DRAYNOR_OAK_EAST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3158, 3287, 0), new RSTile(3182, 3261, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3169, 3277, 0);
		}

	},
	DRAYNOR_WILLOW_SOUTH {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3073, 3240, 0), new RSTile(3097, 3218, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3087, 3236, 0);
		}
	},

	DRAYNOR_WILLOW_WEST {

		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3053, 3260, 0), new RSTile(3065, 3250, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3058, 3255, 0);
		}
	},

	DRAYNOR_NORMAL_SOUTH {

		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3095, 3237, 0), new RSTile(3127, 3212, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3109, 3227, 0);
		}

	},
	DRAYNOR_NORMAL_NORTH {

		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3082, 3317, 0), new RSTile(3112, 3285, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3100, 3301, 0);
		}

	},

	LUMBRIDGE_NORMALS_WEST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3179, 3231, 0), new RSTile(3200, 3205, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3191, 3220, 0);
		}
	},
	LUMBRIDGE_OAKS_WEST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3214, 3230, 0), new RSTile(3162, 3263, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3192, 3246, 0);
		}
	},
	LUMBRIDGE_YEWS_WEST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3198, 3211, 0), new RSTile(3138, 3266, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3171, 3228, 0);
		}
	},

	VARROCK_NORMALS_WEST {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3173, 3376, 0), new RSTile(3153, 3418, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3164, 3407, 0);
		}
	},

	VARROCK_NORMALS_SOUTH {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile[] { new RSTile(3169, 3362, 0), new RSTile(3162, 3356, 0),
					new RSTile(3154, 3380, 0), new RSTile(3169, 3377, 0) });
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3164, 3370, 0);
		}
	},

	VARROCK_OAKS_SOUTH {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile(3192, 3381, 0), new RSTile(3225, 3352, 0));
		}

		@Override
		public RSTile getWalkTile() {
			return new RSTile(3204, 3367, 0);
		}
	};

	public abstract RSArea getArea();

	public abstract RSTile getWalkTile();
}
