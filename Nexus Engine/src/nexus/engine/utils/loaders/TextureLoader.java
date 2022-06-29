package nexus.engine.utils.loaders;

import java.io.IOException;

import com.google.gson.stream.JsonReader;

import nexus.engine.core.utils.FileUtils;
import nexus.engine.utils.loaders.builders.TextureBuilder;

public class TextureLoader extends Loader implements JsonLoader {
	
	@Override
	public void initObjectArrayFromFile(String path) throws IOException {
		JsonReader reader = gson.newJsonReader(FileUtils.newReader(path));
		TextureBuilder[] loadedBuilders = gson.fromJson(reader, TextureBuilder[].class);
		addObjectsToList(loadedBuilders);
		reader.close();
	}
	
	//GETTERS
	/**
	 * Fetches builder instance from its Name, returns NULL if no Builder can be found
	 * @param name The specified name of the Builder instance
	 * @return The Builder object with the specified Name, NULL if no Builder exists
	 */
	public TextureBuilder getBuilderFromMainTag(String tag) {
		for (int i = 0; i < builders.size(); i++) {
			TextureBuilder temp = (TextureBuilder) builders.get(i);
			if (temp.getTags()[0] == tag) return temp;
		}
		return null;
	}

	@Override
	public void initObjectFromFile(String path) throws IOException {
	}
}
