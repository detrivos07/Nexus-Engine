package nexus.engine.core.render;

import nexus.engine.core.render.shader.Shader;
import nexus.engine.core.render.utils.Transformation3D;

public abstract class ShadedRenderer implements IRenderer {
	
	protected Shader shader;
	protected Transformation3D t;
	
	@Override
	public void destroy() {
		if (shader != null) shader.destroy();
	}
}