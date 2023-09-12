package com.detrivos.auto.entity.spawners;

import java.util.List;

import com.detrivos.auto.Game;
import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.level.Level;

public class TurretSpawner extends Spawner {

	private double respawn = 800;
	private double changeDif = 0;
	private int tier = 0;
	
	boolean changed = false;
	
	public TurretSpawner(int x, int y, int amount, Level level) {
		super (x, y, Type.MOB, amount, level);
		tick();
	}
	
	public void tick() {
		List<Entity> ents = level.getEntities(this, 6 * 16);
		List<Player> players = level.getPlayers(this, 7 * 16);
		respawn++;
		changeDif = (Game.chal.dif / 4.0);
		if (Game.chal.dif > 3) {
			if (respawn > (501 * changeDif) && ents.size() == 1) respawn = 1;
		} else {
			if (respawn > (501) && ents.size() == 1) respawn = 1;
		}
		if (ents.size() == 1 && players.size() > 0) {
			if (Game.chal.dif > 3) {
				if (respawn % (500 * changeDif) == 0 || respawn > (500 * changeDif)) {
					spawn();
				}
			} else {
				if (respawn % (500) == 0 || respawn > (500)) {
					spawn();
				}
			}
		}
	}
	
	private void spawn() {
		for (int i = 0; i < Game.chal.dif; i++) {
			if (Game.chal.dif < 2) level.add(new Turret((int) (this.x / 16) + xOffset(5), (int) (this.y / 16) + yOffset(5), Turret.Type.NORMAL));
			else if (random.nextInt(2) == 0) level.add(new Turret((int) (this.x / 16) + xOffset(5), (int) (this.y / 16) + yOffset(5), Turret.Type.NORMAL));
			int mc = 0;
			if (Game.chal.dif < 10) mc = (int) (11 - Game.chal.dif);
			else mc = 1;
			if (random.nextInt(mc) == 0) {
				level.add(new Turret((int) (this.x / 16) + xOffset(5), (int) (this.y / 16) + yOffset(5), Turret.Type.MACHINE));
			}
			int rc = 0;
			if (Game.chal.dif < 20) rc = (int) (21 - Game.chal.dif);
			else rc = 1;
			if (random.nextInt(rc) == 0) {
				level.add(new Turret((int) (this.x / 16) + xOffset(5), (int) (this.y / 16) + yOffset(5), Turret.Type.ROCKET));
			}
		}
	}
}
