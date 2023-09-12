package com.detrivos.auto.entity.assets;

import java.util.List;

import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.Mob;
import com.detrivos.auto.entity.utils.HealthBar;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.projectile.Projectile;

public class Leecher extends Mob {
	
	private int rot = 0;
	private int rotSpeed = 2;
	
	double dx = 0, dy = 0;
	double angle = 0;
	
	boolean posx = false;
	boolean negx = false;
	boolean posy = false;
	boolean negy = false;
	
	private double tang = 0;
	private double oSpeed = 1.4;

	public Leecher(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		this.health = 100;
		speed = oSpeed;
		sprite = Sprite.leecher; 
		if (level != null) {
			level.add(this);
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
		double xa = 0, ya = 0;
		
		List<Player> players = level.getPlayers(this, 10 * 16);
		if (players.size() > 0) {
			Player player = players.get(0);
			if (!player.box) {
				dx = player.getX() - this.x;
				dy = player.getY() - this.y;
				if ((int) Math.toDegrees(Math.atan2(dy, dx)) != rot && !locked) {
					if (((int) Math.toDegrees(Math.atan2(dy, dx)) >= 0 && rot >= 0) || ((int) Math.toDegrees(Math.atan2(dy, dx)) <= 0 && rot <= 0)) {
						if ((int) Math.toDegrees(Math.atan2(dy, dx)) > rot &&  (int) Math.toDegrees(Math.atan2(dy, dx)) - rot < rotSpeed) rotSpeed = (int) Math.toDegrees(Math.atan2(dy, dx)) - rot;
						else rotSpeed = 3;
						if ((int) Math.toDegrees(Math.atan2(dy, dx)) > rot) rot += rotSpeed;
						
						if ((int) Math.toDegrees(Math.atan2(dy, dx)) < rot && rot - (int) Math.toDegrees(Math.atan2(dy, dx)) < rotSpeed) rotSpeed = rot - (int) Math.toDegrees(Math.atan2(dy, dx));
						else rotSpeed = 3;
						if ((int) Math.toDegrees(Math.atan2(dy, dx)) < rot) rot -= rotSpeed;
						rotSpeed = 3;
						
					} else if (((int) Math.toDegrees(Math.atan2(dy, dx)) > 0 && rot < 0)) {
						if (Math.abs((int) Math.toDegrees(Math.atan2(dy, dx))) + Math.abs(rot) < 180) rot += rotSpeed;
						else rot -= rotSpeed;
					} else if (((int) Math.toDegrees(Math.atan2(dy, dx)) < 0 && rot > 0)) {
						if (Math.abs((int) Math.toDegrees(Math.atan2(dy, dx))) + Math.abs(rot) < 180) rot -= rotSpeed;
						else rot += rotSpeed;
					}
				} else {
					angle = Math.atan2(dy, dx);
					rot = (int) Math.toDegrees(Math.atan2(dy, dx));
					if (x < player.getX() && player.getX() - x < oSpeed) speed = player.getX() - x;
					else speed = oSpeed;
					if (x < player.getX()) xa += speed;
					
					if (x > player.getX() && x - player.getX() < oSpeed) speed = x - player.getX();
					else speed = oSpeed;
					if (x > player.getX()) xa -= speed;
					
					if (y < player.getY() && player.getY() - y < oSpeed) speed = player.getY() - y;
					else speed = oSpeed;
					if (y < player.getY()) ya += speed;
					
					if (y > player.getY() && y - player.getY() < oSpeed) speed = y - player.getY();
					else speed = oSpeed;
					if (y > player.getY()) ya -= speed;
					locked = true;
					
					if (collision(0, ya) && !posx && !negx) {
						if (player.getX() < this.x) {
							posx = true;
							negx = false;
						} else {
							negx = true;
							posx = false;
						}
					}
					
					if (collision(xa, 0) && !posy && !negy) {
						if (player.getY() < this.y) {
							posy = true;
							negy = false;
						} else {
							negy = true;
							posy = false;
						}
					}
				}
			} else {
				xa = 0;
				ya = 0;
			}
		} else {
			locked = false;
		}
		
		if (posx) {
			xa += speed;
			if (!collision(0, ya)) {
				posx = false;
			}
		}
		if (negx) {
			xa -= speed;
			if (!collision(0, ya)) {
				negx = false;
			}
		}
		if (posy) {
			ya += speed;
			if (!collision(xa, 0)) {
				posy = false;
			}
		}
		if (negy) {
			ya -= speed;
			if (!collision(xa, 0)) {
				negy = false;
			}
		}
		
		List<Projectile> projectiles = level.getProjectiles(this, 6);
		if (projectiles.size() > 0) {
			for (int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);
				p.remove();
				this.health -= p.damage;
			}
		}
		
		List<Entity> ents = level.getEntities(this, 6);
		if (ents.size() > 0) {
			for (int i = 0; i < ents.size(); i++) {
				Entity e = ents.get(i);
				if (x < e.getX()) xa -= speed;
				if (x > e.getX()) xa += speed;
				if (y < e.getY()) ya -= speed;
				if (y > e.getY()) ya += speed;
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
	
	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (xa > 0) dir = Direction.RIGHT;
		if (xa < 0) dir = Direction.LEFT;
		if (ya > 0) dir = Direction.DOWN;
		if (ya < 0) dir = Direction.UP;
		
		while (xa != 0) {
			if (collision(0, 0) & !collision(1, 0)) this.x += 0.5;
			if (collision(0, 0) & !collision(-1, 0)) this.x -= 0.5;
			if (collision(0, 0) & !collision(0, 1)) this.y += 0.5;
			if (collision(0, 0) & !collision(0, -1)) this.y -= 0.5;
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.x += xa;
				}
				xa = 0;
			}
		}
		
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.y += ya;
				}
				ya = 0;
			}
		}
	}
	
	/*protected boolean collision(double xa, double ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) + c % 2 * -6 + 3) / 16;
			double yt = ((y + ya) + c / 2 * -6 + 3) / 16;
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt); 
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			if (level.getTile(ix, iy).solid()) solid = true;
		}
		return solid;
	}*/

	public void render(Screen screen) {
		screen.renderMob((int) x, (int) y, Sprite.rotate(sprite, angle), this);
	}
}
