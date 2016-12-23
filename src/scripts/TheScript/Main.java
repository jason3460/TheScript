package scripts.TheScript;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api2007.Walking;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Ending;
import org.tribot.script.interfaces.Painting;
import org.tribot.script.interfaces.Starting;

import scripts.TheScript.api.antiban.Antiban;
import scripts.TheScript.api.methods.Methods;
import scripts.TheScript.api.methods.Timer;
import scripts.TheScript.enums.Object;
import scripts.TheScript.enums.ScriptState;
import scripts.TheScript.gui.Gui;
import scripts.TheScript.tasks.combat.Combat;
import scripts.TheScript.tasks.moneyMaking.ObjectPicker;
import scripts.TheScript.tasks.woodcutting.Woodcutting;
import scripts.TheScript.variables.Variables;

@ScriptManifest(authors = { "JDog" }, category = "Test", name = "Test")
public class Main extends Script implements Starting, Ending, Painting {

	@Override
	public void onStart() {
		Mouse.setSpeed(General.random(120, 150));
		Walking.setWalkingTimeout(General.random(1500, 2000));
		General.useAntiBanCompliance(true);

	}

	@Override
	public void run() {

		Variables.runScript = true;

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

		Variables.timer = new Timer(Timer.getDuration());
		Variables.SCRIPT_STATE = getState();

		while (Variables.runScript) {
			if (!Antiban.activateRun()) {
				if (!Variables.timer.isRunning()) {
					General.println("Task Ended. Creating New Task.");
					Variables.timer.createTimer();
				} else {
					switch (Variables.SCRIPT_STATE) { //
					case COMBAT:
						Combat.handleCombat();
						break;
					case WOODCUTTING:
						Woodcutting.handleWoodcutting();
						break;
					case POTATO_PICKER:
						ObjectPicker.handlePicking(Object.POTATO);
						break;
					// case AFK:
					// Afk.doAfk();
					// break;
					default:
						break;
					}
					sleep(50, 75);
				}
			}

		}
	}

	public static ScriptState getState() {
		return Methods.randomEnum(ScriptState.class);
	}

	@Override
	public void onEnd() {

		System.out.print("Scripted ended.");
		System.out.print("Time: ");
		System.out.print("Tasks Complete: ");
		Antiban.destroy();
	}

	private Image getImage(String url) {
		try {
			return ImageIO.read(new URL(url));
		} catch (@SuppressWarnings("unused") IOException e) {
			return null;
		}
	}

	@SuppressWarnings("unused")
	private final Image img = getImage("");

	private static final long startTime = System.currentTimeMillis();

	Font font = new Font("Verdana", Font.BOLD, 14);

	private final RenderingHints antialiasing = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);

	private final Color color1 = new Color(255, 255, 255);
	private final Color color2 = new Color(0, 0, 0);

	private final BasicStroke stroke1 = new BasicStroke(1);

	private final Font font1 = new Font("Arial", 0, 12);

	@SuppressWarnings("unused")
	@Override
	public void onPaint(Graphics g1) {
		try {

			Graphics2D g = (Graphics2D) g1;
			g.setRenderingHints(antialiasing);

			long timeRan = System.currentTimeMillis() - startTime;
			long potatosPerHour = (Variables.count * 3600000 / timeRan);

			g.setColor(color1);
			g.fillRect(300, 344, 212, 129);
			g.setColor(color2);
			g.setStroke(stroke1);
			g.drawRect(300, 344, 212, 129);
			g.setFont(font1);

			g.drawString("Runtime: ", 310, 360);
			g.drawString(Timing.msToString(timeRan), 410, 360);

			g.drawString("Task: ", 310, 375);
			g.drawString(Variables.SCRIPT_STATE.toString(), 410, 375);

			g.drawString("Sub-Task: ", 310, 390);
			g.drawString(Variables.miniState, 410, 390);

			g.drawString("Task Timer:", 310, 405);

			g.drawString(Timing.msToString(Variables.timer.remainingTime()), 410, 405);

			g.drawString("Tasks Complete: ", 310, 420);
			g.drawString("" + (Variables.tasksComplete - 1), 410, 420);

			if (Variables.SCRIPT_STATE == ScriptState.POTATO_PICKER) {
				g.drawString("Potatoes/hr: ", 310, 435);
				g.drawString("" + potatosPerHour, 410, 435);
				g.drawString("Total Potatoes: ", 310, 450);
				g.drawString("" + Variables.count, 410, 450);
			}

		} catch (Exception e) {
		}

	}
}
