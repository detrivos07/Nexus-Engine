package com.detrivos.auto.entity.assets.unused;

import java.util.List;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.assets.Leecher;
import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.entity.utils.HealthBar;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.projectile.Projectile;

public class Blurr extends Entity {
	
	private int rot = 0;
	private int rotSpeed = 4;
	
	double xa = 0, ya = 0;
	double dx = 0, dy = 0;
	double lx = 0, ly = 0, tx = 0, ty = 0;
	double angle = 0;
	
	private double tang = 0;
	private double oSpeed = 1.4;
	private double fireSpeed = 3;
	
	public boolean hitPlayer = false;

	public Blurr(int x, int y, int health) {
		this.x = x << 4;
		this.y = y << 4;
		this.health = health;
		speed = oSpeed;
		sprite = null; // TODO :: Create texture
		if (level != null) {
			level.add(this);
		}
	}
	
	private void gotoPlayer() {
		if (x < lx && lx - x < fireSpeed) speed = lx - x;
		else speed = fireSpeed;
		if (x < lx) xa += speed;
		
		if (x > lx && x - lx < fireSpeed) speed = x - lx;
		else speed = fireSpeed;
		if (x > lx) xa -= speed;
		
		if (y < ly && ly - y < fireSpeed) speed = ly - y;
		else speed = fireSpeed;
		if (y < ly) ya += speed;
		
		if (y > ly && y - ly < fireSpeed) speed = y - ly;
		else speed = fireSpeed;
		if (y > ly) ya -= speed;
		
		if (x == lx && y == ly) {
			lx = 0;
			ly = 0;
		}
	}// TODO :: STRAIGHT LINE FOLLOWING, NOT DIVERTING, edit speed in either direction
	
	private void gotoPoint() {
		if (x < tx && tx - x < fireSpeed) speed = tx - x;
		else speed = fireSpeed;
		if (x < tx) xa += speed;
		
		if (x > tx && x - tx < fireSpeed) speed = x - tx;
		else speed = fireSpeed;
		if (x > tx) xa -= speed;
		
		if (y < ty && ty - y < fireSpeed) speed = ty - y;
		else speed = fireSpeed;
		if (y < ty) ya += speed;
		
		if (y > ty && y - ty < fireSpeed) speed = y - ty;
		else speed = fireSpeed;
		if (y > ty) ya -= speed;
		
		if (x == tx && y == ty) {
			locked = false;
			hitPlayer = false;
			tx = 0;
			ty = 0;
		}
	}
	
	public void tick() {
		if (level != null && !this.barAdd) {
			bar = new HealthBar(this, (int) this.x, (int) this.y);
			level.add(bar);
			this.barAdd = true;
		}
		if (this.health <= 0) {
			bar.remove();
			remove();
		}
		xa = 0;
		ya = 0;//TODO :: DAMAGE
		List<Player> players = level.getPlayers(this, 12 * 16);
		if (players.size() > 0) {
			Player player = players.get(0);
			dx = player.getX() - this.x;
			dy = player.getY() - this.y;
			if ((int) Math.toDegrees(Math.atan2(dy, dx)) == rot && !locked && !hitPlayer) {
				locked = true;
				lx = player.getX();
				ly = player.getY();
				if (Math.abs(dx / 2) > 3 * 16) {
					tx = lx + (dx );
				} else {
			//		if (dx > 0) tx = lx + 3 * 16;
			//		if (dx < 0) tx = lx - 3 * 16; // TODO :: FIX
				}
				if (Math.abs(dy / 2) > 3 * 16) {
					ty = ly + (dy );
				} else {
					//if (dy > 0) ty = ly + 3 * 16;
					//if (dy < 0) ty = ly - 3 * 16; // TODO :: FIX
				}
			}
			if ((int) Math.toDegrees(Math.atan2(dy, dx)) != rot && !locked && !hitPlayer) {
				if ((int) Math.toDegrees(Math.atan2(dy, dx)) - rot < rotSpeed) rotSpeed = (int) Math.toDegrees(Math.atan2(dy, dx)) - rot;
				else rotSpeed = 4;
				if ((int) Math.toDegrees(Math.atan2(dy, dx)) > rot) {
					rot += rotSpeed;
					lx = 0;
					ly = 0;
				}
				
				if (rot - (int) Math.toDegrees(Math.atan2(dy, dx)) < rotSpeed) rotSpeed = rot - (int) Math.toDegrees(Math.atan2(dy, dx));
				else rotSpeed = 4;
				if ((int) Math.toDegrees(Math.atan2(dy, dx)) < rot) {
					rot -= rotSpeed;
					lx = 0;
					ly = 0;
				}
				// TODO :: Complete rotation during tracking
				angle = Math.toRadians(rot);
			} else {
				if (locked && !hitPlayer) gotoPlayer();
				if (hitPlayer && locked) gotoPoint();
			}
		} else {
			locked = false;
			hitPlayer = false;
		}
		
		List<Projectile> projectiles = level.getProjectiles(this, 6);
		if (projectiles.size() > 0) {
			Projectile p = projectiles.get(0);
			p.remove();
			this.health -= p.damage;
		}
		
		List<Entity> ents = level.getEntities(this, 6);
		if (ents.size() > 0) {
			for (int i = 0; i < ents.size(); i++) {
				Entity e = ents.get(i);
				if (e instanceof Leecher || e instanceof Blurr) {
					if (x < e.getX()) xa -= speed;
					if (x > e.getX()) xa += speed;
					if (y < e.getY()) ya -= speed;
					if (y > e.getY()) ya += speed;
				}
			}
		}
		
		if (ya < 0) {
			dir = Direction.UP;
		} else if (ya > 0) {
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			dir = Direction.LEFT;
		} else if (xa > 0) {
			dir = Direction.RIGHT;
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
		}
	}

	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, Sprite.rotate(sprite, angle), this);
	}
}
