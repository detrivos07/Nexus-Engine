package nexus.engine.core.render.shader.d3.lighting;

import java.lang.Math;

import org.joml.*;

import nexus.engine.core.render.shader.Shader;

public class SceneLight {

	public static final int MAX_POINT_LIGHTS = 8;
	public static final int MAX_SPOT_LIGHTS = 4;

	private Vector3f ambient, sbox;
	private DirectionalLight sun;
	private PointLight[] points;
	private SpotLight[] spots;
	
	private float langle = 0f;
	
	private float specPower;
	
	public SceneLight() {
		this.ambient = new Vector3f(0.3f);
		this.sbox = new Vector3f(1.0f);
		sun = new DirectionalLight(new Vector3f(1), new Vector3f(1,1f,-0.55f), 1.0f);
		sun.setShadowPosMul(5);
		float i = 50.0f;
		sun.setOrthoCoords(-i, i, -i, i, -1.0f, 100.0f);
		points = new PointLight[MAX_POINT_LIGHTS];
		spots = new SpotLight[MAX_SPOT_LIGHTS];
		specPower = 10f;
	}
		
	public SceneLight(Vector3f ambient, DirectionalLight sun) {
		this.ambient = ambient;
		this.sun = sun;
		points = new PointLight[MAX_POINT_LIGHTS];
		spots = new SpotLight[MAX_SPOT_LIGHTS];
		specPower = 10f;
	}
	
	public void update() {
		//langle += 0.5f;
		if (langle > 360) langle = 0;
		
		double angRad = Math.toRadians(langle);
		sun.getDir().x = (float) Math.sin(angRad);
		//sun.getDir().y = (float) Math.cos(angRad);
	}
	
	public void render(Shader scene, Matrix4f viewMat) {
		scene.setUniform("ambientLight", ambient);
		scene.setUniform("specPower", specPower);
		
		PointLight currLight;
		Vector3f lightPos;
		Vector4f aux;
		if (points != null && points[0] != null) for (int i = 0; i < points.length; i++) {
			currLight = new PointLight(points[i]);
			lightPos = currLight.getPos();
			aux = new Vector4f(lightPos, 1);
			aux.mul(viewMat);
			lightPos.x = aux.x;
			lightPos.y = aux.y;
			lightPos.z = aux.z;
			scene.setUniform("pointLights", currLight, i);
		}
		
		
		SpotLight currSpot;
		Vector4f spotDir;
		Vector3f spotPos;
		Vector4f auxSpot;
		if (spots != null && spots[0] != null) for (int i = 0; i < spots.length; i++) {
			currSpot = new SpotLight(spots[i]);
			spotDir = new Vector4f(currSpot.getDir(), 0);
			spotDir.mul(viewMat);
			currSpot.setDir(new Vector3f(spotDir.x, spotDir.y, spotDir.z));
			
			spotPos = currSpot.getPoint().getPos();
			auxSpot = new Vector4f(spotPos, 1);
			auxSpot.mul(viewMat);
			spotPos.x = auxSpot.x;
			spotPos.y = auxSpot.y;
			spotPos.z = auxSpot.z;
			scene.setUniform("spotLights", currSpot, i);
		}
		
		
		DirectionalLight currSun = new DirectionalLight(sun);
		Vector4f dir = new Vector4f(currSun.getDir(), 0);
		dir.mul(viewMat);
		currSun.setDir(new Vector3f(dir.x, dir.y, dir.z));
		scene.setUniform("sun", currSun);
	}
	
	public void destroy() {
		
	}
	
	public void createPointLight(Vector3f pos) {//TODO:: finish light creation and handling
		for (int i = 0; i < MAX_POINT_LIGHTS; i++) if (points[i] == null) {
			Vector3f col = new Vector3f(1,1,1);
			float intensity = 1.0f;
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
			light.setAttenuation(att);
			points[i] = light;
		} else if (i + 1 == MAX_POINT_LIGHTS) throw new IllegalStateException("Too many point lights!");
	}
	
	public void createPointLight(Vector3f pos, float intensity) {
		for (int i = 0; i < MAX_POINT_LIGHTS; i++) if (points[i] == null) {
			Vector3f col = new Vector3f(1,1,1);
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
			light.setAttenuation(att);
			points[i] = light;
		} else if (i + 1 == MAX_POINT_LIGHTS) throw new IllegalStateException("Too many point lights!");
	}
	
