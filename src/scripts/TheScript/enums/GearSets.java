package scripts.TheScript.enums;

public enum GearSets {

	IRON(new String[] { "Iron full helm", "Iron platebody", "Iron platelegs", "Iron kiteshield", "Iron scimitar",
			"Amulet of power", "Leather gloves", "Leather boots", "Green cape" }),

	STEEL(new String[] { "Steel full helm", "Steel platebody", "Steel platelegs", "Steel kiteshield", "Steel scimitar",
			"Amulet of power", "Leather gloves", "Leather boots", "Green cape" }),

	MITHRIL(new String[] { "Mithril full helm", "Mithril platebody", "Mithril platelegs", "Mithril kiteshield",
			"Mithril scimitar", "Amulet of power", "Leather gloves", "Leather boots", "Green cape" }),

	WOODCUTTING(new String[] { "Leather gloves", "Leather boots", "Green cape" });

	private String[] gear;

	GearSets(String[] gear) {
		this.gear = gear;
	}

	public String[] getGear() {
		return this.gear;
	}
}
