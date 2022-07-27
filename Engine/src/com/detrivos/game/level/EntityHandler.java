package com.detrivos.game.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.Random;

import javax.imageio.ImageIO;

import org.joml.*;

import com.detrivos.eng.handle.Handler;
import com.detrivos.eng.render.shader.EntityShader;
import com.detrivos.entity.*;
import com.detrivos.entity.projectile.Projectile;

public class EntityHandler {
	
	private Random random = new Random();
	
	private EntityShader shader;
	private Level level;

	public List<Projectile> projs = new ArrayList<Projectile>();
	public List<Entity> ents = new ArrayList<Entity>();
	
	private List<Vector3f> projRendered = new ArrayList<Vector3f>();
	
	public EntityHandler(Level level) {
		this.level = level;
		shader = new EntityShader("entityShader");
	}
	
	public void generatePlayerPosition(int width, int height) {
		Vector2i pos = chooseRandomLocation(width, height);
		pos = new Vector2i(8, 10);
		Handler.player = new Player(new Vector3f(pos.x * 2, -pos.y * 2, 0), level);
	}//11,4
	
	public void generateMonsters(int width, int height, int amt) {
		Vector2i pos = new Vector2i();
		for (int i = 0; i < amt; i++) {
			pos = chooseRandomLocation(width, height);
			while (level.getTilesAt(pos.x, pos.y).isSolid()) pos = chooseRandomLocation(width, height);
			//add(new Dummy(new Vector3f(pos.x * 2, -pos.y * 2, 0), level));
		}
	}
	
	public void generateMonsters(int width, int height, int xPos, int yPos) {
		Vector2i pos = new Vector2i(xPos, yPos);
		//add(new Dummy(new Vector3f(pos.x * 2, -pos.y * 2, 0), level));
	}
	
	public void loadEntitySheet(String path) {
		try {
			BufferedImage lvl = ImageIO.read(Level.class.getResource("/levels/entities/" + path + ".png"));
			int w = lvl.getWidth();
			int h = lvl.getHeight();
			
			int[] colSheet = lvl.getRGB(0, 0, w, h, null, 0, w);
			
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int pixel = colSheet[x + y * w];
					
					switch (pixel) {
					case 0xFFFF00FF:
						Handler.player = new Player(new Vector3f(x * 2, -y * 2, 0), level);
						break;
					case 0xFF00FF00:
						//add(new Dummy(new Vector3f(x * 2, -y * 2, 0), level));
						break;
					case 0xFFFFFFFF://walkable tile
						break;
					case 0xFF000000://solid tile
						break;
					default:
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void add(Entity e) {
		if (e instanceof Projectile) projs.add((Projectile) e);
		else ents.add(e);
	}
	
	public void remove() {
		for (int i = 0; i < ents.size(); i++) if (ents.get(i).isRemoved()) ents.remove(i);
		for (int i = 0; i < projs.size(); i++) {
			if (projs.size() >= 256) projs.get(i).remove();
			if (projs.get(i).isRemoved()) projs.remove(i);
		}
	}
	
	public void tick(float delta) {
		for (int i = 0; i < ents.size(); i++) ents.get(i).tick(delta);
		for (int i = 0; i < projs.size(); i++) projs.get(i).tick(delta);
		remove();
	}
	
	public void render() {
		renderProjectiles();
		for (int i = 0; i < ents.size(); i++) ents.get(i).render(shader);
	}
	
	public void renderProjectiles() {
		for (int i = 0; i < projs.size(); i++) {
			if (projRendered.isEmpty()) {
				projs.get(i).render(shader);
				projRendered.add(projs.get(i).getPos());
			} else {
				boolean newLoc = true;
				for (int j = 0; j < projRendered.size(); j++) {
					if (!isLocationNew(projs.get(i).getPos(), projRendered.get(j))) {
						newLoc = false;
						break;
					}
				}
				if (newLoc) {
					projs.get(i).render(shader);
					projRendered.add(projs.get(i).getPos());
				}
			}
		}
		for (int i = 0; i < projs.size(); i++) projs.get(i).render(shader);
	}
	
	private Vector2i chooseRandomLocation(int width, int height) {
		return new Vector2i(random.nextInt(width), random.nextInt(height));
	}
	
	private boolean isLocationNew(Vector3f a, Vector3f b) {
		if ((a.x == b.x) && (a.y == b.y)) return false;
		return true;
	}
}
