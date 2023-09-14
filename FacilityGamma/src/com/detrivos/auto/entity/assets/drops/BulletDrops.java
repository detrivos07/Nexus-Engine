package com.detrivos.auto.entity.assets.drops;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class BulletDrops extends Entity {
	
	public enum BType {
		PISTOL, SCATTER, MACHINE, ROCKET
	}
	
	public BType t;
	public int quantity;
	public boolean gave = false;
	
	public BulletDrops(double x, double y, BType t, int quantity) {
		this.x = x;
		this.y = y;
		this.t = t;
		this.quantity = quantity;
		if (t == BType.PISTOL || t == BType.SCATTER || t == BType.MACHINE) sprite = Sprite.bullets;
		if (t == BType.ROCKET) sprite = Sprite.rocket;
	}
	
	public void tick() {
	}

	public void render(Screen screen) {
		screen.renderMob((int) x + 2, (int) y + 2, sprite);
	}
}
