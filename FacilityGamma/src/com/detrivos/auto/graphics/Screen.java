package com.detrivos.auto.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import com.detrivos.auto.entity.Mob;
import com.detrivos.auto.entity.assets.Turret;
import com.detrivos.auto.entity.utils.BulletBar;
import com.detrivos.auto.entity.utils.HealthBar;
import com.detrivos.auto.entity.utils.PlayerStatsBar;
import com.detrivos.auto.level.tile.Tile;
import com.detrivos.auto.projectile.Projectile;
import com.detrivos.auto.ui.StoryUI;
import com.detrivos.auto.ui.UI;

public class Screen {

	public int width;
	public int height;
	public int[] pixels;
	public int xOffset, yOffset;

	private Graphics g;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void graphics(Graphics g) {
		this.g = g;
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}

	public void renderHealthBar(int xp, int yp, HealthBar bar) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < bar.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < bar.width; x++) {
				int xa = x + xp;
				if (xa < -18 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				pixels[xa + ya * width] = bar.col;
			}
		}
	}

	public void renderPStatsBar(int xp, int yp, PlayerStatsBar bar) {
		for (int y = 0; y < bar.height; y++) {
			int ya = y + yp;
			for (int x = 0; x < bar.width; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				pixels[xa + ya * width] = bar.col;
			}
		}
	}

	public void renderBulletBar(int xp, int yp, BulletBar bar) {
		for (int y = 0; y < bar.height; y++) {
			int ya = y + yp;
			for (int x = 0; x < bar.width; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				pixels[xa + ya * width] = bar.col;
			}
		}
	}

	public void renderStoryUI(int xp, int yp, StoryUI ui, boolean offset) {
		if (offset) {
			xp -= xOffset;
			yp -= yOffset;
		}

		for (int y = 0; y < ui.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < ui.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = ui.pixels[x + y * ui.WIDTH];
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderUI(int xp, int yp, UI ui) {
		for (int y = 0; y < ui.HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < ui.WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = ui.pixels[x + y * ui.WIDTH];
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderTile(int xp, int yp, Tile tile) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -tile.sprite.SIZE || xa >= width || ya < 0 || ya >= height) break;
				if (xa < 0) xa = 0;
				int col = tile.sprite.pixels[x + y * 16];
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderMob(int xp, int yp, Sprite sprite, Mob m) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * sprite.SIZE];
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
				if (m instanceof Turret && m.locked)
					if (col == 0xFFFF0000)
						pixels[xa + ya * width] = 0xFF00FF00;
			}
		}
	}

	public void renderMob(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * sprite.SIZE];
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderProjectile(int xp, int yp, Projectile p) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int ya = y + yp;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xa = x + xp;
				if (xa < -p.getSpriteSize() || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = p.getSprite().pixels[x + y * p.getSpriteSize()];
				if (col != 0xFFFF00FF)
					pixels[xa + ya * width] = col;
			}
		}
	}

	public void renderText(String text, int x, int y, int size, int style, int color) {
		int r = (color & 0xff0000) >> 16;
		int g = (color & 0xff00) >> 8;
		int b = (color & 0xff);
		Color c = new Color(r, g, b);
		Font f = new Font("Terminal", style, size);
		FontMetrics fm = this.g.getFontMetrics(f);
		this.g.setColor(c);
		this.g.setFont(f);
		this.g.drawString(text, x - (fm.stringWidth(text) / 2), y);
	}

	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}
