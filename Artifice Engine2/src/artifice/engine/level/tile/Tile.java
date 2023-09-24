package artifice.engine.level.tile;

import artifice.engine.render.model.InstMaterial;

public class Tile {
	
	private int x, y;
	private TileMap map;
	private Tile[] nearby;
	
	private InstMaterial imat;
	private boolean solid = false;
	
	public Tile(TileMap map, int x, int y, InstMaterial material) {
		this.map = map;
		this.x = x;
		this.y = y;
		this.imat = material;
	}
	
	public void setNearby(Tile... nearby) {
		this.nearby = nearby;
	}
	
	public void setSolid() {
		solid = true;
	}
	
	//GETTERS
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public TileMap getMap() {
		return map;
	}
	
	public Tile[] getNearby() {
		return nearby;
	}
	
	public InstMaterial getImat() {
		return imat;
	}
}
