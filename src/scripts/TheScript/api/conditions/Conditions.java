package scripts.TheScript.api.conditions;

import org.tribot.api.General;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Combat;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Game;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSTile;

public class Conditions {

	private static Conditions conditions = null;

	public static Conditions get() {
		return conditions != null ? conditions : new Conditions();
	}

	public Condition bankOpen() {
		return new Condition() {
			@Override
			public boolean active() {
				return Banking.isBankScreenOpen();
			}
		};
	}

	public Condition bankClosed() {
		return new Condition() {
			@Override
			public boolean active() {
				return !Banking.isBankScreenOpen();
			}
		};
	}

	public Condition animating() {
		return new Condition() {
			@Override
			public boolean active() {
				return Player.getAnimation() != -1;
			}
		};
	}

	public Condition notAnimating() {
		return new Condition() {
			@Override
			public boolean active() {
				return Player.getAnimation() != -1;
			}
		};
	}

	public Condition playerMoving() {
		return new Condition() {
			@Override
			public boolean active() {
				return Player.isMoving();
			}
		};
	}

	public Condition playerIdle() {
		return new Condition() {
			@Override
			public boolean active() {
				return !Player.isMoving();
			}
		};
	}

	public Condition playerInCombat() {
		return new Condition() {
			@Override
			public boolean active() {
				return Player.getRSPlayer().isInCombat() || Player.getAnimation() != -1 || Combat.isUnderAttack();
			}
		};
	}

	public Condition playerInteracting() {
		return new Condition() {
			@Override
			public boolean active() {
				return Player.getRSPlayer().getInteractingCharacter() != null;
			}
		};
	}

	public Condition canReachTile(RSTile tile) {
		return new Condition() {
			@Override
			public boolean active() {
				return PathFinding.canReach(tile, false);
			}
		};
	}

	public Condition isInArea(RSArea area) {
		return new Condition() {
			@Override
			public boolean active() {
				return area.contains(Player.getPosition());
			}
		};
	}

	public Condition inventoryEmpty() {
		return new Condition() {
			@Override
			public boolean active() {
				return Inventory.getAll().length == 0;
			}
		};
	}

	public Condition inventoryFull() {
		return new Condition() {
			@Override
			public boolean active() {
				return Inventory.isFull();
			}
		};
	}

	public Condition runIsOn() {
		return new Condition() {
			@Override
			public boolean active() {
				return Game.isRunOn();
			}
		};
	}

	public Condition haveItem(String item, int amount) {
		return new Condition() {
			@Override
			public boolean active() {
				return Inventory.getCount(item) >= amount;
			}
		};
	}

	public Condition dontHaveItem(String item) {
		return new Condition() {
			@Override
			public boolean active() {
				return Inventory.find(item).length == 0;
			}
		};
	}

	public Condition uptext_Contains(String text) {
		return new Condition() {
			@Override
			public boolean active() {
				String uptext = Game.getUptext();
				if (uptext == null)
					return false;
				return uptext.contains(text);
			}
		};
	}

	public Condition interface_Open(int parent, int child) {
		return new Condition() {
			@Override
			public boolean active() {
				RSInterfaceChild temp = Interfaces.get(parent, child);
				if (temp == null)
					return false;
				return !temp.isHidden();
			}
		};
	}

	public Condition inventoryItemCount(String name, int count) {
		return new Condition() {
			@Override
			public boolean active() {
				return Inventory.getCount(name) != count;
			}
		};
	}

	public Condition inventoryCount(int count) {
		return new Condition() {
			@Override
			public boolean active() {
				return Inventory.getAll().length > count;
			}
		};
	}

	public Condition playerDistanceToTile(RSTile tile, int i) {
		return new Condition() {
			@Override
			public boolean active() {
				General.sleep(200, 300);
				return Player.getPosition().distanceTo(tile) < i;
			}
		};
	}

	public Condition equipmentEmpty() {
		return new Condition() {
			@Override
			public boolean active() {
				return Equipment.getItems() == null;
			}
		};
	}
}