package nexus.engine.core.render;

import org.joml.Matrix4f;

import nexus.engine.core.render.shader.Shader;
import nexus.engine.core.render.utils.Transformation3D;
import nexus.engine.scene.Scene3d;
import nexus.engine.scene.Skybox;

public class SkyboxRenderer extends ShadedRenderer {

	private Scene3d space;
	
	public SkyboxRenderer(Transformation3D t, Scene3d space) {
		this.t = t;
		this.space = space;
	}

	@Override
	public void init() {
		shader = new Shader("skybox");
		shader.createUniform("projMat");
		shader.createUniform("modelViewMat");
		shader.createUniform("sampler");
		shader.createUniform("ambientLight");
	}
	
	@Override
	public void render() {
		shader.bind();
		
		shader.setUniform("sampler", 0);
		
		Matrix4f projMat = t.getProjectionMatrix();
		shader.setUniform("projMat", projMat);
		Skybox box = space.getSkybox();
		Matrix4f viewMat = t.getViewMatrix();
		float m30 = viewMat.m30();
		float m31 = viewMat.m31();
		float m32 = viewMat.m32();
		viewMat.m30(0);
		viewMat.m31(0);
		viewMat.m32(0);
		Matrix4f modelViewMat = t.buildModelViewMatrix(box, viewMat);
		shader.setUniform("modelViewMat", modelViewMat);
		shader.setUniform("ambientLight", space.getSceneLight().getSkyboxLight());
		
		space.getSkybox().getMesh().render();
		
		viewMat.m30(m30);
		viewMat.m31(m31);
		viewMat.m32(m32);
		shader.unbind();
	}
}
