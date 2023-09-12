package com.detrivos.auto.entity.assets;

import java.util.List;

import com.detrivos.auto.Game;
import com.detrivos.auto.audio.SoundClip;
import com.detrivos.auto.entity.Entity;
import com.detrivos.auto.entity.Mob;
import com.detrivos.auto.entity.assets.drops.BulletDrops;
import com.detrivos.auto.entity.assets.drops.BulletDrops.BType;
import com.detrivos.auto.entity.assets.drops.Medkit;
import com.detrivos.auto.entity.assets.drops.Medkit.Tier;
import com.detrivos.auto.entity.utils.HealthBar;
import com.detrivos.auto.experience.Experience;
import com.detrivos.auto.graphics.AnimatedSprite;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.Sprite;
import com.detrivos.auto.graphics.SpriteSheet;
import com.detrivos.auto.projectile.Bullet;
import com.detrivos.auto.projectile.Projectile;
import com.detrivos.auto.projectile.Rocket;

public class Turret extends Mob {

	private int rot = 0;
	private int posneg = 1;
	private int rotSpeed;
	private int orotSpeed;
	private int fireRate = 0;
	private int recharge = 0;
	private int firing = 150;
	
	private int hitbox = 3;
	
	public enum Type {
		NORMAL, MACHINE, ROCKET
	}
	
	public Type t;
	
	private double dir;
	double dx = 0, dy = 0;
	double angle = 0;
	
	private int range = 9 * 16;
	
	private boolean dead = false;
	private boolean hit = false;
	private boolean bulletDropped = false;
	private boolean soundCreated = false;
	private boolean l = false;
	
	private SoundClip explode = new SoundClip("sounds/explosion1.wav");
	private SoundClip shoot = new SoundClip("sounds/shoot1.wav");
	
	private AnimatedSprite anim = new AnimatedSprite(SpriteSheet.kaboom, 16, 16, 8);
	
	public Turret(int x, int y, Type t) {
		this.x = x << 4;
		this.y = y << 4;
		this.t = t;
		setProperType();
		rotSpeed = orotSpeed;
		if (level != null) level.add(this);
	}
	
	public void tick() {
		if (level != null && !this.barAdd) {
			bar = new HealthBar(this, (int) this.x, (int) this.y);
			level.add(bar);
			this.barAdd = true;
		}
		
		if (collision(0, 0)) remove();
		
		if (this.health <= 0) dead = true;
		if (dead) {
			if (!soundCreated) {
				SoundClip.play(explode, -15.0f);
				Experience.addExp(t);
				soundCreated = true;
			}
			anim.tick();
			anim.setFrameRate(5);
			if (anim.getFrame() + 1 > 7) remove();
			
		}
		
		if (dead) {
			if (!bulletDropped) {
				if (t == Type.NORMAL) {
					level.add(new BulletDrops(this.x, this.y, BType.PISTOL, 13));
					if (Game.onchal && random.nextInt(7) == 0) level.add(new Medkit((int) this.x / 16, (int) this.y / 16, Tier.LOW)); 
				}
				if (t == Type.MACHINE) {
					level.add(new BulletDrops(this.x, this.y, BType.MACHINE, 25));
					if (Game.onchal && random.nextInt(9) == 0) level.add(new Medkit((int) this.x / 16, (int) this.y / 16, Tier.MID)); 
				}
				if (t == Type.ROCKET) {
					level.add(new BulletDrops(this.x, this.y, BType.ROCKET, 2));
					if (Game.onchal && random.nextInt(2) == 0) level.add(new Medkit((int) this.x / 16, (int) this.y / 16, Tier.HIGH)); 
				}
				if (Game.onchal) Player.kills++;
				bulletDropped = true;
			}
		}
		
		targetLeechers();
		targetPlayer();
		handleProjectiles();
	}
	
