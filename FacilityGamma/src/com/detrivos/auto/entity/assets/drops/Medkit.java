package com.detrivos.auto.entity.assets.drops;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class Medkit extends Entity {
	
	public boolean health = false;
	public int hp;
	
	public enum Tier {
		LOW, MID, HIGH
	}
	
	public Tier t;

	public Medkit(int x, int y, Tier t) {
		this.x = (x << 4) + 5;
		this.y = (y << 4) + 5;
		this.t = t;
		if (t == Tier.LOW) {
			hp = 15;
			sprite = Sprite.medkit;
		} else if (t == Tier.MID) {
			hp = 40;
			sprite = Sprite.medkitMid;
		} else if (t == Tier.HIGH) {
			hp = 65;
			sprite = Sprite.medkitHigh;
		}
	}
	
	public void tick() {
	}

	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, sprite, this);
	}
}
