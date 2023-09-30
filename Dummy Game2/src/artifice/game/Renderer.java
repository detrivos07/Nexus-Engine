package artifice.game;

import artifice.engine.io.Camera;
import artifice.engine.level.Level;
import artifice.engine.render.IRenderer;
import artifice.engine.render.shader.Shader;
import nexus.engine.core.io.Window;

public class Renderer implements IRenderer {
	
	Shader inst, basic;
	
	@Override
	public void init(Window window) {
		basic = new Shader("basic");
		inst = new Shader("instanced");
	}
	
	@Override
	public void render(Window window, Camera camera, Level level) {
		if (level != null) {
			level.renderLevel(inst, camera, level.getMap());
			level.renderEnts(basic, camera);
		}
	}
	
	@Override
	public void destroy() {
		inst.destroy();
		basic.destroy();
	}
}
