package com.detrivos.auto.level.tile;

import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;
	private boolean solid = false, bsolid = false;
	
	public static Tile dirt = new Tile(Sprite.dirt);
	public static Tile grass = new Tile(Sprite.grass);
	
	public static Tile dirtSR = new Tile(Sprite.dirtSR);
	public static Tile dirtMR = new Tile(Sprite.dirtMR);
	public static Tile dirtLR = new Tile(Sprite.dirtLR);
	
	public static Tile outhouse = new Tile(Sprite.outhouse).setSolid();
	
	public static Tile grassFadeL = new Tile(Sprite.grassFadeL);
	public static Tile grassFadeT = new Tile(Sprite.grassFadeT);
	public static Tile grassFadeR = new Tile(Sprite.grassFadeR);
	public static Tile grassFadeB = new Tile(Sprite.grassFadeB);
	
	public static Tile grassFadeILT = new Tile(Sprite.grassFadeILT);
	public static Tile grassFadeIRT = new Tile(Sprite.grassFadeIRT);
	public static Tile grassFadeIRB = new Tile(Sprite.grassFadeIRB);
	public static Tile grassFadeILB = new Tile(Sprite.grassFadeILB);
	
	public static Tile grassFadeOLT = new Tile(Sprite.grassFadeOLT);
	public static Tile grassFadeORT = new Tile(Sprite.grassFadeORT);
	public static Tile grassFadeORB = new Tile(Sprite.grassFadeORB);
	public static Tile grassFadeOLB = new Tile(Sprite.grassFadeOLB);
	
	public static Tile sGrass = new Tile(Sprite.sGrass);
	
	public static Tile voidTile = new Tile(Sprite.voidSprite).setSolid();
	
	public static Tile toCryo = new Tile(Sprite.whiteFloor);
	public static Tile toTurHall = new Tile(Sprite.whiteFloor);
	public static Tile toturhall2 = new Tile(Sprite.whiteFloor);
	public static Tile toturhall3 = new Tile(Sprite.whiteFloor);
	public static Tile tothft = new Tile(Sprite.mrtb);
	public static Tile toleech1 = new Tile(Sprite.dirtLR);
	
	//tunnel SS
	public static Tile dirtTLC = new Tile(Sprite.dirtTLC);
	public static Tile dirtTRC = new Tile(Sprite.dirtTRC);
	public static Tile dirtBRC = new Tile(Sprite.dirtBRC);
	public static Tile dirtBLC = new Tile(Sprite.dirtBLC);
	
	public static Tile dirtLeft = new Tile(Sprite.dirtLeft);
	public static Tile dirtRight = new Tile(Sprite.dirtRight);
	public static Tile dirtDown = new Tile(Sprite.dirtDown);
	public static Tile dirtTop = new Tile(Sprite.dirtTop);
	
	public static Tile dirtWall = new Tile(Sprite.dirtWall).setSolid();
	
	public static Tile brt = new Tile(Sprite.brt);
	public static Tile brl = new Tile(Sprite.brl);
	
	public static Tile mrtb = new Tile(Sprite.mrtb);
	public static Tile mrr = new Tile(Sprite.mrr);
	
	public static Tile srtl = new Tile(Sprite.srtl);
	public static Tile srt = new Tile(Sprite.srt);
	
	
	//base spritesheet
	public static Tile fw1 = new Tile(Sprite.fw1);
	public static Tile fw2 = new Tile(Sprite.fw2);
	public static Tile fw3 = new Tile(Sprite.fw3);
	public static Tile fw4 = new Tile(Sprite.fw4);
	
	public static Tile fb1 = new Tile(Sprite.fb1);
	public static Tile fb2 = new Tile(Sprite.fb2);
	
	public static Tile wdt = new Tile(Sprite.wdt).setSolid();
	public static Tile wdb = new Tile(Sprite.wdb).setSolid();
	
	public static Tile tloc = new Tile(Sprite.tloc).setSolid();
	public static Tile troc = new Tile(Sprite.troc).setSolid();
	public static Tile bloc = new Tile(Sprite.bloc).setSolid();
	public static Tile broc = new Tile(Sprite.broc).setSolid();
	
	public static Tile bric = new Tile(Sprite.bric).setSolid();
	public static Tile blic = new Tile(Sprite.blic).setSolid();
	public static Tile tric = new Tile(Sprite.tric).setSolid();
	public static Tile tlic = new Tile(Sprite.tlic).setSolid();
	
	public static Tile bench = new Tile(Sprite.bench).setSolid();
	public static Tile crate1 = new Tile(Sprite.crate1).setSolid().setBulletSolid();
	
	public static Tile tlw = new LockerTile(Sprite.tlw, false, false).setSolid();
	public static Tile tlcw = new LockerTile(Sprite.tclw, true, false).setSolid();
	public static Tile blw = new LockerTile(Sprite.blw, false, false).setSolid();
	public static Tile blgw = new LockerTile(Sprite.bglw, false, true).setSolid();
	
	public static Tile rw = new Tile(Sprite.rw).setSolid();
	public static Tile tw = new Tile(Sprite.tw).setSolid();
	public static Tile lw = new Tile(Sprite.lw).setSolid();
	public static Tile bw = new Tile(Sprite.bw).setSolid();
	
	public static Tile ble = new Tile(Sprite.ble).setSolid();
	public static Tile bre = new Tile(Sprite.bre).setSolid();
	public static Tile tle = new Tile(Sprite.tle).setSolid();
	public static Tile tre = new Tile(Sprite.tre).setSolid();
	
	public static Tile ltd = new Tile(Sprite.ltd).setSolid();
	public static Tile lbd = new Tile(Sprite.lbd).setSolid();
	
	public static Tile whiteFloor = new Tile(Sprite.whiteFloor);
	public static Tile blueFloor = new Tile(Sprite.blueFloor);
	
	public static Tile changeFloor = new ChangeTile(Sprite.whiteFloor);
	public static Tile shotgun = new Tile(Sprite.blueFloor).setSolid();
	
	public static Tile pFloor = new Tile(Sprite.whiteFloor).setSolid();
	public static Tile dFloor = new Tile(Sprite.whiteFloor).setSolid();
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
	
	protected Tile setBulletSolid() {
		bsolid = true;
		return this;
	}
	
	protected Tile setSolid() {
		this.solid = true;
		return this.setBulletSolid();
	}
	
	public boolean solid() {
		return solid;
	}
	
	public boolean bulletSolid() {
		return bsolid;
	}

	public boolean hasGun() {
		return false;
	}
	
	public boolean hasClothes() {
		return false;
	}
}