	private void targetPlayer() {
		List<Player> players = level.getPlayers(this, range);
		if (players.size() > 0 && !l) {
			Player player = players.get(0);
			if (t != Type.ROCKET) {
				dx = player.getX() - (this.x + rand());
				dy = player.getY() - (this.y + rand());
			} else {
				dx = player.getX() - this.x;
				dy = player.getY() - this.y;
			}
			if ((int) Math.toDegrees(Math.atan2(dy, dx)) != rot && !locked) {
				if (((int) Math.toDegrees(Math.atan2(dy, dx)) >= 0 && rot >= 0) || ((int) Math.toDegrees(Math.atan2(dy, dx)) <= 0 && rot <= 0)) {
					if ((int) Math.toDegrees(Math.atan2(dy, dx)) > rot &&  (int) Math.toDegrees(Math.atan2(dy, dx)) - rot < rotSpeed) rotSpeed = (int) Math.toDegrees(Math.atan2(dy, dx)) - rot;
					else rotSpeed = orotSpeed;
					if ((int) Math.toDegrees(Math.atan2(dy, dx)) > rot) rot += rotSpeed;
					
					if ((int) Math.toDegrees(Math.atan2(dy, dx)) < rot && rot - (int) Math.toDegrees(Math.atan2(dy, dx)) < rotSpeed) rotSpeed = rot - (int) Math.toDegrees(Math.atan2(dy, dx));
					else rotSpeed = orotSpeed;
					if ((int) Math.toDegrees(Math.atan2(dy, dx)) < rot) rot -= rotSpeed;
					rotSpeed = orotSpeed;
					
				} else if (((int) Math.toDegrees(Math.atan2(dy, dx)) > 0 && rot < 0)) {
					if (Math.abs((int) Math.toDegrees(Math.atan2(dy, dx))) + Math.abs(rot) < 180) rot += rotSpeed;
					else rot -= rotSpeed;
				} else if (((int) Math.toDegrees(Math.atan2(dy, dx)) < 0 && rot > 0)) {
					if (Math.abs((int) Math.toDegrees(Math.atan2(dy, dx))) + Math.abs(rot) < 180) rot -= rotSpeed;
					else rot += rotSpeed;
				}
				rotSpeed = orotSpeed;
				
				if (rot < -180) rot = 180;
				if (rot > 180) rot = -180;
				angle = dir = Math.toRadians(rot);
			} else {
				angle = dir = Math.atan2(dy, dx);
				rot = (int) Math.toDegrees(Math.atan2(dy, dx));
				locked = true;
				if (!dead && firing > 0 && recharge == 0) {
					if (t == Type.MACHINE && firing > 0) {
						firing--;
					}
					if (firing == 0 && recharge == 0) {
						recharge = 30;
					}
					shooting();
				}
				if (recharge > 0 && firing == 0) recharge--;
				if (recharge == 0 && firing == 0) firing = 150;
			}
			if (fireRate > 0) fireRate--; 
		} else {
			locked = false;
			if (rot > 180) {
				rot = -180;
				posneg = -1;
			} else if (rot < -180) {
				rot = -180;
				posneg = 1;
			}
			rot += posneg;
			angle = Math.toRadians(rot);
		}
	}
	
