package nexus.engine.core.render.opengl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nexus.engine.utils.loaders.TextureLoader;
import nexus.engine.utils.loaders.builders.Builder;
import nexus.engine.utils.loaders.builders.TextureBuilder;

public class TextureManager {
	
	private List<Texture> textures, normalMaps, heightMaps, depthMaps;

	/**Default constructor*/
	public TextureManager() {
		textures = new ArrayList<Texture>();
		normalMaps = new ArrayList<Texture>();
		heightMaps = new ArrayList<Texture>();
		depthMaps = new ArrayList<Texture>();
	}
	
	/**
	 * Creates a new {@link TextureLoader} instance and loads the .json files of the 
	 * artifice-engine format for internal use
	 * @param path Path to .json file
	 * @throws IOException
	 */
	public void initFromFile(String path) throws IOException {
		TextureLoader tloader = new TextureLoader();
		
		tloader.initObjectArrayFromFile(path + "textures.json");
		List<TextureBuilder> texBuilders = convertBuilderList(tloader.getAllBuilders());
		for (int i = 0; i < texBuilders.size(); i++) addTexture(texBuilders.get(i).build());
		tloader.reset();
		
		tloader.initObjectArrayFromFile(path + "normalMaps.json");
		List<TextureBuilder> normBuilders = convertBuilderList(tloader.getAllBuilders());
		for (int i = 0; i < normBuilders.size(); i++) addNormalMap(normBuilders.get(i).build());
		tloader.reset();
		
		tloader.initObjectArrayFromFile(path + "heightMaps.json");
		List<TextureBuilder> heightBuilders = convertBuilderList(tloader.getAllBuilders());
		for (int i = 0; i < heightBuilders.size(); i++) addHeightMap(heightBuilders.get(i).build());
		tloader.reset();
		
		tloader = null;
	}
	
	/**Adds a new texture to the list*/
	public void addTexture(Texture t) {
		textures.add(t);
	}
	
	/**Adds a new normal map texture to the list*/
	public void addNormalMap(Texture t) {
		normalMaps.add(t);
	}
	
	/**Adds a new height map texture to the list*/
	public void addHeightMap(Texture t) {
		heightMaps.add(t);
	}
	
	/**Adds a new depth map texture to the list*/
	public void addDepthMap(Texture t) {
		depthMaps.add(t);
	}
	
	/**Destroys all associated objects and clears all lists then logs to Console*/
	public void destroy() {
		for (Texture t : textures) t.destroy();
		for (Texture t : normalMaps) t.destroy();
		for (Texture t : depthMaps) t.destroy();
		for (Texture t : heightMaps) t.destroy();
		textures.clear();
		normalMaps.clear();
		depthMaps.clear();
		heightMaps.clear();
	}
	
	//GETTERS
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
	
	/**
	 * Fetches the Texture with the specified InternalID
	 * @param fetchList List of {@link Texture}s to look in
	 * @param iID The specified InternalID
	 * @return The texture wanted.  Null if no such texture exists
	 */
	public Texture getTextureFromID(List<Texture> fetchList, long iID) {
		for (int i = 0; i < fetchList.size(); i++) {
			Texture temp = fetchList.get(i);
			if (temp.getInternalID() == iID) return temp;
		}
		return null;
	}
	
	//LOCAL
	/**
	 * Converts a {@link Builder} list of {@link TextureBuilder} objects to a {@link TextureBuilder}
	 * list of {@link TextureBuilder} objects. Stupid, but necessary.
	 * @param toConvert {@link Builder} list of {@link TextureBuilder} objects
	 * @return {@link TextureBuilder} list of {@link TextureBuilder} objects
	 */
	@SuppressWarnings("unchecked")
	List<TextureBuilder> convertBuilderList(List<Builder> toConvert) {
		return (List<TextureBuilder>)(List<?>) toConvert;
	}
}
