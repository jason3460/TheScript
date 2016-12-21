package scripts.TheScript;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.api.methods.Timer;
import scripts.TheScript.enums.ScriptState;
import scripts.TheScript.gui.Gui;
import scripts.TheScript.tasks.afk.Afk;
import scripts.TheScript.tasks.combat.Combat;
import scripts.TheScript.tasks.woodcutting.Woodcutting;
import scripts.TheScript.variables.Variables;

@ScriptManifest(authors = { "JDog" }, category = "Test", name = "Test")
public class Main extends Script implements Painting {

	@Override
	public void run() {

		Variables.runScript = true;

		Variables.timer = new Timer(Timer.getDuration());

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
			if (!Variables.timer.isRunning()) {
				General.println("Task Ended. Creating New Task.");
				Variables.timer.createTimer();
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

	public static ScriptState getState() {
		return Methods.randomEnum(ScriptState.class);
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
		int h = Variables.timer.getHour();
		int m = Variables.timer.getMinute();
		int s = Variables.timer.getSecond();

		gg.setFont(font);

		gg.setColor(Color.RED);
		gg.drawString("Runtime: " + Timing.msToString(timeRan), 200, 370);
		gg.drawString("STATE: " + Variables.SCRIPT_STATE, 200, 390);
		gg.drawString("Mini-State: " + Variables.miniState, 200, 410);
		
		gg.drawString("Timer: " + h + ":" + m + ":" + s, 200, 430);
	}

}
