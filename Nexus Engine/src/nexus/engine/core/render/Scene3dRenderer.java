package nexus.engine.core.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.*;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import nexus.engine.core.io.DisplayManager;
import nexus.engine.core.io.camera.Camera;
import nexus.engine.core.render.opengl.*;
import nexus.engine.core.render.shader.FrustumCullingFilter;
import nexus.engine.core.render.shader.Shader;
import nexus.engine.core.render.shader.d3.lighting.DirectionalLight;
import nexus.engine.core.render.shader.d3.lighting.SceneLight;
import nexus.engine.core.render.utils.Transformation3D;
import nexus.engine.core.scene.GameObject;
import nexus.engine.core.scene.Scene3d;

public class Scene3dRenderer extends ShadedRenderer {

	public static final float FOV = (float) Math.toRadians(70.0f);
	public static final float ZNEAR = 0.01f;
	public static final float ZFAR = 1000.0f;
	
	//************************************************
	private Shader depthv;
	
	private DisplayManager mgr;
	private Camera camera;
	private Scene3d scene;
	
	private FrustumCullingFilter fcf;
	private final List<GameObject> filtered;
	private Transformation3D t;
	
	private ShadowMap smap;
	
	private SkyboxRenderer sr;
	
	public Scene3dRenderer(DisplayManager mgr, Scene3d space, Camera camera) {
		this.mgr = mgr;
		this.scene = space;
		this.camera = camera;
		t = new Transformation3D();
		fcf = new FrustumCullingFilter();
		filtered = new ArrayList<GameObject>();
		sr = new SkyboxRenderer(t, space);
	}
	
	@Override
	public void init() {
		smap = new ShadowMap();
		
		setupGameSpace();
		setupDepthShader();
		sr.init();
	}
	
	@Override
	public void render() {
		fcf.updateFrustum(t.getProjectionMatrix(), t.getViewMatrix());
		fcf.filter(scene.getMeshMap());
		fcf.filter(scene.getIMeshMap());
		
		if (scene.isRenderShadows()) renderDepthMap(camera, scene);
		
		glViewport (0, 0, mgr.getWindow().getWidth(), mgr.getWindow().getHeight());
		t.updateProjectionMat(FOV, mgr.getWindow().getWidth(), mgr.getWindow().getHeight(), ZNEAR, ZFAR);
		t.updateViewMat(camera);
		
		renderSpace(camera, scene);
		sr.render();
	}
	
	public void renderSpace(Camera camera, Scene3d space) {
		shader.bind();
		//proj and view mats
		Matrix4f projMat = t.getProjectionMatrix();
		shader.setUniform("projMat", projMat);
		Matrix4f orthoProjMat = t.getOrthoProjMat();
		shader.setUniform("orthoProjMat", orthoProjMat);
		Matrix4f lightViewMat = t.getLightViewMat();
		Matrix4f viewMat = t.getViewMatrix();
		
		space.getSceneLight().render(shader, viewMat);
		shader.setUniform("fog", space.getFog());
		
		shader.setUniform("sampler", 0);
		shader.setUniform("normalMap", 1);
		shader.setUniform("shadowMap", 2);
		shader.setUniform("renderShadow", space.isRenderShadows() ? 1 : 0);
		
		renderNonInstancedMeshes(space, false, shader, viewMat, lightViewMat);
		
		renderInstancedMeshes(space, false, shader, viewMat, lightViewMat);
		
		shader.unbind();
	}
	
	void renderNonInstancedMeshes(Scene3d space, boolean dmap, Shader shader, Matrix4f viewMat, Matrix4f lightViewMat) {
		shader.setUniform("instanced", 0);
		
		Map<Mesh, List<GameObject>> mapmeshes = space.getMeshMap();
		for (Mesh mesh : mapmeshes.keySet()) {
			if (!dmap) {
				shader.setUniform("material", mesh.getMaterial());
				smap.getDepthMap().bind(2);
			}
			mesh.renderList(mapmeshes.get(mesh), (GameObject object) -> {
				Matrix4f modelMat = t.buildModelMatrix(object);
				if (!dmap) {
					Matrix4f modelViewMat = t.buildModelViewMatrix(modelMat, viewMat);
					shader.setUniform("modelViewMat", modelViewMat);
				}
				
				Matrix4f modelLightViewMat = t.buildModelLightViewMatrix(modelMat, lightViewMat);
				shader.setUniform("modelLightViewMat", modelLightViewMat);
			});
		}
	}
	
