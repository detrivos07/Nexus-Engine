package artifice.engine.level.tile;

import static artifice.engine.level.tile.TileTypes.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.joml.Vector2f;

import artifice.engine.render.model.InstMaterial;
import artifice.engine.render.texture.Texture;
import artifice.engine.utils.AABB;

public class TileMap {
	
	private InstMaterial[] types;
	private Map<TileTypes, InstMaterial> tps;
	private AABB[][] bbmap;
	private Tile[][] map;
	private int width, height;
	
	public TileMap(InstMaterial[] types) {
		this.types = types;
		this.tps = new HashMap<TileTypes, InstMaterial>();
		List<Texture> temp = types[0].getTextureAtlas().getTextureList();
		for (int i = 0; i < types.length; i++) {
			String name = temp.get(i).getPath();
			switch (name.substring(12)) {
			case "f000.png":
				tps.put(F0, types[i]);
				break;
			case "f100.png":
				tps.put(F1, types[i]);
				break;
			case "f200.png":
				tps.put(F2, types[i]);
				break;
			case "f300.png":
				tps.put(F3, types[i]);
				break;
			case "f400.png":
				tps.put(F4, types[i]);
				break;
			case "f500.png":
				tps.put(F5, types[i]);
				break;
			case "f600.png":
				tps.put(F6, types[i]);
				break;
			case "f700.png":
				tps.put(F7, types[i]);
				break;
			case "f800.png":
				tps.put(F8, types[i]);
				break;
			case "f900.png":
				tps.put(F9, types[i]);
				break;
			case "f1000.png":
				tps.put(F10, types[i]);
				break;
			case "f1100.png":
				tps.put(F11, types[i]);
				break;
			case "f1200.png":
				tps.put(F12, types[i]);
				break;
			case "void000.png":
				tps.put(VOID, types[i]);;
				break;
			case "w000.png":
				tps.put(W0, types[i]);
				break;
			case "w100.png":
				tps.put(W1, types[i]);
				break;
			case "w200.png":
				tps.put(W2, types[i]);
				break;
			case "w300.png":
				tps.put(W3, types[i]);
				break;
			case "w400.png":
				tps.put(W4, types[i]);
				break;
			case "w500.png":
				tps.put(W5, types[i]);
				break;
			case "w600.png":
				tps.put(W6, types[i]);
				break;
			case "w700.png":
				tps.put(W7, types[i]);
				break;
			case "w800.png":
				tps.put(W8, types[i]);
				break;
			case "w900.png":
				tps.put(W9, types[i]);
				break;
			case "w1000.png":
				tps.put(W10, types[i]);
				break;
			case "w1100.png":
				tps.put(W11, types[i]);
				break;
			case "w1200.png":
				tps.put(W12, types[i]);
				break;
			case "wb000.png":
				tps.put(WALL_BASE, types[i]);
				break;
			default:
				break;
			}
		}
	}
	
	public TileMap(InstMaterial[] types, int width, int height) {
		this.width = width;
		this.height = height;
		this.map = new Tile[width][height];
		this.bbmap = new AABB[width][height];
		
		this.types = types;
	}
	
