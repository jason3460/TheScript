package scripts.TheScript.enums;

public enum TreeTypes {
	NORMAL("Tree", "Logs", 0), OAK("Oak", "Oak logs", 1), WILLOW("Willow", "Willow logs", 2), YEW("Yew", "Yew logs", 3);

	private String name;
	private String logs;
	private int index;

	TreeTypes(String name, String logs, int index) {
		this.name = name;
		this.logs = logs;
		this.index = index;
	}

	public String getName() {
		return this.name;
	}

	public String getLogs() {
		return this.logs;
	}

	public int getIndex() {
		return this.index;
	}
}
