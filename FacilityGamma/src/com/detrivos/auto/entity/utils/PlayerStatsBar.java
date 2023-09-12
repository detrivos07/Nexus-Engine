package com.detrivos.auto.entity.utils;

import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.graphics.Screen;

public class PlayerStatsBar {
	
	public int width, height;
	public int col;
	private int x, y;
	private Player p;
	
	public enum Type {
		HEALTH, STAMINA
	}
	
	private Type t;
	
	public PlayerStatsBar(Player p, Type t, int col, int width, int height, int x, int y) {
		this.p = p;
		this.t= t;
		this.col = col;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}
	
	public void tick() {
		switch (t) {
		case HEALTH :
			changeHealth();
			break;
		case STAMINA :
			changeStamina();
			break;
		default :
			break;
		}
	}
	
	private void changeHealth() {
		int h1 = height;
		this.height = (int) (p.health * 90) / 100;
		if (p.health > 0 && this.height == 0) this.height = 1;
		int h2 = height;
		if (h2 - h1 != 0) this.y -= h2 - h1;
	}
	
	private void changeStamina() {
		int h1 = height;
		this.height = (int) (p.stamina * 90) / 100;
		if (p.stamina > 0 && this.height == 0) this.height = 1;
		int h2 = height;
		if (h2 - h1 != 0) this.y -= h2 - h1;
	}
	
	public void render(Screen screen) {
		screen.renderPStatsBar(x, y, this);
	}
}
