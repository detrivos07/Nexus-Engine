package com.detrivos.game.level;

import static com.detrivos.eng.handle.Handler.*;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.Random;

import javax.imageio.ImageIO;

import org.joml.*;

import com.detrivos.game.level.tile.*;
import com.detrivos.game.utils.AABB;

public class Level {
	
	private Random random = new Random();

	private int viewX, viewY;
	private Matrix4f level;
	private Dimension size;
	private AABB[] boundingBoxes;
	
	public EntityHandler handler;
	public Tile[] tilesAt;
	public int[] tiles;
	
	public Level(Dimension size, int[] tiles, Tile[] tilesAt, AABB[] boundingBoxes) {
		this.handler = new EntityHandler(this);
		this.size = size;
		this.level = new Matrix4f().translate(new Vector3f()).scale(absScale);
		this.tiles = tiles;
		this.tilesAt = tilesAt;
		this.boundingBoxes = boundingBoxes;
		handler.generatePlayerPosition((int) size.getWidth(), (int) size.getHeight());
		handler.generateMonsters((int) size.getWidth(), (int) size.getHeight(), 8, 4);
		//handler.generateMonsters((int) size.getWidth(), (int) size.getHeight(), 25);
	}
	
	public Level(String path) {
		handler = new EntityHandler(this);
		loadLevel(path);
		handler.loadEntitySheet(path);
	}
	
	private void loadLevel(String path) {
		try {
			BufferedImage lvl = ImageIO.read(Level.class.getResource("/levels/tiles/" + path + ".png"));
			size = new Dimension(lvl.getWidth(), lvl.getHeight());
			int w = lvl.getWidth();
			int h = lvl.getHeight();
			this.level = new Matrix4f().translate(new Vector3f()).scale(absScale);
			
			int[] colSheet = lvl.getRGB(0, 0, w, h, null, 0, w);
			tiles = new int[w * h];
			tilesAt = new Tile[w * h];
			boundingBoxes = new AABB[w * h];
			
			for (int y = 0; y < h; y++) {
				for (int x = 0; x < w; x++) {
					int pixel = colSheet[x + y * w];
					
					Tile t;
					switch (pixel) {
					case 0xFFFFFFFF:
						if (random.nextInt(2) == 0) t = initTile(x, y, w, 1);// grass1
						else t = initTile(x, y, w, 3);
						break;
					case 0xFF000000:
						t = initTile(x, y, w, 2);//stone
						break;
					default:
						t = initTile(x, y, w, 0);// N/A
						break;
					}
					
					if (t != null) setTile(t, x, y);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void tick() {
		handler.tick((float) (1.0 / 60.0));
	}
	
	public List<TileData> gatherData() {
		List<TileData> data = new ArrayList<TileData>();
		
		int posX = ((int) camera.getPos().x) / (absScale * 2);
		int posY = ((int) camera.getPos().y) / (absScale * 2);
		
		for (int y = 0; y < viewY; y++) {
			for (int x = 0; x < viewX; x++) {
				Tile t = getTile(x - posX - (viewX / 2) + 1, y + posY - (viewY / 2));
				if (t != null) {
					data.add(new TileData(new Vector2i(x - posX - (viewX / 2) + 1, -y - posY + (viewY / 2)), t.getTexUnits()));
				}
			}
		}
		return data;
	}
	
	public List<TilePosition> findTiles() {
		int posX = ((int) camera.getPos().x) / (absScale * 2);
		int posY = ((int) camera.getPos().y) / (absScale * 2);
		
		List<Tile> tiles = new ArrayList<Tile>();
		List<Vector2i> positions = new ArrayList<Vector2i>();
		
		for (int y = 0; y < viewY; y++) {
			for (int x = 0; x < viewX; x++) {
				Tile t = getTile(x - posX - (viewX / 2) + 1, y + posY - (viewY / 2));
				if (t != null) {
					tiles.add(t);
					positions.add(new Vector2i(x - posX - (viewX / 2) + 1, -y - posY + (viewY / 2)));
				}
			}
		}
		
		List<Tile> allTiles = new ArrayList<Tile>();
		
		for (int i = 0; i < tiles.size(); i++) {
			if (!allTiles.contains(tiles.get(i))) allTiles.add(tiles.get(i));
		}
		
		List<List<Vector2i>> tilePos = new ArrayList<List<Vector2i>>();
		List<TilePosition> tp = new ArrayList<TilePosition>();
		
		for (int i = 0; i < allTiles.size(); i++) {
			tilePos.add(new ArrayList<Vector2i>());
			tp.add(new TilePosition(allTiles.get(i)));
		}
		
		String[] tex = new String[allTiles.size()];
		for (int i = 0; i < tex.length; i++) tex[i] = allTiles.get(i).getTexture().getPath();
		
		for (int i = 0; i < tiles.size(); i++) {
			for (int j = 0; j < allTiles.size(); j++) {
				if (tiles.get(i).getID() == allTiles.get(j).getID()) {
					tilePos.get(j).add(positions.get(i));
					break;
				}
			}
		}
		
		for (int i = 0; i < tilePos.size(); i++) {
			for (int u = 0; u < tilePos.get(i).size(); u++) {
				tp.get(i).addPositions(tilePos.get(i));;
			}
		}
		return tp;
	}
	
	private Tile initTile(int x, int y, int w, int tile) {
		Tile t = Tile.tiles[tile];
		tilesAt[x + y * w] = t;
		return t;
	}
	
	public void calculateView() {
		viewX = ((int) window.getSize().getWidth()) / (absScale * 2) + 4;
		viewY = ((int) window.getSize().getHeight()) / (absScale * 2) + 4;
	}
	
	public Level setTile(Tile tile, int x, int y) {
		int w = (int) size.getWidth();
		tiles[x + y * w] = tile.getID();
		if (tile.isSolid()) boundingBoxes[x + y * w] = new AABB(new Vector2f(x * 2, -y * 2), new Vector2f(1, 1));
		else boundingBoxes[x + y * w] = null;
		return this;
	}
	//GETTERS
	public Tile getTile(int x, int y) {
		try {
			return Tile.tiles[tiles[x + y * (int) size.getWidth()]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Tile getTilesAt(int x, int y) {
		try {
			return tilesAt[x + y * (int) size.getWidth()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public AABB getTileBoundingBox(int x, int y) {
		try {
			return boundingBoxes[x + y * (int) size.getWidth()];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	public Dimension getDimension() {
		return size;
	}
	
	public Matrix4f getLevelData() {
		return level;
	}
}
