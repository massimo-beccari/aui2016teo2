package teo2sm.model;

import java.util.Scanner;

public class ActionTime {
	private long hours;
	private int minutes;
	private int seconds;
	private int millis;
	
	public ActionTime(long h, int m, int s, int mm) throws Exception {
		if(h < 0 || m < 0 || s < 0 || mm < 0)
			throw new Exception();
		if(m > 59 || s > 59 || mm > 999)
			throw new Exception();
		hours = h;
		minutes = m;
		seconds = s;
		millis = mm;
	}
	
	public ActionTime(String time) throws Exception {
		Scanner sc = new Scanner(time);
		sc.useDelimiter(":");
		String s = sc.next();
		hours = Long.parseLong(s);
		if(hours < 0) {
			sc.close();
			throw new Exception();
		}
		s = sc.next();
		minutes = Integer.parseInt(s);
		if(minutes < 0 || minutes > 59) {
			sc.close();
			throw new Exception();
		}
		s = sc.next();
		seconds = Integer.parseInt(s);
		if(seconds < 0 || seconds > 59) {
			sc.close();
			throw new Exception();
		}
		s = sc.next();
		millis = Integer.parseInt(s);
		if(millis < 0 || millis > 999) {
			sc.close();
			throw new Exception();
		}
		sc.close();
	}
	
	public ActionTime(long time) {
		hours = (int) (time / (60*60*1000));
		int remain = (int) (time - (hours*60*60*1000));
		minutes = remain / (60*1000);
		remain = remain - (minutes*60*1000);
		seconds = remain / 1000;
		remain = remain - (seconds*1000);
		millis = remain;
	}
	
	public ActionTime add(ActionTime t) throws Exception {
		int newMinutes = 0, newSeconds = 0, newMillis = this.millis + t.millis;
		long newHours = 0;
		if(newMillis >= 1000) {
			newSeconds = 1;
			newMillis = newMillis - 1000;
		}
		newSeconds = newSeconds + this.seconds + t.seconds;
		if(newSeconds >= 60) {
			newMinutes = 1;
			newSeconds = newSeconds - 60;
		}
		newMinutes = newMinutes + this.minutes + t.minutes;
		if(newMinutes >= 60) {
			newHours = 1;
			newMinutes = newMinutes - 60;
		}
		newHours = newHours + this.hours + t.hours;
		return new ActionTime(newHours, newMinutes, newSeconds, newMillis);
	}
	
	public ActionTime sub(ActionTime t) throws Exception {
		int newMinutes = 0, newSeconds = 0, newMillis = this.millis - t.millis;
		long newHours = 0;
		if(newMillis < 0) {
			newSeconds = 1;
			newMillis = newMillis + 1000;
		}
		newSeconds = this.seconds - t.seconds - newSeconds;
		if(newSeconds < 0) {
			newMinutes = 1;
			newSeconds = newSeconds + 60;
		}
		newMinutes = this.minutes - t.minutes - newMinutes;
		if(newMinutes < 0) {
			newHours = 1;
			newMinutes = newMinutes + 60;
		}
		newHours = this.hours - t.hours - newHours;
		if(newHours < 0)
			throw new Exception();
		return new ActionTime(newHours, newMinutes, newSeconds, newMillis);
	}

	public long getHours() {
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
	
	public long toLong() {
		long time = (hours*60*60*1000) + (minutes*60*1000) + (seconds*1000) + millis;
		return time;
	}
	
	/* test main
	public static void main(String[] args) {
		ActionTime t = new ActionTime(41729512);
		System.out.println(t.toString());
		try {
			ActionTime t2 = new ActionTime(0,0,30,600);
			System.out.println(t2.toString());
			ActionTime t3 = t.sub(t2);
			System.out.println(t3.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
