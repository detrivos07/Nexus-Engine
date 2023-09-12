package com.detrivos.auto.entity.utils;

import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.graphics.Screen;

public class BulletBar {

	public int width, height;
	public int col;
	private int x, y;
	private Player p;
	
	public enum Bullet {
		PISTOL, SCATTER, MACHINE, ROCKET, LANDMINE
	}
	
	private Bullet t;
	
	public BulletBar(Player p, Bullet t,  int width, int height, int x, int y) {
		col = 0xFF606060;
		this.p = p;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.t= t;
	}
	
	public void tick() {
		switch (t) {
		case PISTOL :
			changePB();
			break;
		case SCATTER :
			changeSB();
			break;
		case MACHINE :
			changeMB();
			break;
		case ROCKET :
			changeR();
			break;
		case LANDMINE :
			changeLM();
			break;
		default :
			break;
		}
	}
	
	private void changePB() {
		int h1 = height;
		this.height = (int) (p.pistolBullets * 21) / 500;
		int h2 = height;
		if (h2 - h1 != 0) this.y -= h2 - h1;
	}
	
	private void changeSB() {
		int h1 = height;
		this.height = (int) (p.scatterBullets * 21) / 500;
		int h2 = height;
		if (h2 - h1 != 0) this.y -= h2 - h1;
	}
	
	private void changeMB() {
		int h1 = height;
		this.height = (int) (p.machineBullets * 21) / 500;
		int h2 = height;
		if (h2 - h1 != 0) this.y -= h2 - h1;
	}
	
	private void changeR() {
		int h1 = height;
		this.height = (int) (p.rockets * 21) / 500;
		int h2 = height;
		if (h2 - h1 != 0) this.y -= h2 - h1;
	}
	
	private void changeLM() {
		int h1 = height;
		this.height = (int) (p.pistolBullets * 21) / 500;
		int h2 = height;
		if (h2 - h1 != 0) this.y -= h2 - h1;
	}
	
	public void render(Screen screen) {
		screen.renderBulletBar(x, y, this);
	}
}
