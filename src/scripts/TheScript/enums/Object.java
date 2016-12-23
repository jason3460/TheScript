package scripts.TheScript.enums;

public enum Object {
	POTATO("Potato", // object name

			"Pick"); // click option

	private String name;
	private String action;

	Object(String name, String action) {
		this.name = name;
		this.action = action;
	}

	public String getName() {
		return this.name;
	}

	public String getAction() {
		return this.action;
	}

}
