package artifice.engine.level.entity;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import artifice.engine.level.Level;
import nexus.engine.core.collision.AABB;
import nexus.engine.core.collision.AABB.Collision;
import nexus.engine.core.render.opengl.Material;
import nexus.engine.core.render.opengl.Texture;

public class Projectile extends Entity {
	
	protected double xo, yo;
	protected double angle;
	double rot = 0;
	protected float nx, ny;
	protected double dist;
	public double speed, range, damage;
	public static int fireRate;
	int despawn = 0;
	
	Entity oe;
	
	public Projectile(Level level, Entity oe, Vector3f pos, double dir) {
		super(level, pos);
		this.oe = oe;
		xo = pos.x;
		yo = pos.y;
		angle = dir;
		rot = Math.toRadians(Math.toDegrees(dir) - 45);
		this.bb = new AABB(new Vector2f(pos.x, pos.y), new Vector2f(scale.x - 0.9f, scale.y - 0.9f));
		
		varyProj(45, 200, 17, 1);
		nx = (float) (speed * Math.cos(angle));
		ny = (float) (speed * Math.sin(angle));
		debugTexture = new Texture(1, 0xFF000000);
		mesh.setMaterial(new Material(debugTexture));
		scale = new Vector3f(0.1f);
	}
	
	public Projectile varyProj(double speed, double range, double damage, int fireRate) {
		this.speed = speed;
		this.range = range;
		this.damage = damage;
		Projectile.fireRate = fireRate;
		return this;
	}
	
	@Override
	public void update() {
		movement.add(new Vector3f(nx * (1.0f/60.0f), ny * (1.0f/60.0f), 0));
		if (dist() >= range) remove();
		postMove();
		checkDespawn();
	}
	
	protected double dist() {
		return Math.sqrt(Math.abs(Math.pow(xo - pos.x, 2) + Math.pow(yo - pos.y, 2)));
	}
	
	void checkDespawn() {
		despawn++;
		if (despawn >= (60 * 10)) {
			remove();
		}
	}
	
	@Override
	protected void checkEntityCollision() {
		List<Entity> ents = level.getEnts();
		Entity e = null;
		for (int i = 0; i < ents.size(); i++) {
			e = ents.get(i);
			if (e.getID() == oe.getID()) continue;
			if (e.getID() == this.id) continue;
			Collision c = bb.getCollision(e.bb);
			c.dist.x /= 2;
			c.dist.y /=  2;
			if (c.isIntersecting) {
				this.remove();
			}
		}
	}
	
	@Override
	protected void checkTileCollision() {
		AABB[] boxes = new AABB[25];
		for (int x = 0; x < 5; x++) {
			for (int y = 0; y < 5; y++) {
				boxes[x + y * 5] = level.getMap().getBB(
							(int) (((pos.x / 2) + 0.5f) - (5 / 2)) + x,
							(int) (((-pos.y / 2) + 0.5f) - (5 / 2)) + y);
			}
		}
		
		AABB box = null;
		for (int i = 0; i < boxes.length; i++) {
			if (boxes[i] != null) { 
				if (box == null) box = boxes[i];
				
				Vector2f l1 = box.getCenter().sub(pos.x, pos.y, new Vector2f());
				Vector2f l2 = boxes[i].getCenter().sub(pos.x, pos.y, new Vector2f());
				
				if (l1.lengthSquared() > l2.lengthSquared()) box = boxes[i];
			}
		}
		
		if (box != null) {
			Collision data = bb.getCollision(box);
			if (data.isIntersecting) {
				checkDespawn();
				nx = 0;
				ny = 0;
				remove();
			}
			
			for (int i = 0; i < boxes.length; i++) {
				if (boxes[i] != null) { 
					if (box == null) box = boxes[i];
					
					Vector2f l1 = box.getCenter().sub(pos.x, pos.y, new Vector2f());
					Vector2f l2 = boxes[i].getCenter().sub(pos.x, pos.y, new Vector2f());
					
					if (l1.lengthSquared() > l2.lengthSquared()) box = boxes[i];
				}
			}
			
			data = bb.getCollision(box);
			if (data.isIntersecting) {
				checkDespawn();
				nx = 0;
				ny = 0;
				remove();
			}
		}
	}
}
