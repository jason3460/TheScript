package scripts.TheScript.enums;

public enum Tree {
	NORMAL("Tree", // tree name

			"Logs"),

	OAK("Oak", "Oak logs"), WILLOW("Willow", "Willow logs"),

	MAPLE("Maple", "Maple logs"),

	YEW("Yew", "Yew logs");

	private String name;
	private String logs;

	Tree(String name, String logs) {
		this.name = name;
		this.logs = logs;
	}

	public String getName() {
		return this.name;
	}

	public String getLogs() {
		return this.logs;
	}

}
