package com.detrivos.auto.entity.assets.story;

import com.detrivos.auto.entity.Mob;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class Note extends Mob {
	
	public enum Type {
		CONTROLS, CRYO
	}
	
	public Type t;
	
	public Note(int x, int y, Type t, Sprite sprite) {
		this.t = t;
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void tick() {
	}

	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, sprite, this);
	}
}
