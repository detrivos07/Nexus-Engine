package nexus.engine.core.utils;

import java.io.InputStreamReader;

public class FileUtils {
	
	public static InputStreamReader newReader(String path) {
		return new InputStreamReader(FileUtils.class.getResourceAsStream(path));
	}
}
