package com.detrivos.auto.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;
	
	private String path;
	
	public static Sprite turret = new Sprite("/textures/entities/turrets/turret.png", 16);
	public static Sprite machineTurret = new Sprite("/textures/entities/turrets/machineTurret.png", 16);
	public static Sprite rocketTurret = new Sprite("/textures/entities/turrets/rocketTurret.png", 16);
	
	public static Sprite leecher = new Sprite("/textures/entities/leecher.png", 16);
	
	public static Sprite note = new Sprite("/textures/entities/story/note.png", 10);
	public static Sprite cryonote = new Sprite("/textures/entities/story/cryonote.png", 10);
	
	public static Sprite medkit = new Sprite("/textures/entities/drops/medkit.png", 10);
	public static Sprite medkitMid = new Sprite("/textures/entities/drops/medkitMid.png", 10);
	public static Sprite medkitHigh = new Sprite("/textures/entities/drops/medkitHigh.png", 10);
	public static Sprite bullets = new Sprite("/textures/entities/drops/bullets.png", 10);
	
	public static Sprite door = new Sprite("/textures/entities/door.png", 32);
	public static Sprite shotgunNPC = new Sprite("/textures/entities/story/shotgunNPC.png", 25);
	
	public static Sprite cryoPod1 = new Sprite(26, 0, 0, SpriteSheet.destroyedCryo);
	public static Sprite cryoPod2 = new Sprite(26, 0, 1, SpriteSheet.destroyedCryo);
	public static Sprite cryoPod3 = new Sprite(26, 1, 0, SpriteSheet.destroyedCryo);
	public static Sprite cryoPod4 = new Sprite(26, 1, 1, SpriteSheet.destroyedCryo);
	
	public static Sprite landmineDirt = new Sprite(8, 0, 0, SpriteSheet.landmineStanding);
	public static Sprite landmineGrass = new Sprite(8, 0, 1, SpriteSheet.landmineStanding);
	
	public static Sprite outhouse = new Sprite("/textures/locations/outhouse.png", 16);
	
	public static Sprite dirt = new Sprite(16, 0, 0, SpriteSheet.ground);
	public static Sprite grass = new Sprite(16, 0, 1, SpriteSheet.ground);
	
	public static Sprite dirtSR = new Sprite(16, 1, 1, SpriteSheet.ground);
	public static Sprite dirtMR = new Sprite(16, 1, 0, SpriteSheet.ground);
	public static Sprite dirtLR = new Sprite(16, 2, 0, SpriteSheet.ground);
	public static Sprite sGrass = new Sprite(16, 2, 1, SpriteSheet.ground);
	
	public static Sprite grassFadeL = new Sprite(16, 0, 0, SpriteSheet.gFade);
	public static Sprite grassFadeT = new Sprite(16, 0, 1, SpriteSheet.gFade);
	public static Sprite grassFadeR = new Sprite(16, 0, 2, SpriteSheet.gFade);
	public static Sprite grassFadeB = new Sprite(16, 0, 3, SpriteSheet.gFade);
	
	public static Sprite grassFadeILT = new Sprite(16, 1, 0, SpriteSheet.gFade);
	public static Sprite grassFadeIRT = new Sprite(16, 1, 1, SpriteSheet.gFade);
	public static Sprite grassFadeIRB = new Sprite(16, 1, 2, SpriteSheet.gFade);
	public static Sprite grassFadeILB = new Sprite(16, 1, 3, SpriteSheet.gFade);
	
	public static Sprite grassFadeOLT = new Sprite(16, 2, 0, SpriteSheet.gFade);
	public static Sprite grassFadeORT = new Sprite(16, 2, 1, SpriteSheet.gFade);
	public static Sprite grassFadeORB = new Sprite(16, 2, 2, SpriteSheet.gFade);
	public static Sprite grassFadeOLB = new Sprite(16, 2, 3, SpriteSheet.gFade);
	
	//tunnel SS
	public static Sprite dirtTLC = new Sprite(16, 0, 0, SpriteSheet.tunnel);
	public static Sprite dirtTRC = new Sprite(16, 1, 0, SpriteSheet.tunnel);
	public static Sprite dirtBRC = new Sprite(16, 1, 1, SpriteSheet.tunnel);
	public static Sprite dirtBLC = new Sprite(16, 0, 1, SpriteSheet.tunnel);
	
	public static Sprite dirtLeft = new Sprite(16, 2, 0, SpriteSheet.tunnel);
	public static Sprite dirtRight = new Sprite(16, 3, 0, SpriteSheet.tunnel);
	public static Sprite dirtDown = new Sprite(16, 3, 1, SpriteSheet.tunnel);
	public static Sprite dirtTop = new Sprite(16, 2, 1, SpriteSheet.tunnel);
	
	public static Sprite dirtWall = new Sprite(16, 0, 2, SpriteSheet.tunnel);
	
	public static Sprite brt = new Sprite(16, 1, 2, SpriteSheet.tunnel);
	public static Sprite brl = new Sprite(16, 0, 3, SpriteSheet.tunnel);
	
	public static Sprite mrtb = new Sprite(16, 2, 2, SpriteSheet.tunnel);
	public static Sprite mrr = new Sprite(16, 3, 2, SpriteSheet.tunnel);
	
	public static Sprite srtl = new Sprite(16, 1, 3, SpriteSheet.tunnel);
	public static Sprite srt = new Sprite(16, 2, 3, SpriteSheet.tunnel);
	
	//base spritesheet
	public static Sprite fw1 = new Sprite(16, 3, 5, SpriteSheet.base);
	public static Sprite fw2 = new Sprite(16, 5, 1, SpriteSheet.base);
	public static Sprite fw3 = new Sprite(16, 5, 2, SpriteSheet.base);
	public static Sprite fw4 = new Sprite(16, 0, 5, SpriteSheet.base);
	
	public static Sprite fb1 = new Sprite(16, 4, 5, SpriteSheet.base);
	public static Sprite fb2 = new Sprite(16, 1, 5, SpriteSheet.base);
	
	public static Sprite wdt = new Sprite(16, 5, 5, SpriteSheet.base);
	public static Sprite wdb = new Sprite(16, 2, 5, SpriteSheet.base);
	
	public static Sprite tloc = new Sprite(16, 0, 0, SpriteSheet.base);
	public static Sprite troc = new Sprite(16, 1, 0, SpriteSheet.base);
	public static Sprite bloc = new Sprite(16, 0, 1, SpriteSheet.base);
	public static Sprite broc = new Sprite(16, 1, 1, SpriteSheet.base);
	
	public static Sprite bric = new Sprite(16, 2, 0, SpriteSheet.base);
	public static Sprite blic = new Sprite(16, 3, 0, SpriteSheet.base);
	public static Sprite tric = new Sprite(16, 2, 1, SpriteSheet.base);
	public static Sprite tlic = new Sprite(16, 3, 1, SpriteSheet.base);
	
	public static Sprite crate1 = new Sprite(16, 5, 0, SpriteSheet.base);
	
	public static Sprite bench = new Sprite(16, 3, 2, SpriteSheet.base);
	
	public static Sprite rw = new Sprite(16, 4, 0, SpriteSheet.base);
	public static Sprite tw = new Sprite(16, 4, 1, SpriteSheet.base);
	public static Sprite lw = new Sprite(16, 4, 2, SpriteSheet.base);
	public static Sprite bw = new Sprite(16, 4, 3, SpriteSheet.base);
	
	public static Sprite tlw = new Sprite(16, 3, 3, SpriteSheet.base);
	public static Sprite blw = new Sprite(16, 3, 4, SpriteSheet.base);
	public static Sprite tclw = new Sprite(16, 2, 4, SpriteSheet.base);
	public static Sprite bglw = new Sprite(16, 4, 4, SpriteSheet.base);
	
	public static Sprite ble = new Sprite(16, 0, 2, SpriteSheet.base);
	public static Sprite bre = new Sprite(16, 1, 2, SpriteSheet.base);
	public static Sprite tle = new Sprite(16, 0, 3, SpriteSheet.base);
	public static Sprite tre = new Sprite(16, 1, 3, SpriteSheet.base);
	
	public static Sprite ltd = new Sprite(16, 2, 2, SpriteSheet.base);
	public static Sprite lbd = new Sprite(16, 2, 3, SpriteSheet.base);
	
	public static Sprite rtd = new Sprite(16, 3, 2, SpriteSheet.base);
	public static Sprite rbd = new Sprite(16, 3, 3, SpriteSheet.base);
	
	public static Sprite whiteFloor = new Sprite(16, 0, 4, SpriteSheet.base);
	public static Sprite blueFloor = new Sprite(16, 1, 4, SpriteSheet.base);
	
	public static Sprite voidSprite = new Sprite(16, 0xFF000000);
	public static Sprite bullet = new Sprite(2, 0xFF000000);
	public static Sprite rocket = new Sprite("/textures/entities/rocket.png", 5);
	public static Sprite pink = new Sprite(16, 0xFFFF00FF);
	
	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}
	
	public Sprite(String path, int size) {
		this.path = path;
		SIZE = size;
		width = size;
		height = size;
		pixels = new int[SIZE * SIZE];
		loadFromFile();
	}
	
	public Sprite(String path, int width, int height) {
		this.path = path;
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[this.width * this.height];
		loadFromFile();
	}
	
	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int colour) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int [width * height];
		setColour(colour);
	}
	
	public Sprite(int size, int colour) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		setColour(colour);
	}
	
	public Sprite(int pixels[], int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}
	
	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	private static int[] rotate(int[] pixels, int width, int height, double angle){
		int[] result = new int [width * height];
		
		double nxX = rotX(-angle, 1.0, 0.0);
		double nxY = rotY(-angle, 1.0, 0.0);
		double nyX = rotX(-angle, 0.0, 1.0);
		double nyY = rotY(-angle, 0.0, 1.0);
		
		double x0 = rotX(-angle, - width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rotY(-angle, - width / 2.0, -height / 2.0) + height / 2.0;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = 0xFFFF00FF;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				x1 += nxX;
				y1 += nxY;
			}
			x0 += nyX;
			y0 += nyY;
		}
		
		return result;
	}
	
	private static double rotX(double angle, double x, double y) {
		double cos = Math.cos(angle - ((2 * Math.PI) / 4));
		double sin = Math.sin(angle - ((2 * Math.PI) / 4));
		return x * cos + y * -sin;
	}
	
	private static double rotY(double angle, double x, double y) {
		double cos = Math.cos(angle - ((2 * Math.PI) / 4));
		double sin = Math.sin(angle - ((2 * Math.PI) / 4));
		return x * sin + y * cos;
	}
	
	private void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void load() {
		for(int y = 0; y < SIZE; y++) {
			for(int x = 0; x < SIZE; x++) {
				pixels[x + y * SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.WIDTH];
			}
		}
	}
	
	private void loadFromFile() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("NO LOAD SPRITE");
		}
	}
}
