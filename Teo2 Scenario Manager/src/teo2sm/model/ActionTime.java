package teo2sm.model;

import java.util.Scanner;

public class ActionTime {
	private int hours;
	private int minutes;
	private int seconds;
	private long millis;
	
	public ActionTime(int h, int m, int s, long mm) {
		hours = h;
		minutes = m;
		seconds = s;
		millis = mm;
	}
	
	public ActionTime(String time) {
		Scanner sc = new Scanner(time);
		sc.useDelimiter(":");
		String s = sc.next();
		hours = Integer.parseInt(s);
		s = sc.next();
		minutes = Integer.parseInt(s);
		s = sc.next();
		seconds = Integer.parseInt(s);
		s = sc.next();
		millis = Long.parseLong(s);
		sc.close();
	}

	public int getHours() {
		return hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getSeconds() {
		return seconds;
	}

	public long getMillis() {
		return millis;
	}
	
	@Override
	public String toString() {
		String string = hours + ":" + minutes + ":" + seconds + ":" + millis;
		return string;
	}
}
