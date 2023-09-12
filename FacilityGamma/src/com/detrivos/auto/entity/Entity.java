package com.detrivos.auto.entity;

import java.util.Random;

import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.level.Level;

public abstract class Entity {

	protected double x, y;
	private boolean removed = false;
	protected Sprite sprite;
	protected Level level;
	protected final Random random = new Random();
	
	public void tick() {
	}
	
	public void render(Screen screen) {
	}
	
	public void setRemove(boolean remove) {
		if (remove == false) removed = false;
		else removed = true;
	}
	
	public void remove() {
		//Remove from level
		removed = true;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void init(Level level) {
		this.level = level;
	}
}
