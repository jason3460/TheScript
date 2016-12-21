package scripts.TheScript.api.methods;

import org.tribot.api.General;

import scripts.TheScript.Main;
import scripts.TheScript.variables.Variables;

public class Timer {

	private long startTime;
	private long duration;

	public Timer(long duration) {
		this.duration = duration;
		startTime = System.currentTimeMillis();
		int h = (int) ((duration / 1000) / 3600);
		int m = (int) (((duration / 1000) / 60) % 60);
		int s = (int) ((duration / 1000) % 60);

		Variables.SCRIPT_STATE = Main.getState();
		Variables.initialBank = false;
		Variables.locations.clear();
		General.println("New Task Created: " + Variables.SCRIPT_STATE);
		General.println("New Task Timer Created: " + h + ":" + m + ":" + s);
	}

	public boolean isRunning() {
		return System.currentTimeMillis() - startTime < duration;
	}

	public long remainingTime() {
		return isRunning() ? duration - (System.currentTimeMillis() - startTime) : 0;
	}
	
	public int getHour(){
		return (int) ((duration / 1000) / 3600);
	}
	
	public int getMinute(){
		return (int) (((duration / 1000) / 60) % 60);
	}
	
	public int getSecond(){
		return (int) ((duration / 1000) % 60);
	}
	
	public static long getDuration() {
		return General.randomLong(1800000, 3600000);
		// return General.randomLong(60000, 180000); //Tester
	}

	public void createTimer() {
		long duration = getDuration();
		Variables.timer = new Timer(duration);
	}

}