package nexus.engine.core.render;

import java.util.ArrayList;
import java.util.List;

import nexus.engine.core.render.opengl.Texture;

public class TextureManager {
	
	private List<Texture> textures, normalMaps, heightMaps, depthMaps;
	
	/**Default constructor*/
	public TextureManager() {
		textures = new ArrayList<Texture>();
		normalMaps = new ArrayList<Texture>();
		heightMaps = new ArrayList<Texture>();
		depthMaps = new ArrayList<Texture>();
	}
	
	//ADD TO LISTS ****************************************
	public void addTexture(Texture texture) {
		this.textures.add(texture);
	}
	
	public void addNormalMap(Texture texture) {
		this.normalMaps.add(texture);
	}
	
	public void addHeightMap(Texture texture) {
		this.heightMaps.add(texture);
	}
	
	public void addDepthMap(Texture texture) {
		this.depthMaps.add(texture);
	}
	
	//GETTERS ********************************************************
	public List<Texture> textures() {
		return textures;
	}
	
	public List<Texture> normalmaps() {
		return normalMaps;
	}
	
	public List<Texture> heightmaps() {
		return heightMaps;
	}
	
	public List<Texture> depthmaps() {
		return depthMaps;
	}
}
