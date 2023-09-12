package com.detrivos.auto.entity.spawners;

import java.util.List;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.assets.Leecher;
import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.level.Level;

public class LeecherSpawner extends Spawner {

	private int respawn = 0;
	private int health;
	
	public LeecherSpawner(int x, int y, int amount, int tier, Level level) {
		super (x, y, Type.MOB, amount, level);
		
		switch (tier) {
		case (1) :
			health = 100;
			break;
		case (2) :
			health = 125;
			break;
		default :
			health = 100;
			break;
		}
		for (int i = 0; i < amount; i++) tick();
	}
	
	public void tick() {
		respawn++;
		if (respawn > 3000) respawn = 0;
		List<Entity> ents = level.getEntities(this, 5 * 16);
		List<Player> players = level.getPlayers(this, 7 * 16);
		if (ents.size() < 7 && players.size() > 0) {
			if (respawn % 1000 == 0) {
				level.add(new Leecher((int) (x + xOffset(4)), (int) (y + yOffset(4))));
			}
		}
	}
}