	void renderInstancedMeshes(Scene3d space, boolean dmap, Shader shader, Matrix4f viewMat, Matrix4f lightViewMat) {
		shader.setUniform("instanced", 1);
		
		Map<InstancedMesh, List<GameObject>> mapmeshes = space.getIMeshMap();
		for (InstancedMesh mesh : mapmeshes.keySet()) {
			if (!dmap) {
				shader.setUniform("material", mesh.getMaterial());
				smap.getDepthMap().bind(2);
			}
			filtered.clear();
			for (GameObject object : mapmeshes.get(mesh)) if (object.insideFrustum()) filtered.add(object);
			mesh.renderListInstanced(filtered, dmap, t, viewMat, lightViewMat);
		}
	}
	
	public void renderDepthMap(Camera camera, Scene3d space) {
		glBindFramebuffer(GL_FRAMEBUFFER, smap.getFBO());
		glViewport (0, 0, ShadowMap.MAPSIZE, ShadowMap.MAPSIZE);
		glClear(GL_DEPTH_BUFFER_BIT);
		depthv.bind();
		
		DirectionalLight dl = space.getSceneLight().getSun();
		Vector3f dir = dl.getDir();
		
		float lax = (float) Math.toDegrees(Math.acos(dir.z));
		float lay = (float) Math.toDegrees(Math.asin(dir.x));
		float laz = 0;
		Matrix4f lightViewMat = t.updateLightViewMatrix(new Vector3f(dir).mul(dl.getShadowPosMul()), new Vector3f(lax, lay, laz));
		DirectionalLight.OrthoCoords ocoord = dl.getOrthoCoords();
		Matrix4f orthoProjMat = t.updateOrthoProjMat(ocoord.left, ocoord.right, ocoord.bottom, ocoord.top, ocoord.near, ocoord.far);
		
		depthv.setUniform("orthoProjMat", orthoProjMat);
		
		renderNonInstancedMeshes(space, true, depthv, null, lightViewMat);
		
		renderInstancedMeshes(space, true, depthv, null, lightViewMat);
		
		depthv.unbind();
		glBindFramebuffer(GL_FRAMEBUFFER, 0);
	}
	
	@Override
	public void destroy() {
		super.destroy();
		if (depthv != null) depthv.destroy();
		sr.destroy();
	}
	
	//************************* LOCAL SET UP METHODS FOR SHADERS *****************************
	void setupGameSpace() {
		shader = new Shader("scene");
		shader.createUniform("projMat");
		shader.createUniform("modelViewMat");
		shader.createUniform("sampler");
		shader.createUniform("normalMap");
		
		//Shadow mapping
		shader.createUniform("shadowMap");
		shader.createUniform("orthoProjMat");
		shader.createUniform("modelLightViewMat");
		shader.createUniform("renderShadow");
		
		shader.createMaterialUniform("material");
		
		shader.createUniform("specPower");
		shader.createUniform("ambientLight");
		shader.createDirectionalLightUniform("sun");
		shader.createSpotLightUniform("spotLights", SceneLight.MAX_SPOT_LIGHTS);
		shader.createPointLightUniform("pointLights", SceneLight.MAX_POINT_LIGHTS);
		
		shader.createFogUniform("fog");
		
		shader.createUniform("jointsMat");
		shader.createUniform("instanced");
	}
	
	void setupDepthShader() {
		depthv = new Shader("depthVertex");
		depthv.createUniform("instanced");
		depthv.createUniform("orthoProjMat");
		depthv.createUniform("modelLightViewMat");
		depthv.createUniform("jointsMat");
	}
}
