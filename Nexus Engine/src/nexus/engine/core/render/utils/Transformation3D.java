package nexus.engine.core.render.utils;

import java.lang.Math;

import org.joml.*;

import nexus.engine.core.io.camera.Camera;
import nexus.engine.scene.GameObject;

public class Transformation3D {
	
	private final Matrix4f  projMat, 
							modelMat,
							modelViewMat, 
							modelLightViewMat,
							viewMat, 
							lightViewMat,
							orthoProjMat,
							orthoModelMat;

	public Transformation3D() {
		projMat = new Matrix4f();
		viewMat = new Matrix4f();
		modelViewMat = new Matrix4f();
		orthoProjMat = new Matrix4f();
		modelMat = new Matrix4f();
		orthoModelMat = new Matrix4f();
		modelLightViewMat = new Matrix4f();
		lightViewMat = new Matrix4f();
	}
	public Matrix4f updateProjectionMat(float fov, float w, float h, float znear, float zfar) {
		return projMat.setPerspective(fov, w / h, znear, zfar);
	}
	
	public Matrix4f updateOrthoProjMat(float l, float r, float b, float t, float n, float f) {
		return orthoProjMat.setOrtho(l, r, b, t, n, f);
	}
	
	public Matrix4f updateViewMat(Camera camera) {
		return updateGenericViewMat(camera.getPos(), camera.getRot(), viewMat);
	}
	
	public Matrix4f updateLightViewMatrix(Vector3f pos, Vector3f rot) {
		return updateGenericViewMat(pos, rot, lightViewMat);
	}
	
	public Matrix4f updateGenericViewMat(Vector3f pos, Vector3f rot, Matrix4f mat) {
		mat.identity();
		mat.rotateX((float) Math.toRadians(rot.x))
		   .rotateY((float) Math.toRadians(rot.y))
		   .rotateZ((float) Math.toRadians(rot.z))
		   .translate(-pos.x, -pos.y, -pos.z);
		return mat;
	}
	
	public Matrix4f buildModelMatrix(GameObject object) {
		Quaternionf rot = object.getRot();
		return modelMat.translationRotateScale(
				object.getPos().x, object.getPos().y, object.getPos().z,
				rot.x, rot.y, rot.z, rot.w, 
				object.getScale().x, object.getScale().y, object.getScale().z);
	}
	
	public Matrix4f buildModelViewMatrix(Matrix4f modelMat, Matrix4f viewMat) {
		return viewMat.mulAffine(modelMat, modelViewMat);
	}
	
	public Matrix4f buildModelViewMatrix(GameObject o, Matrix4f viewMat) {
		return buildModelViewMatrix(buildModelMatrix(o), viewMat);
	}
	
	public Matrix4f buildOrthoProjModelMat(GameObject item, Matrix4f ortho) {
		return ortho.mulOrthoAffine(buildModelMatrix(item), orthoModelMat);
	}
	
	public Matrix4f buildModelLightViewMatrix(Matrix4f modelMat, Matrix4f lightViewMat) {
		return lightViewMat.mulAffine(modelMat, modelLightViewMat);
	}
	
	public Matrix4f buildModelLightViewMatrix(GameObject o, Matrix4f lightViewMat) {
		return buildModelLightViewMatrix(buildModelMatrix(o), lightViewMat);
	}
	
	//SETTERS
	public void setLightViewMat(Matrix4f lvm) {
		this.lightViewMat.set(lvm);
	}
	
	//GETTERS
	public Matrix4f getProjectionMatrix() {
		return projMat;
	}
	
	public Matrix4f getOrthoProjMat() {
		return orthoProjMat;
	}
	
	public Matrix4f getViewMatrix() {
		return viewMat;
	}
	
	public Matrix4f getLightViewMat() {
		return lightViewMat;
	}
	
	public static Matrix4f getProjection(Matrix4f target, Vector3f scale, Vector3f pos) {
		return target.translate(pos).scale(scale);
	}
}
