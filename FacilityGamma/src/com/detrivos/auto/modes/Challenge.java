package com.detrivos.auto.modes;

public class Challenge {
	
	byte inc = 0;
	public static int dif = 0;
	long timer = 0;
	
	boolean d = false;
	
	public void tick() {
		difficulty();
	}
	
	private void difficulty() {
		inc++;
		if (inc % 60 == 0) timer++;
		if (timer >= Long.MAX_VALUE) timer = 0;
		if (inc > 120) inc = 0;
		
		if (timer % 120 == 0 && !d) {
			dif++;
			d = true;
		} else if (timer % 120 != 0) {
			d = false;
		}
	}
	
	public void reset() {
		inc = 0;
		dif = 0;
		timer = 0;
	}
}
