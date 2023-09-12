package com.detrivos.auto.experience;

import com.detrivos.auto.entity.assets.Turret;

public class Experience {
	
	boolean converted = false;
	
	public static int expAmount;
	
	private static final int[] TURRET_BASE_TABLE = {1, 2, 4};
	private static int[] turretConvTable = {1, 2, 4};

	public Experience() {
		this.expAmount = 0;
	}
	
	public static void addExp(Turret.Type t) {
		switch (t) {
		case NORMAL:
			expAmount += turretConvTable[0];
			break;
		case MACHINE:
			expAmount += turretConvTable[1];
			break;
		case ROCKET:
			expAmount += turretConvTable[2];
			break;
		}
	}
	
	public void convertChalTables(int dif) {
		if ((dif != 0 && dif % 2 == 0) && !converted) {
			for (int i = 0; i < 3; i++) {
				turretConvTable[i] = (int) Math.ceil(turretConvTable[i] * 1.5);
			}
			converted = true;
		}
		if (dif % 2 != 0 && converted) converted = false;
	}
	
	public static void resetChalTables() {
		for (int i = 0; i < 3; i++) {
			turretConvTable[i] = TURRET_BASE_TABLE[i];
		}
	}
}
