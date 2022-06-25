package nexus.engine.core.io;

import java.io.IOException;

import com.google.common.flogger.FluentLogger;
import com.google.gson.stream.JsonReader;

import nexus.engine.core.render.opengl.GLWindowContext;
import nexus.engine.core.utils.FileUtils;
import nexus.engine.utils.loaders.JsonLoader;

public class DisplayManager implements JsonLoader {
	private static final String defaultPath = "/window/defaultWindow.json";
	
	private Window window;
	private WindowContext context;
	
	public DisplayManager() throws IOException {
		JsonReader reader = gson.newJsonReader(FileUtils.newReader(defaultPath));
		window = gson.fromJson(reader, Window.class);
		context = window.getCTX().contains("gl") ? new GLWindowContext() : null;
		window.setContext(context);
		reader.close();
	}
	
	/**Initializes the window and the context*/
	public void init() {
		window.init();
		context.createContext();
	}
	
	/**Runs the basic updates in the window*/
	public void update() {
		window.update();
	}
	
	/**Prepares the display for a new frame*/
	public void preRender() {
		context.preRender();
	}
	
	/**Wraps up a frame*/
	public void postRender() {
		window.render();
	}
	
	/**Destroys all local objects*/
	public void destroy() {
		window.destroy();
		window = null;
	}
	
	//GETTERS
	public Window getWindow() {
		return window;
	}
}
