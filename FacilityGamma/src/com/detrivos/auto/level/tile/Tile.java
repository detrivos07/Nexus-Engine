package com.detrivos.auto.level.tile;

import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.graphics.SpriteSheet;

public class Tile {

	public int x, y;
	public Sprite sprite;
	
	public static Tile dirt = new DirtTile(Sprite.dirt);
	public static Tile grass = new DirtTile(Sprite.grass);
	
	public static Tile dirtSR = new DirtTile(Sprite.dirtSR);
	public static Tile dirtMR = new DirtTile(Sprite.dirtMR);
	public static Tile dirtLR = new DirtTile(Sprite.dirtLR);
	
	public static Tile outhouse = new OuthouseTile(Sprite.outhouse);
	
	public static Tile grassFadeL = new DirtTile(Sprite.grassFadeL);
	public static Tile grassFadeT = new DirtTile(Sprite.grassFadeT);
	public static Tile grassFadeR = new DirtTile(Sprite.grassFadeR);
	public static Tile grassFadeB = new DirtTile(Sprite.grassFadeB);
	
	public static Tile grassFadeILT = new DirtTile(Sprite.grassFadeILT);
	public static Tile grassFadeIRT = new DirtTile(Sprite.grassFadeIRT);
	public static Tile grassFadeIRB = new DirtTile(Sprite.grassFadeIRB);
	public static Tile grassFadeILB = new DirtTile(Sprite.grassFadeILB);
	
	public static Tile grassFadeOLT = new DirtTile(Sprite.grassFadeOLT);
	public static Tile grassFadeORT = new DirtTile(Sprite.grassFadeORT);
	public static Tile grassFadeORB = new DirtTile(Sprite.grassFadeORB);
	public static Tile grassFadeOLB = new DirtTile(Sprite.grassFadeOLB);
	
	public static Tile sGrass = new DirtTile(Sprite.sGrass);
	
	public static Tile voidTile = new WallTile(Sprite.voidSprite);
	
	public static Tile toCryo = new ToCryoTile(Sprite.whiteFloor);
	public static Tile toTurHall = new ToTurHallTile(Sprite.whiteFloor);
	public static Tile toturhall2 = new ToTurHall2Tile(Sprite.whiteFloor);
	public static Tile toturhall3 = new ToTurHall3Tile(Sprite.whiteFloor);
	public static Tile tothft = new ToTurHall3Tile(Sprite.mrtb);
	public static Tile toleech1 = new ToLeech1Tile(Sprite.dirtLR);
	
	//tunnel SS
	public static Tile dirtTLC = new DirtTile(Sprite.dirtTLC);
	public static Tile dirtTRC = new DirtTile(Sprite.dirtTRC);
	public static Tile dirtBRC = new DirtTile(Sprite.dirtBRC);
	public static Tile dirtBLC = new DirtTile(Sprite.dirtBLC);
	
	public static Tile dirtLeft = new DirtTile(Sprite.dirtLeft);
	public static Tile dirtRight = new DirtTile(Sprite.dirtRight);
	public static Tile dirtDown = new DirtTile(Sprite.dirtDown);
	public static Tile dirtTop = new DirtTile(Sprite.dirtTop);
	
	public static Tile dirtWall = new WallTile(Sprite.dirtWall);
	
	public static Tile brt = new DirtTile(Sprite.brt);
	public static Tile brl = new DirtTile(Sprite.brl);
	
	public static Tile mrtb = new DirtTile(Sprite.mrtb);
	public static Tile mrr = new DirtTile(Sprite.mrr);
	
	public static Tile srtl = new DirtTile(Sprite.srtl);
	public static Tile srt = new DirtTile(Sprite.srt);
	
	
	//base spritesheet
	public static Tile fw1 = new FloorTile(Sprite.fw1);
	public static Tile fw2 = new FloorTile(Sprite.fw2);
	public static Tile fw3 = new FloorTile(Sprite.fw3);
	public static Tile fw4 = new FloorTile(Sprite.fw4);
	
	public static Tile fb1 = new FloorTile(Sprite.fb1);
	public static Tile fb2 = new FloorTile(Sprite.fb2);
	
	public static Tile wdt = new WallTile(Sprite.wdt);
	public static Tile wdb = new WallTile(Sprite.wdb);
	
	public static Tile tloc = new WallTile(Sprite.tloc);
	public static Tile troc = new WallTile(Sprite.troc);
	public static Tile bloc = new WallTile(Sprite.bloc);
	public static Tile broc = new WallTile(Sprite.broc);
	
	public static Tile bric = new WallTile(Sprite.bric);
	public static Tile blic = new WallTile(Sprite.blic);
	public static Tile tric = new WallTile(Sprite.tric);
	public static Tile tlic = new WallTile(Sprite.tlic);
	
	public static Tile bench = new SandbagTile(Sprite.bench);
	public static Tile crate1 = new WallTile(Sprite.crate1);
	
	public static Tile tlw = new LockerTile(Sprite.tlw, false, false);
	public static Tile tlcw = new LockerTile(Sprite.tclw, true, false);
	public static Tile blw = new LockerTile(Sprite.blw, false, false);
	public static Tile blgw = new LockerTile(Sprite.bglw, false, true);
	
	public static Tile rw = new WallTile(Sprite.rw);
	public static Tile tw = new WallTile(Sprite.tw);
	public static Tile lw = new WallTile(Sprite.lw);
	public static Tile bw = new WallTile(Sprite.bw);
	
	public static Tile ble = new WallTile(Sprite.ble);
	public static Tile bre = new WallTile(Sprite.bre);
	public static Tile tle = new WallTile(Sprite.tle);
	public static Tile tre = new WallTile(Sprite.tre);
	
	public static Tile ltd = new DoorTile(Sprite.ltd);
	public static Tile lbd = new DoorTile(Sprite.lbd);
	
	public static Tile whiteFloor = new FloorTile(Sprite.whiteFloor);
	public static Tile blueFloor = new FloorTile(Sprite.blueFloor);
	
	public static Tile changeFloor = new ChangeTile(Sprite.whiteFloor);
	public static Tile shotgun = new ShotgunTile(Sprite.blueFloor);
	
	public static Tile pFloor = new PlayerTile(Sprite.whiteFloor);
	public static Tile dFloor = new DeadTile(Sprite.whiteFloor);
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
	}
	
	public boolean solid() {
		return false;
	}
	
	public boolean bulletSolid() {
		return false;
	}

	public boolean hasGun() {
		return false;
	}
	
	public boolean hasClothes() {
		return false;
	}
}
