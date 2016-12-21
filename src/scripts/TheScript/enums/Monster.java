package scripts.TheScript.enums;

import org.tribot.api.General;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.TheScript.variables.Variables;

public enum Monster {
	CHICKEN(
			"Chicken",

			new RSArea[] {
					new RSArea(new RSTile[] { new RSTile(3181, 3270, 0), new RSTile(3194, 3284, 0) }),
					new RSArea(new RSTile[] { new RSTile(3185, 3289, 0), new RSTile(3169, 3306, 0) })
					
					},
			
			new RSTile[] {
					new RSTile(3188, 3279, 0),
					new RSTile(3181, 3289, 0)
		
					},

			new String[] { 
					"Feather"
					},

			new String[] { "Iron full helm", "Iron platebody", "Iron platelegs", "Iron kiteshield", "Iron scimitar",
					"Amulet of power", "Leather gloves", "Leather boots", "Green cape" 
					});

	private String name;
	private RSArea[] areas;
	private RSTile[] tiles;
	private String[] loot;
	private String[] gear;

	Monster(String name, RSArea[] areas, RSTile[] tiles, String[] loot, String[] gear) {
		this.name = name;
		this.areas = areas;
		this.tiles = tiles;
		this.loot = loot;
		this.gear = gear;
	}

	public String[] getGear() {
		return this.gear;
	}

	public String[] getLoot() {
		return this.loot;
	}

	public String getName() {
		return this.name;
	}

	public RSArea[] getAreas() {
		return this.areas;
	}
	
	public RSTile[] getTiles(){
		return this.tiles;
	}
	
	public RSArea getArea() {
		int random = General.random(0, this.areas.length - 1);
		return Variables.randomArea = this.areas[random];
	}
	
	public RSTile getTile(){
		int random = General.random(0, this.tiles.length - 1);
		return Variables.randomAreaWalkTile = this.tiles[random];
	}
}