	public void createPointLight(Vector3f pos, Vector3f col) {
		for (int i = 0; i < MAX_POINT_LIGHTS; i++) if (points[i] == null) {
			float intensity = 1.0f;
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
			light.setAttenuation(att);
			points[i] = light;
		} else if (i + 1 == MAX_POINT_LIGHTS) throw new IllegalStateException("Too many point lights!");
	}
	
	public void createPointLight(Vector3f pos, Vector3f col, float intensity) {
		for (int i = 0; i < MAX_POINT_LIGHTS; i++) if (points[i] == null) {
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
			light.setAttenuation(att);
			points[i] = light;
		} else if (i + 1 == MAX_POINT_LIGHTS) throw new IllegalStateException("Too many point lights!");
	}
	
	public void createSpotLight(Vector3f pos) {
		for (int i = 0; i < MAX_SPOT_LIGHTS; i++) if (spots[i] == null) {
			Vector3f col = new Vector3f(1,1,1);
			float intensity = 1.0f;
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
			light.setAttenuation(att);
			float range = (float) Math.cos(Math.toRadians(140));
			spots[i] = new SpotLight(light, new Vector3f(0, 0, -1), range);
		} else if (i + 1 == MAX_SPOT_LIGHTS) throw new IllegalStateException("Too many spot lights!");
	}
	
	public void createSpotLight(Vector3f pos, float intensity) {
		for (int i = 0; i < MAX_SPOT_LIGHTS; i++) if (spots[i] == null) {
			Vector3f col = new Vector3f(1,1,1);
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
			light.setAttenuation(att);
			float range = (float) Math.cos(Math.toRadians(140));
			spots[i] = new SpotLight(light, new Vector3f(0, 0, -1), range);
		} else if (i + 1 == MAX_SPOT_LIGHTS) throw new IllegalStateException("Too many spot lights!");
	}
	
	public void createSpotLight(Vector3f pos, Vector3f col, Vector3f dir) {
		for (int i = 0; i < MAX_SPOT_LIGHTS; i++) if (spots[i] == null) {
			float intensity = 1.0f;
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
			light.setAttenuation(att);
			float range = (float) Math.cos(Math.toRadians(140));
			spots[i] = new SpotLight(light, new Vector3f(dir), range);
		} else if (i + 1 == MAX_SPOT_LIGHTS) throw new IllegalStateException("Too many spot lights!");
	}
	
	public void createSpotLight(Vector3f pos, Vector3f col, float intensity) {
		for (int i = 0; i < MAX_SPOT_LIGHTS; i++) if (spots[i] == null) {
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
			light.setAttenuation(att);
			float range = (float) Math.cos(Math.toRadians(140));
			spots[i] = new SpotLight(light, new Vector3f(0, 0, -1), range);
		} else if (i + 1 == MAX_SPOT_LIGHTS) throw new IllegalStateException("Too many spot lights!");
	}
	
	public void createSpotLight(Vector3f pos, Vector3f col, Vector3f dir, float intensity) {
		for (int i = 0; i < MAX_SPOT_LIGHTS; i++) if (spots[i] == null) {
			PointLight light = new PointLight(col, pos, intensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.02f);
			light.setAttenuation(att);
			float range = (float) Math.cos(Math.toRadians(140));
			spots[i] = new SpotLight(light, new Vector3f(dir), range);
		} else if (i + 1 == MAX_SPOT_LIGHTS) throw new IllegalStateException("Too many spot lights!");
	}
	
	//SETTERS
	public void setAmbient(Vector3f ambient) {
		this.ambient = ambient;
	}
	
	public void setSkyboxLight(Vector3f sbox) {
		this.sbox = sbox;
	}

	public void setSun(DirectionalLight sun) {
		this.sun = sun;
	}

	public void setPoints(PointLight... points) {
		this.points = points;
	}

	public void setSpots(SpotLight... spots) {
		this.spots = spots;
	}

	//GETTERS
	public Vector3f getAmbient() {
		return ambient;
	}
	
	public Vector3f getSkyboxLight() {
		return sbox;
	}
	
	public DirectionalLight getSun() {
		return sun;
	}
	
	public PointLight[] getPoints() {
		return points;
	}
	
	public SpotLight[] getSpots() {
		return spots;
	}
}
