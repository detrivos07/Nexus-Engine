package com.detrivos.auto.level;

import java.util.ArrayList;
import java.util.List;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.assets.CryoPod;
import com.detrivos.auto.entity.assets.Door;
import com.detrivos.auto.entity.assets.Landmine;
import com.detrivos.auto.entity.assets.Leecher;
import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.entity.assets.drops.BulletDrops;
import com.detrivos.auto.entity.assets.drops.Medkit;
import com.detrivos.auto.entity.assets.story.Note;
import com.detrivos.auto.entity.assets.story.ShotgunNPC;
import com.detrivos.auto.entity.utils.HealthBar;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.level.tile.Tile;
import com.detrivos.auto.projectile.Projectile;
import com.detrivos.auto.projectile.Rocket;

public class Level {

	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<HealthBar> bars = new ArrayList<HealthBar>();
	private List<Player> players = new ArrayList<Player>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Entity> drops = new ArrayList<Entity>();
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel() {
	}
	
	protected void loadLevel(String path) {
	}
	
	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
		for (int i = 0; i < drops.size(); i++) {
			drops.get(i).tick();
		}
		for (int i = 0; i < bars.size(); i++) {
			bars.get(i).tick();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).tick();
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).tick();
		}
		remove();
	}
	
	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < drops.size(); i++) {
			if (drops.get(i).isRemoved()) drops.remove(i);
		}
		for (int i = 0; i < bars.size(); i++) {
			if (bars.get(i).isRemoved()) bars.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
		}
	}
	
	public void removeAll() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
			entities.get(i).remove();
		}
		for (int i = 0; i < drops.size(); i++) {
			if (drops.get(i).isRemoved()) drops.remove(i);
			drops.get(i).remove();
		}
		for (int i = 0; i < bars.size(); i++) {
			if (bars.get(i).isRemoved()) bars.remove(i);
			bars.get(i).remove();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
			projectiles.get(i).remove();
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
			players.get(i).remove();
		}
	}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size + xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).bulletSolid()) solid = true;
		}
		return solid;
	}
	
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Player) {
			players.add((Player) e);
		} else if (e instanceof HealthBar) {
			bars.add((HealthBar) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Medkit || e instanceof BulletDrops){
			drops.add(e);
		} else {
			entities.add(e);
		}
	}
	
	public void remove(Entity e) {
		if (e instanceof Player) {
			players.remove((Player) e);
		} else if (e instanceof HealthBar) {
			bars.remove((HealthBar) e);
		} else if (e instanceof Projectile) {
			projectiles.remove((Projectile) e);
		} else {
			entities.remove(e);
		}
	}
	
	public List<Player> getPlayers() {
		return players;
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public List<Entity> getEntities() {
		return entities;
	}
	
	public List<HealthBar> getBars() {
		return bars;
	}
	
	public List<Entity> getDrops(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.getX() + 8;
		int ey = (int) e.getY() + 8;
		for (int i = 0; i < drops.size(); i++) {
			Entity entity = drops.get(i);
			int x , y;
			if (entity instanceof BulletDrops || entity instanceof Medkit) {
				x = (int) entity.getX() + 5;
				y = (int) entity.getY() + 5;
			} else {
				x = (int) entity.getX();
				y = (int) entity.getY();
			}
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) result.add(entity);
		}
		return result;
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex, ey;
		if (e instanceof Landmine) {
			ex = (int) e.getX() + 4;
			ey = (int) e.getY() + 4;
		} else {
			ex = (int) e.getX() + 8;
			ey = (int) e.getY() + 8;
		}
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x , y;
			if (entity instanceof Leecher) {
				x = (int) entity.getX() + 8;
				y = (int) entity.getY() + 8;
			} else if (entity instanceof Landmine) {
				x = (int) entity.getX() + 4;
				y = (int) entity.getY() + 4;
			} else if (entity instanceof Note) {
				x = (int) entity.getX() + 5;
				y = (int) entity.getY() + 5;
			} else if (entity instanceof BulletDrops) {
				x = (int) entity.getX() + 5;
				y = (int) entity.getY() + 5;
			} else {
				x = (int) entity.getX();
				y = (int) entity.getY();
			}
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) result.add(entity);
		}
		return result;
	}
	
	public List<Projectile> getProjectiles(Entity e, int radius) {
		List<Projectile> result = new ArrayList<Projectile>();
		int ex, ey;
		if (e instanceof Landmine) {
			ex = (int) e.getX() + 4;
			ey = (int) e.getY() + 4;
		} else if (e instanceof CryoPod) {
			ex = (int) e.getX() + 13;
			ey = (int) e.getY() + 13;
		} else {
			ex = (int) e.getX() + 8;
			ey = (int) e.getY() + 8;
		}
		for (int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			int x, y;
			if (p instanceof Rocket) {
				x = (int) (p.getX() + 2.5);
				y = (int) (p.getY() + 2.5);
			} else {
				x = (int) p.getX();
				y = (int) p.getY();
			}
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) result.add(p);
		}
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex, ey;
		if (e instanceof ShotgunNPC) {
			ex = (int) e.getX() + 13;
			ey = (int) e.getY() + 13;
		} else if (e instanceof Landmine) {
			ex = (int) e.getX() + 4;
			ey = (int) e.getY() + 4;
		} else if (e instanceof Door) {
			ex = (int) e.getX() + 16;
			ey = (int) e.getY() + 16;
		} else {
			ex = (int) e.getX() + 8;
			ey = (int) e.getY() + 8;
		}
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = (int) player.getX() + 8;
			int y = (int) player.getY() + 8;
			
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			
			double dist = Math.sqrt((dx * dx) + (dy * dy));
			if (dist <= radius) result.add(player);
		}
		return result;
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for(int y = y0; y < y1; y++) {
			for(int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < drops.size(); i++) {
			drops.get(i).render(screen);
		}
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < bars.size(); i++) {
			bars.get(i).render(screen);
		}
	}
	
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.voidTile;
		/*
		if (tiles[x + y * width] == 0xFF00FF00) return Tile.grass;
		
		if (tiles[x + y * width] == 0xFF003333) return Tile.outhouse;
		
		if (tiles[x + y * width] == 0xFF009900) return Tile.grassFadeR;
		if (tiles[x + y * width] == 0xFF00B200) return Tile.grassFadeL;
		if (tiles[x + y * width] == 0xFF00CC00) return Tile.grassFadeB;
		if (tiles[x + y * width] == 0xFF00E500) return Tile.grassFadeT;
		
		if (tiles[x + y * width] == 0xFF00FFFF) return Tile.grassFadeILT;
		if (tiles[x + y * width] == 0xFF00E5E5) return Tile.grassFadeIRT;
		if (tiles[x + y * width] == 0xFF00CCCC) return Tile.grassFadeIRB;
		if (tiles[x + y * width] == 0xFF00B2B2) return Tile.grassFadeILB;
		
		if (tiles[x + y * width] == 0xFF0000FF) return Tile.grassFadeOLT;
		if (tiles[x + y * width] == 0xFF0000E5) return Tile.grassFadeORT;
		if (tiles[x + y * width] == 0xFF0000CC) return Tile.grassFadeORB;
		if (tiles[x + y * width] == 0xFF0000B2) return Tile.grassFadeOLB;*/
		
		if (tiles[x + y * width] == 0xFF71426D) return Tile.dirt;
		
		if (tiles[x + y * width] == 0xFF71181C) return Tile.dirtSR;
		if (tiles[x + y * width] == 0xFF891E23) return Tile.dirtMR;
		if (tiles[x + y * width] == 0xFFA3232A) return Tile.dirtLR;
		
		//tunnel SS
		if (tiles[x + y * width] == 0xFF71FF6D) return Tile.dirtTLC;
		if (tiles[x + y * width] == 0xFF67E562) return Tile.dirtTRC;
		if (tiles[x + y * width] == 0xFF5DCC57) return Tile.dirtBRC;
		if (tiles[x + y * width] == 0xFF53B24C) return Tile.dirtBLC;
		
		if (tiles[x + y * width] == 0xFF779EFF) return Tile.dirtLeft;
		if (tiles[x + y * width] == 0xFF6B90E5) return Tile.dirtRight;
		if (tiles[x + y * width] == 0xFF5F82CC) return Tile.dirtDown;
		if (tiles[x + y * width] == 0xFF5373B2) return Tile.dirtTop;
		
		if (tiles[x + y * width] == 0xFF71421C) return Tile.dirtWall;
		
		if (tiles[x + y * width] == 0xFFDEA5FF) return Tile.brt;
		if (tiles[x + y * width] == 0xFFC895E5) return Tile.brl;
		
		if (tiles[x + y * width] == 0xFF718F1C) return Tile.mrtb;
		if (tiles[x + y * width] == 0xFFB286CC) return Tile.mrr;
		
		if (tiles[x + y * width] == 0xFF9B75B2) return Tile.srtl;
		if (tiles[x + y * width] == 0xFF856499) return Tile.srt;
		
		//base spritesheet
		
		if (tiles[x + y * width] == 0xFFFFFFFF) return Tile.whiteFloor;
		if (tiles[x + y * width] == 0xFFC4DDED) return Tile.blueFloor;
		if (tiles[x + y * width] == 0xFFCF82FF) return Tile.changeFloor;
		if (tiles[x + y * width] == 0xFF9ACCEA) return Tile.shotgun;
		
		if (tiles[x + y * width] == 0xFF0A0000) return Tile.wdt;
		if (tiles[x + y * width] == 0xFF140000) return Tile.wdb;
		
		if (tiles[x + y * width] == 0xFF00E1EC) return Tile.fb1;
		if (tiles[x + y * width] == 0xFF00E1CD) return Tile.fb2;
		
		if (tiles[x + y * width] == 0xFF00FF00) return Tile.fw1;
		if (tiles[x + y * width] == 0xFF00F500) return Tile.fw2;
		if (tiles[x + y * width] == 0xFF00EB00) return Tile.fw3;
		if (tiles[x + y * width] == 0xFF00E100) return Tile.fw4;
		
		if (tiles[x + y * width] == 0xFF503C00) return Tile.dirtLR;
		if (tiles[x + y * width] == 0xFF5A3C00) return Tile.dirtMR;
		if (tiles[x + y * width] == 0xFF643C00) return Tile.dirtSR;
		if (tiles[x + y * width] == 0xFFFFD800) return Tile.sGrass;
		
		if (tiles[x + y * width] == 0xFFC4784A) return Tile.toTurHall;
		if (tiles[x + y * width] == 0xFFA3FBFF) return Tile.toCryo;
		if (tiles[x + y * width] == 0xFFA93AFF) return Tile.toturhall2;
		if (tiles[x + y * width] == 0xFFC862EA) return Tile.toturhall3;
		if (tiles[x + y * width] == 0xFF4F631B) return Tile.tothft;
		if (tiles[x + y * width] == 0xFFDDD199) return Tile.toleech1;
		
		if (tiles[x + y * width] == 0xFF1A00E0) return Tile.dFloor;
		if (tiles[x + y * width] == 0xFF6500E0) return Tile.pFloor;
		
		if (tiles[x + y * width] == 0xFF070707) return Tile.tloc;
		if (tiles[x + y * width] == 0xFF0F0F0F) return Tile.troc;
		if (tiles[x + y * width] == 0xFF141414) return Tile.bloc;
		if (tiles[x + y * width] == 0xFF191919) return Tile.broc;
		
		if (tiles[x + y * width] == 0xFFF7F7F7) return Tile.bric;
		if (tiles[x + y * width] == 0xFFEDEDED) return Tile.blic;
		if (tiles[x + y * width] == 0xFFE2E2E2) return Tile.tric;
		if (tiles[x + y * width] == 0xFFD8D8D8) return Tile.tlic;
		
		if (tiles[x + y * width] == 0xFF7F3300) return Tile.bench;
		if (tiles[x + y * width] == 0xFFA52A2A) return Tile.crate1;
		
		if (tiles[x + y * width] == 0xFF69777F) return Tile.tlw;
		if (tiles[x + y * width] == 0xFF697761) return Tile.tlcw;
		
		if (tiles[x + y * width] == 0xFF6E95AD) return Tile.blw;
		if (tiles[x + y * width] == 0xFF6E9555) return Tile.blgw;
		
		if (tiles[x + y * width] == 0xFF4F4F4F) return Tile.rw;
		if (tiles[x + y * width] == 0xFF636363) return Tile.tw;
		if (tiles[x + y * width] == 0xFF545454) return Tile.lw;
		if (tiles[x + y * width] == 0xFF686868) return Tile.bw;
		
		if (tiles[x + y * width] == 0xFFD1D1D1) return Tile.ble;
		if (tiles[x + y * width] == 0xFFC6C6C6) return Tile.bre;
		if (tiles[x + y * width] == 0xFFBCBCBC) return Tile.tle;
		if (tiles[x + y * width] == 0xFFB2B2B2) return Tile.tre;
		
		if (tiles[x + y * width] == 0xFF878C91) return Tile.ltd;
		if (tiles[x + y * width] == 0xFF8E9499) return Tile.lbd;
		
		if (tiles[x + y * width] == 0xFF000000) return Tile.voidTile;
		return Tile.voidTile;
	}
}