	/**
	 * Generates level
	 * Should be overridden on creation
	 */
	public void generate() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				initTile(x, y, types[0], false);
			}
		}
		gatherNeighbours();
	}
	
	void genLevel(int pixel, int x, int y) {
		switch (pixel) {
		case (0xFF253a5e):
			initTile(x, y, F0, false);
			break;
		case (0xFF3c5e8b):
			initTile(x, y, F1, false);
			break;
		case (0xFF4f8fba):
			initTile(x, y, F2, false);
			break;
		case (0xFF25562e):
			initTile(x, y, F3, false);
			break;
		case (0xFF468232):
			initTile(x, y, F4, false);
			break;
		case (0xFF75a743):
			initTile(x, y, F5, false);
			break;
		case (0xFF4d2b32):
			initTile(x, y, F6, false);
			break;
		case (0xFF7a4841):
			initTile(x, y, F7, false);
			break;
		case (0xFFad7757):
			initTile(x, y, F8, false);
			break;
		case (0xFFa4dddb):
			initTile(x, y, F9, false);
			break;
		case (0xFF19332d):
			initTile(x, y, F10, false);
			break;
		case (0xFFa8ca58):
			initTile(x, y, F11, false);
			break;
		case (0xFFd0da91):
			initTile(x, y, F12, false);
			break;
		case (0xFFda863e):
			initTile(x, y, VOID, true);
			break;
		case (0xFFc09473):
			initTile(x, y, W0, true);
			break;
		case (0xFFd7b594):
			initTile(x, y, W1, true);
			break;
		case (0xFFe7d5b3):
			initTile(x, y, W2, true);
			break;
		case (0xFF884b2b):
			initTile(x, y, W3, true);
			break;
		case (0xFFbe772b):
			initTile(x, y, W4, true);
			break;
		case (0xFFde9e41):
			initTile(x, y, W5, true);
			break;
		case (0xFF752438):
			initTile(x, y, W6, true);
			break;
		case (0xFFa53030):
			initTile(x, y, W7, true);
			break;
		case (0xFFcf573c):
			initTile(x, y, W8, true);
			break;
		case (0xFF341c27):
			initTile(x, y, W9, true);
			break;
		case (0xFF602c2c):
			initTile(x, y, W10, true);
			break;
		case (0xFFe8c170):
			initTile(x, y, W11, true);
			break;
		case (0xFF241527):
			initTile(x, y, W12, true);
			break;
		case (0xFF411d31):
			initTile(x, y, WALL_BASE, true);
		break;
		default:
			initTile(x, y, VOID, true);
			break;
		}
	}
	
	public void load(String path) {
		File file;
		if (path.contains(".")) file = new File(path);
		else file = new File(path + ".png");
		BufferedImage image;
		try {
			image = ImageIO.read(file);
			this.width = image.getWidth();
			this.height = image.getHeight();
			this.map = new Tile[width][height];
			this.bbmap = new AABB[width][height];
			
			int[] cols = new int[width * height];
			cols = image.getRGB(0, 0, width, height, null, 0, width);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = cols[x + y * width];
					genLevel(pixel, x, y);
				}
			}
		} catch (IOException e) {
			System.out.println("Unable to load texture at: " + path);
			e.printStackTrace();
		}
		gatherNeighbours();
	}
	
	//SETTERS
	public void setTile(int x, int y, InstMaterial mat, boolean solid) {
		try {
			map[x][y] = new Tile(this, x, y, mat);
			if (solid) {
				map[x][y].setSolid();
				bbmap[x][y] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1,1));
			}
			gatherNeighbours(x, y);
		} catch (ArrayIndexOutOfBoundsException e) {}//THROWS HELLA EXCEPTIONS
	}
	
	public void initTile(int x, int y, InstMaterial mat, boolean solid) {
		map[x][y] = new Tile(this, x, y, mat);
		if (solid) {
			map[x][y].setSolid();
			bbmap[x][y] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1,1));
		}
	}
	
	public void initTile(int x, int y, TileTypes t, boolean solid) {
		map[x][y] = new Tile(this, x, y, tps.get(t));
		if (solid) {
			map[x][y].setSolid();
			bbmap[x][y] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1,1));
		}
	}
	
	//GETTERS
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public Tile getTile(int x, int y) {
		try {
			return map[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public AABB getBB(int x, int y) {
		try {
			return bbmap[x][y];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Tile[][] getMap() {
		return map;
	}
	
	public AABB[][] getBBMap() {
		return bbmap;
	}
	
	protected void gatherNeighbours() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				gatherNeighbours(x, y);
			}
		}
	}
	
	protected void gatherNeighbours(int x, int y) {
		Tile[] near = new Tile[8];
		for (int i = 0; i < 9; i++) {
			if (i == 4) continue;
			int xi = (i % 3) - 1;
			int yi = (i / 3) - 1;
			int xa = x + xi;
			int ya = y + yi;
			if (xa < 0) xa = width - 1;
			else if (xa > width) xa = 0;
			if (ya < 0) ya = height - 1;
			else if (ya > width) ya = 0;
			
			Tile t = getTile(xa, ya);
			if (i < 4) near[i] = t;
			else if (i > 4) near[i-1] = t;
		}
		map[x][y].setNearby(near);
	}
}
