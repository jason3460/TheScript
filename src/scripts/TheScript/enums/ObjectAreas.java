package scripts.TheScript.enums;

import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

public enum ObjectAreas {
	POTATO_LUMBRIDGE {
		@Override
		public RSArea getArea() {
			return new RSArea(new RSTile[] { new RSTile(3139, 3290, 0), new RSTile(3156, 3289, 0), new RSTile(3155, 3268, 0), new RSTile(3138, 3270, 0) });
		}

		@Override
		public RSTile getTile() {
			return new RSTile(3145, 3289, 0);
		}
	};
	
	public abstract RSArea getArea();

	public abstract RSTile getTile();
}
