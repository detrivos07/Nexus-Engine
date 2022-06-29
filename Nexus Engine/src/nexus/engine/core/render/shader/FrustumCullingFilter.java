package nexus.engine.core.render.shader;

import java.lang.Math;
import java.util.List;
import java.util.Map;

import org.joml.*;

import nexus.engine.core.render.opengl.Mesh;
import nexus.engine.scene.GameObject;

public class FrustumCullingFilter {

	private final Matrix4f projViewMat;
	private FrustumIntersection frustumInt;
	
	public FrustumCullingFilter() {
		projViewMat = new Matrix4f();
		frustumInt = new FrustumIntersection();
	}
	
	public void updateFrustum(Matrix4f projMat, Matrix4f viewMat) {
		projViewMat.set(projMat);
		projViewMat.mul(viewMat);
		frustumInt.set(projViewMat);
	}
	
	public void filter(Map<? extends Mesh, List<GameObject>> mapmesh) {
		for (Map.Entry<? extends Mesh, List<GameObject>> entry : mapmesh.entrySet()) {
			List<GameObject> objects = entry.getValue();
			filter(objects, entry.getKey().getBoundingRadius());
		}
	}
	
	public void filter(List<GameObject> objects, float mbRadius) {
		float br;
		Vector3f pos;
		for (GameObject object : objects) {
			br = Math.max(object.getScale().x, Math.max(object.getScale().y, object.getScale().z)) * mbRadius;
			pos = object.getPos();
			object.setInFrustum(insideFrustum(pos.x, pos.y, pos.z, mbRadius));
		}
	}
	
	public boolean insideFrustum(float x, float y, float z, float boundingRadius) {
		return frustumInt.testSphere(x, y, z, boundingRadius);
	}
}
