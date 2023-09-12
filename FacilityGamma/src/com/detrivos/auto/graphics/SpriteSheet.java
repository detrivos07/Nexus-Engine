package com.detrivos.auto.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public int[] pixels;
	
	private Sprite[] sprites;
	
	public static SpriteSheet ground = new SpriteSheet("/textures/sheets/ground.png", 64);
	public static SpriteSheet gFade = new SpriteSheet("/textures/sheets/gFade.png", 64);
	public static SpriteSheet base = new SpriteSheet("/textures/sheets/base.png", 96);//TODO :: Edit size when expand sheet
	public static SpriteSheet tunnel = new SpriteSheet("/textures/sheets/tunnel.png", 64);
	
	public static SpriteSheet player = new SpriteSheet("/textures/entities/anims/player.png", 160, 64);
	public static SpriteSheet pnaked = new SpriteSheet(player, 0, 0, 1, 4, 16);
	public static SpriteSheet pclothed = new SpriteSheet(player, 1, 0, 1, 4, 16);
	public static SpriteSheet pcgun = new SpriteSheet(player, 2, 0, 1, 4, 16);
	public static SpriteSheet pgun = new SpriteSheet(player, 3, 0, 1, 4, 16);
	public static SpriteSheet cs = new SpriteSheet(player, 4, 0, 1, 4, 16);
	public static SpriteSheet cm = new SpriteSheet(player, 5, 0, 1, 4, 16);
	public static SpriteSheet cb = new SpriteSheet(player, 6, 0, 1, 4, 16);
	public static SpriteSheet ns = new SpriteSheet(player, 7, 0, 1, 4, 16);
	public static SpriteSheet nm = new SpriteSheet(player, 8, 0, 1, 4, 16);
	public static SpriteSheet nb = new SpriteSheet(player, 9, 0, 1, 4, 16);
	
	public static SpriteSheet doorAnim = new SpriteSheet("/textures/entities/anims/doorAnim.png", 128, 192);
	public static SpriteSheet openRight = new SpriteSheet(doorAnim, 0, 0, 1, 6, 32);
	public static SpriteSheet openLeft = new SpriteSheet(doorAnim, 1, 0, 1, 6, 32);
	public static SpriteSheet closeRight = new SpriteSheet(doorAnim, 2, 0, 1, 6, 32);
	public static SpriteSheet closeLeft = new SpriteSheet(doorAnim, 3, 0, 1, 6, 32);
	
	public static SpriteSheet destroyedCryo = new SpriteSheet( "/textures/entities/cryoPods.png", 52);
	
	public static SpriteSheet cryoEmerge = new SpriteSheet("/textures/entities/anims/cryoEmersion.png", 26, 364);
	public static SpriteSheet emerge = new SpriteSheet(cryoEmerge, 0, 0, 1, 14, 26);
	
	public static SpriteSheet explode = new SpriteSheet("/textures/sheets/explosion.png", 16, 128);
	public static SpriteSheet kaboom = new SpriteSheet(explode, 0, 0, 1, 8, 16);
	
	public static SpriteSheet landmineStanding = new SpriteSheet("/textures/sheets/landmineStanding.png", 8, 16);
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if(width == height) SIZE = width;
		else SIZE = -1;
		WIDTH = w;
		HEIGHT = h;
		pixels = new int[w * h];
		for(int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for(int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}
		
		int frame = 0;
		sprites = new Sprite[width * height];
		for(int ya = 0; ya < height; ya++) {
			for(int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for(int y0 = 0; y0 < spriteSize; y0++) {
					for(int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
		
	public SpriteSheet(String path, int size) {
		this.path = path;
		SIZE = size;
		WIDTH = size;
		HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[width * height];
		load();
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("NO LOAD SPRITESHEET");
		}
	}
}
