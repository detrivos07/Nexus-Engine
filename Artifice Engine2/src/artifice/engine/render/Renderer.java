package artifice.engine.render;

import artifice.engine.io.Camera;
import artifice.engine.level.Level;
import nexus.engine.core.io.Window;
import nexus.engine.core.render.shader.Shader;

public class Renderer implements IRenderer {
	
	Shader inst, basic;
	
	@Override
	public void init(Window window) {
		basic = new Shader("basic");
		basic.createUniform("sampler");
		basic.createUniform("projMat");
		inst = new Shader("instanced");
		inst.createUniform("sampler");
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
