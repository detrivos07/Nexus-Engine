package com.detrivos.auto.level.tile;

import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class LockerTile extends Tile {

	private boolean clothes, gun;
	
	public LockerTile(Sprite sprite, boolean clothes, boolean gun) {
		super(sprite);
		this.clothes = clothes;
		this.gun = gun;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	public boolean solid() {
		return true;
	}
	
	public boolean bulletSolid() {
		return true;
	}
	
	public boolean hasGun() {
		return gun;
	}
	
	public boolean hasClothes() {
		return clothes;
	}
}
