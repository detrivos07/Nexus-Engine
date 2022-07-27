package com.detrivos.game.level.tile;

import com.detrivos.eng.render.*;

public class Tile {

	public static Tile[] tiles = new Tile[1023];
	public static int nID = 0;
	
	//init Tiles here
	public static final Tile NA = new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 9, 90).setSolid();
	public static final Tile grass = new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 2, 0, 2);
	public static final Tile stone1 = new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 3, 0, 3).setSolid();
	public static final Tile brick1 = new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 5, 0, 3).setSolid();
	public static final Tile woodFloor1 = new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 5, 1, 3);
	
	
	//dirt1 Tiles
	public static final Tile dirt1 = new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 0, 0);
	
	public static final Tile[] dirtToGrass = new Tile[] {
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 5, 50),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 6, 60),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 7, 70),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 8, 80),
			
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 1, 0, 1),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 1, 1, 11),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 4, 40),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 1, 10),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 2, 20),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 0, 3, 30),
			
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 1, 5, 51),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 1, 2, 21),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 1, 3, 31),
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 1, 4, 41),
			
			new Tile(MasterRenderer.activeTexPack.getMainAtlas(), 1, 6, 71)
	};
	
	private boolean solid = false;
	private int id;
	private float xOffset, yOffset;
	private Texture texture;
	private TextureAtlas tAtlas;
	
	private float[] texUnits;
	
	public Tile(TextureAtlas tAtlas, int xOffset, int yOffset, int id) {
		this.id = nID;
		nID++;
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.tAtlas = tAtlas;
		this.texture = new Texture("/sprites/tiles/tAtlas");
		if (tiles[this.id] != null) throw new IllegalStateException("Tile at [" + this.id + "] already exists!");
		else tiles[this.id] = this;
		
		float numRows = (float) (this.tAtlas.getSize() / this.tAtlas.getTexSize());
		
		texUnits = new float[] {
				(0f + (float) this.xOffset) / (float) numRows, (0f + (float) this.yOffset) / (float) numRows,//currently only using this vector to render
				(1f + (float) this.xOffset) / (float) numRows, (0f + (float) this.yOffset) / (float) numRows,
				(1f + (float) this.xOffset) / (float) numRows, (1f + (float) this.yOffset) / (float) numRows,
				(0f + (float) this.xOffset) / (float) numRows, (1f + (float) this.yOffset) / (float) numRows,
		};
	}
	
	public Tile(Texture texture, boolean rotatable) {
		this.id = nID;
		nID++;
		this.texture = texture;
		if (tiles[id] != null) throw new IllegalStateException("Tile at [" + id + "] already exists!");
		else tiles[id] = this;
	}
	
	public Tile setSolid() {
		solid = true;
		return this;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public int getID() {
		return id;
	}
	
	public Texture getTexture() {
		return texture;
	}
	
	public TextureAtlas getTextureAtlas() {
		return tAtlas;
	}
	
	public float[] getTexUnits() {
		return texUnits;
	}
	
	public static int getTileAmt() {
		return nID;
	}
}
