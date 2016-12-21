package scripts.TheScript.enums;

import org.tribot.api.General;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.variables.Variables;

public enum Tree {
	NORMAL(
			"Tree", //tree name
			
			"Logs", //log name
			new RSArea[] {	new RSArea(new RSTile(3095, 3237, 0), new RSTile(3127, 3212, 0)), //draynor south
							new RSArea(new RSTile(3082, 3317, 0), new RSTile(3112, 3285, 0)), //draynor north
							new RSArea(new RSTile(3179, 3231, 0), new RSTile(3200, 3205, 0)), //lumby west
							new RSArea(new RSTile(3173, 3376, 0), new RSTile(3153, 3418, 0)), //varrock west
							new RSArea(new RSTile(3169, 3362, 0), new RSTile(3169, 3377, 0)), //varrock south
							}, 
			
			new RSTile[] {	new RSTile(3109, 3227, 0), 
							new RSTile(3100, 3301, 0),
							new RSTile(3191, 3220, 0),
							new RSTile(3164, 3407, 0),
							new RSTile(3164, 3370, 0)
							}), 
	
	OAK(
			"Oak", 
			"Oak logs", 
			new RSArea[] {},
			new RSTile[] {}), 
	WILLOW(
			"Willow", 
			"Willow logs", 
			new RSArea[] {}, new RSTile[] {}), 
	
	MAPLE(
			"Maple",
			"Maple logs", 
			new RSArea[] {},
			new RSTile[] {}), 
	
	YEW(
			"Yew", 
			"Yew logs", 
			new RSArea[] {}, 
			new RSTile[] {});

	private String name;
	private String logs;
	private RSArea[] areas;
	private RSTile[] tiles;

	Tree(String name, String logs, RSArea[] areas, RSTile[] tiles) {
		this.name = name;
		this.logs = logs;
		this.areas = areas;
		this.tiles = tiles;
	}

	public String getName() {
		return this.name;
	}

	public String getLogs() {
		return this.logs;
	}

	public RSArea[] getAreas() {
		return this.areas;
	}

	public RSTile[] getTiles() {
		return this.tiles;
	}

	public RSArea getArea() {
		int random = General.random(0, this.areas.length - 1);
		return Variables.randomArea = this.areas[random];
	}

	public RSTile getTile() {
		int random = General.random(0, this.tiles.length - 1);
		return Variables.randomAreaWalkTile = this.tiles[random];
	}

}
