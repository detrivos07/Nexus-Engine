package artifice.engine.render;

import static org.lwjgl.opengl.GL11.*;

import artifice.engine.io.Camera;
import artifice.engine.level.Level;

public interface IRenderer {
	void init();
	void render(Camera camera, Level level);
	void destroy();
	
	default void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	}
}
