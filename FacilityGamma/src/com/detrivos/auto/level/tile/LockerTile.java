package com.detrivos.auto.level.tile;

import com.detrivos.auto.graphics.Sprite;

public class LockerTile extends Tile {

	private boolean clothes, gun;
	
	public LockerTile(Sprite sprite, boolean clothes, boolean gun) {
		super(sprite);
		this.clothes = clothes;
		this.gun = gun;
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
