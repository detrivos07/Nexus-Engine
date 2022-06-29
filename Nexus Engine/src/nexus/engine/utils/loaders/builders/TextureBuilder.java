package nexus.engine.utils.loaders.builders;

import nexus.engine.core.render.opengl.Texture;

public class TextureBuilder extends Builder {
	
	private String path;
	private String[] tags;
	
	/**
	 * Takes in the main data needed for the engine about a texture
	 * @param iID Internal ID of the texture
	 * @param path Path to the specified texture
	 * @param tags Tags of the texture to use internally
	 */
	public TextureBuilder(long iID, String path, String... tags) {
		super(iID);
		this.path = path;
		this.tags = tags;
	}
	
	@Override
	public Texture build() {
		return new Texture(iID, path, tags);
	}
	
	//GETTERS
	public String getPath() {
		return path;
	}
	
	public String[] getTags() {
		return tags;
	}
}
