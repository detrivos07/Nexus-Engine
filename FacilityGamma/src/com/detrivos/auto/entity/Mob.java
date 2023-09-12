package com.detrivos.auto.entity;

import com.detrivos.auto.Game;
import com.detrivos.auto.entity.utils.HealthBar;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.projectile.Bullet;
import com.detrivos.auto.projectile.Projectile;

public abstract class Mob extends Entity {
		
		protected boolean moving = false;
		public double health;
		public double stamina;
		protected double speed = 1;
		protected HealthBar bar;
		protected boolean barAdd = false;
		
		public boolean locked = false;
		
		protected enum Direction {
			UP, DOWN, LEFT, RIGHT
		}
		
		protected Direction dir;

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
				if (collision(-1, 0) && collision(1, 0) && !collision(0, -1) && (this.x + xa) > 0 && (this.x + xa) < Game.width - 15) this.y -= 0.5;
				if (collision(0, 0) & !collision(1, 0)) this.x += 0.5;
				if (collision(0, 0) & !collision(-1, 0)) this.x -= 0.5;
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
		
		public double getHealth() {
			return health;
		}
		
		protected int abs(double ya) {
			if (ya < 0) return -1;
			return 1;
		}

		public abstract void tick();
		
		public abstract void render(Screen screen);
		
		protected void shoot(double x, double y, double dir) {
			Projectile p = new Bullet(x, y, dir, null); // Change Projectile here
			level.add(p);
		}
		
		public boolean solid() {
			return true;
		}

		protected boolean collision(double xa, double ya) {
			boolean solid = false;
			for (int c = 0; c < 4; c++) {
				//Player Specific
				double xt = ((x + xa) + c % 2 * -9 + 4) / 16;
				double yt = ((y + ya) + c / 2 * -1 + 1) / 16;
				int ix = (int) Math.ceil(xt);
				int iy = (int) Math.ceil(yt);
				if (c % 2 == 0) ix = (int) Math.floor(xt); 
				if (c / 2 == 0) iy = (int) Math.floor(yt);
				if (level.getTile(ix, iy).solid()) solid = true;
			}
			return solid;
		}
}
