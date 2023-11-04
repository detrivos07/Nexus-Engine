package artifice.engine.render.model;

import org.joml.Vector2i;

import nexus.engine.core.render.opengl.TextureAtlas;

public class InstMaterial {
	static int nID = 0;
	
	private int id;
	private float xOff, yOff;
	private float[] texUnits;
	private TextureAtlas atlas;
	
	public InstMaterial(TextureAtlas atlas, Vector2i offset) {
		id = nID;
		nID++;
		this.atlas = atlas;
		this.xOff = offset.x;
		this.yOff = offset.y;
		
		float numRows = this.atlas.getSize();
		texUnits = new float[] {
				(0f + this.xOff) / numRows, (0f + this.yOff) / numRows,
				(1f + this.xOff) / numRows, (0f + this.yOff) / numRows,
				(1f + this.xOff) / numRows, (1f + this.yOff) / numRows,
				(0f + this.xOff) / numRows, (1f + this.yOff) / numRows,
		};
	}
	
	public int getID() {
		return id;
	}
	
	public TextureAtlas getTextureAtlas() {
		return atlas;
	}
	
	public float[] getTexUnits() {
		return texUnits;
	}
	
	public static int getTileAmt() {
		return nID;
	}
}
