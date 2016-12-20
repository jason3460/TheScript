package scripts.TheScript;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;

import javax.imageio.ImageIO;

import org.tribot.api.Timing;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.script.interfaces.Painting;

import scripts.TheScript.afk.Afk;
import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.methods.Timer;
import scripts.TheScript.combat.Combat;
import scripts.TheScript.enums.ScriptState;
import scripts.TheScript.variables.Variables;
import scripts.TheScript.woodcutting.Woodcutting;

@ScriptManifest(authors = { "JDog" }, category = "Test", name = "Test")
public class Main extends Script implements Painting {

	private static final SecureRandom random = new SecureRandom();

	public static Timer timer;

	@Override
	public void run() {

		Variables.runScript = true;

		timer = new Timer(Timer.getDuration());

		Gui GUI = new Gui();

		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenW = (screensize.width) / 2;
		int screenH = (screensize.height) / 2;

		GUI.setVisible(true);

		GUI.setLocation((screenW / 2), (screenH / 2));

		while (!Variables.GUI_COMPLETE) {
			sleep(300);
		}

		GUI.setVisible(false);

		Mouse.setSpeed(General.random(120, 150));
		Walking.setWalkingTimeout(General.random(1500, 2000));
		WebWalking.setUseRun(Antiban.activateRun());

		Variables.SCRIPT_STATE = getState();

		while (Variables.runScript) {
			if (!timer.isRunning()) {
				General.println("Task Ended. Creating New Task.");
				timer.createTimer();
			} else {
				switch (Variables.SCRIPT_STATE) {

				case CHICKENS:
					Combat.handleCombat();
					break;

				case WOODCUTTING:
					Woodcutting.handleWoodcutting();
					break;

				case AFK:
					Afk.doAfk();
					break;
				default:
					break;
				}
				sleep(50, 75);
			}

		}
	}

	public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
		int x = random.nextInt(enumClass.getEnumConstants().length);
		return enumClass.getEnumConstants()[x];
	}

	public static ScriptState getState() {
		return randomEnum(ScriptState.class);
	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (@SuppressWarnings("unused") IOException e) {
			return null;
		}
	}

	private final Image img = getImage("");

	private static final long startTime = System.currentTimeMillis();

	Font font = new Font("Verdana", Font.BOLD, 14);

	@Override
	public void onPaint(Graphics g) {

		Graphics2D gg = (Graphics2D) g;
		gg.drawImage(img, 0, 304, null);

		long timeRan = System.currentTimeMillis() - startTime;

		gg.setFont(font);

		gg.setColor(Color.RED);
		gg.drawString("Runtime: " + Timing.msToString(timeRan), 300, 370);
		gg.drawString("STATE: " + Variables.SCRIPT_STATE, 300, 390);
		gg.drawString("Mini-State: " + Variables.miniState, 300, 410);
	}

}
