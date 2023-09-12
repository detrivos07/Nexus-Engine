package com.detrivos.auto.entity.spawners;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.level.Level;

public abstract class Spawner extends Entity {

	public enum Type {
		MOB
	}
	
	@SuppressWarnings("unused")
	private Type t;
	private int sign;
	
	public Spawner(int x, int y, Type t, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.t = t;
	}
	
	public void tick() {
	}
	
	//Offsets used for mob spawning
		protected int xOffset(int r) {
			if (random.nextInt(2) == 0) sign = -1;
			else sign = 1;
			int result = random.nextInt(r) * sign;
			return result;
		}
		
		protected int yOffset(int r) {
			if (random.nextInt(2) == 0) sign = -1;
			else sign = 1;
			int result = random.nextInt(r) * sign;
			return result;
		}
}
