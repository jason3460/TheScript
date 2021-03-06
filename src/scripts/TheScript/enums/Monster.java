package scripts.TheScript.enums;

public enum Monster {

	CHICKEN("Chicken", new String[] { "Feather", "Bones" }),

	COW("Cow", new String[] { "Cowhide", "Bones" });

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