	private void targetLeechers() {
		List<Entity> ents = level.getEntities(this, range);
		if (ents.size() > 0) {
			for (int i = 0; i < ents.size(); i++) {
				Entity e = ents.get(0);
				if (e instanceof Leecher) {
					l = true;
					if (t != Type.ROCKET) {
						dx = e.getX() - (this.x + rand());
						dy = e.getY() - (this.y + rand());
					} else {
						dx = e.getX() - this.x;
						dy = e.getY() - this.y;
					}
					
					if ((int) Math.toDegrees(Math.atan2(dy, dx)) != rot && !locked) {
						if (((int) Math.toDegrees(Math.atan2(dy, dx)) >= 0 && rot >= 0) || ((int) Math.toDegrees(Math.atan2(dy, dx)) <= 0 && rot <= 0)) {
							if ((int) Math.toDegrees(Math.atan2(dy, dx)) > rot &&  (int) Math.toDegrees(Math.atan2(dy, dx)) - rot < rotSpeed) rotSpeed = (int) Math.toDegrees(Math.atan2(dy, dx)) - rot;
							else rotSpeed = orotSpeed;
							if ((int) Math.toDegrees(Math.atan2(dy, dx)) > rot) rot += rotSpeed;
							
							if ((int) Math.toDegrees(Math.atan2(dy, dx)) < rot && rot - (int) Math.toDegrees(Math.atan2(dy, dx)) < rotSpeed) rotSpeed = rot - (int) Math.toDegrees(Math.atan2(dy, dx));
							else rotSpeed = orotSpeed;
							if ((int) Math.toDegrees(Math.atan2(dy, dx)) < rot) rot -= rotSpeed;
							rotSpeed = orotSpeed;
							
						} else if (((int) Math.toDegrees(Math.atan2(dy, dx)) > 0 && rot < 0)) {
							if (Math.abs((int) Math.toDegrees(Math.atan2(dy, dx))) + Math.abs(rot) < 180) rot += rotSpeed;
							else rot -= rotSpeed;
						} else if (((int) Math.toDegrees(Math.atan2(dy, dx)) < 0 && rot > 0)) {
							if (Math.abs((int) Math.toDegrees(Math.atan2(dy, dx))) + Math.abs(rot) < 180) rot -= rotSpeed;
							else rot += rotSpeed;
						}
						rotSpeed = orotSpeed;
						
						if (rot < -180) rot = 180;
						if (rot > 180) rot = -180;
						angle = dir = Math.toRadians(rot);
					} else {
						angle = dir = Math.atan2(dy, dx);
						rot = (int) Math.toDegrees(Math.atan2(dy, dx));
						locked = true;
						if (!dead && firing > 0 && recharge == 0) {
							if (t == Type.MACHINE && firing > 0) {
								firing--;
							}
							if (firing == 0 && recharge == 0) {
								recharge = 30;
							}
							shooting();
						}
						if (recharge > 0 && firing == 0) recharge--;
						if (recharge == 0 && firing == 0) firing = 150;
					}
					if (fireRate > 0) fireRate--; 
				}
			}
		} else {
			l = false;
			locked = false;
		}
	}
	
	private void handleProjectiles() {
		List<Projectile> projectiles = level.getProjectiles(this, hitbox);
		if (projectiles.size() > 0) {
			Projectile p = projectiles.get(0);
			if (p.type != 1) {
				if (p instanceof Rocket) {
					if (!hit) {
						this.health -= p.damage; 
						hit = true;
					}
					((Rocket) p).exploded = true;
				} else {
					this.health -= p.damage;
					p.remove();
				}
			}
		} else {
			hit = false;
		}
	}
	
	private void setProperType() {
		switch (t) {
		case NORMAL:
			health = 80;
			sprite = Sprite.turret;
			hitbox = 3;
			orotSpeed = 3;
			break;
		case MACHINE:
			health = 50;
			sprite = Sprite.machineTurret;
			range = 16 * 10;
			hitbox = 4;
			orotSpeed = 5;
			break;
		case ROCKET:
			health = 150;
			sprite = Sprite.rocketTurret;
			range = 16 * 13;
			hitbox = 5;
			orotSpeed = 3;
			fireRate = Rocket.fireRate;
			break;
		default:
			t = Type.NORMAL;
			health = 80;
			sprite = Sprite.turret;
			hitbox = 3;
			orotSpeed = 3;
			break;
		}
	}
	
	private int rand() {
		int sign, numb;
		if (random.nextInt(2) == 0) sign = -1;
		else sign = 1;
		numb = random.nextInt(7) * sign;
		return numb;
	}
	
	private void shooting() {
		if (fireRate <= 0) {
			shoot(x + 8, y + 8, dir);
			if (t != Type.ROCKET) fireRate = Bullet.fireRate;
			else fireRate = Rocket.fireRate;
		}
	}
	
	protected void shoot(double x, double y, double dir) {
		Projectile p = null;
		if (t == Type.ROCKET) p = new Rocket(x, y, dir, this);
		else p = new Bullet(x, y, dir, this);
		boolean shot = false;
		if (!shot && p instanceof Bullet) {
			SoundClip.play(shoot, -14.0f); shot = true;
		}
		level.add(p);
	}

	public void render(Screen screen) {
		if (dead) sprite = anim.getSprite();
		screen.renderMob((int) x, (int) y, Sprite.rotate(sprite, angle), this);
	}
}
