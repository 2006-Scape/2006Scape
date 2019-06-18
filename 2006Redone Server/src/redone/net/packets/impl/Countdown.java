package redone.net.packets.impl;

/**
 * @author Jayden
 */

public class Countdown {
	private long endMillis;
	private long countdownMillis;
	private long setMillis;

	public Countdown() {
		endMillis = System.currentTimeMillis();
	}

	public Countdown(long endMillis) {
		this.endMillis = endMillis;
	}

	public void setTimeMillis(long millis) {
		this.setMillis = millis;
	}

	public void addDays(int days) {
		endMillis += days * 24 * 60 * 60 * 1000;
	}

	public void addHours(int hours) {
		endMillis += hours * 60 * 60 * 1000;
	}

	public void addMinutes(int minutes) {
		endMillis += minutes * 60 * 1000;
	}

	public void addSeconds(int seconds) {
		endMillis += seconds * 1000;
	}

	public void removeDays(int days) {
		endMillis -= days * 24 * 60 * 60 * 1000;
	}

	public void removeHours(int hours) {
		endMillis -= hours * 60 * 60 * 1000;
	}

	public void removeMinutes(int minutes) {
		endMillis -= minutes * 60 * 1000;
	}

	public void removeSeconds(int seconds) {
		endMillis -= seconds * 1000;
	}

	public int getSeconds() {
		calculate();
		int seconds = (int) (countdownMillis / 1000);
		if (seconds > 60) {
			seconds = seconds % 60;
		}
		return seconds;
	}

	public int getMinutes() {
		calculate();
		int minutes = (int) (countdownMillis / (60 * 1000));
		if (minutes >= 60) {
			minutes = minutes % 60;
		}
		return minutes;
	}

	public int getHours() {
		calculate();
		int hours = (int) (countdownMillis / (60 * 60 * 1000));
		if (hours >= 24) {
			hours = hours % 24;
		}
		return hours;
	}

	public int getDays() {
		calculate();
		return (int) (countdownMillis / (24 * 60 * 60 * 1000));
	}

	public int getTotalSeconds() {
		calculate();
		return (int) (countdownMillis / 1000);
	}

	public int getTotalMinutes() {
		calculate();
		return (int) (countdownMillis / (60 * 1000));
	}

	public String daysToString() {
		int days = getDays();
		return (days == 0 ? "a day" : days + (days == 1 ? " day" : " days"));
	}

	public String minutesToString() {
		int minutes = getMinutes();
		return (minutes == 0 ? "a minute" : minutes + (minutes == 1 ? " minute" : " minutes"));
	}

	public String secondsToString() {
		int seconds = getSeconds();
		return (seconds + (seconds == 1 ? " second" : " seconds"));
	}

	public long getEndMillis() {
		return endMillis;
	}

	public boolean finished() {
		if (getDays() <= 0 && getHours() <= 0 && getMinutes() <= 0 && getSeconds() <= 0) {
			return true;
		}
		return false;
	}

	private void calculate() {
		countdownMillis = endMillis - (System.currentTimeMillis() - setMillis);
	}
}
