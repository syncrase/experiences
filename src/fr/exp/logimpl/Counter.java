package fr.exp.logimpl;

public class Counter {

	long startTime;
	long endTime;

	public Counter() {
		this.startTime = 0;
		this.endTime = 0;
	}

	public void start() {
		startTime = System.currentTimeMillis();
	}

	public void stop() {
		endTime = System.currentTimeMillis();
	}

	public String getTime() {
		if (endTime == 0)
			return ((System.currentTimeMillis() - startTime) / 1000.0) + " sec";
		else
			return ((endTime - startTime) / 1000.0d) + " sec";
	}

	public static Counter getCounter() {
		return new Counter();
	}
}
