package nexus.engine.utils.loaders;

import java.io.IOException;

import com.google.gson.Gson;

public interface JsonLoader {
	
	public static Gson gson = new Gson();
	
	/**TODO dvs :: Check old definitions
	 * Reads the given json file and deserializes the Builder objects
	 * @param path The path, from the working directory, to the given file 
	 * (i.e.) /artifice/res/...
	 * @throws IOException
	 */
	public default void initObjectArrayFromFile(String path) throws IOException {
		
	}
	
	/**TODO dvs :: Check old definitions
	 * Reads the given json file and deserializes a new object
	 * @param path The path, from the working directory, to the given file 
	 * (i.e.) /artifice/res/...
	 * @throws IOException
	 */
	public default void initObjectFromFile(String path) throws IOException {
		
	}
	
	default void getString(String path) {
		
	}
}
