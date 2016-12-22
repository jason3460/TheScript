package scripts.TheScript.enums;

public enum Monster {

	CHICKEN("Chicken", new String[] { "Feather", "Bones" }),

	COW("Chicken", new String[] { "Cow hide", "Bones" });

	private String name;
	private String[] loot;

	Monster(String name, String[] loot) {
		this.name = name;
		this.loot = loot;
	}

	public String[] getLoot() {
		return this.loot;
	}

	public String getName() {
		return this.name;
	}
}