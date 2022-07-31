package nexus.demo;

import static org.lwjgl.glfw.GLFW.*;

import java.io.IOException;

import org.joml.*;

import nexus.engine.IProgram;
import nexus.engine.core.io.*;
import nexus.engine.core.render.Scene3dRenderer;
import nexus.engine.core.render.opengl.*;
import nexus.engine.scene.*;
import nexus.engine.utils.loaders.OBJLoader;

public class DemoGame implements IProgram {
	
	private TextureManager texManager;
	
	private Scene3dRenderer renderer;
	private Camera camera;
	private Scene3d scene;
	
	private final Vector3f cameraInc;
	private static final float CAMERA_STEP = 0.07f;
	
	private Terrain terrain;
	private GameObject plane;
	
	private Keyboard board;
	private Mouse mouse;
	
	public DemoGame() {
		cameraInc = new Vector3f();
		scene = new Scene3d();
	}
	
	@Override
	public void init(DisplayManager display) {
		board = Keyboard.getInstance();
		mouse = Mouse.getInstance();
		
		texManager = new TextureManager();
		try {
			texManager.initFromFile("/textures/");
		} catch (IOException e) {
			System.out.println("Unable to read file!");
		} catch (NullPointerException e) {
			System.out.println("Unable to find file!");
			e.printStackTrace();
		}
		camera = new Camera();
		
		scene.init();
		
		renderer = new Scene3dRenderer(display, scene, camera);
		renderer.init();
		
		
		Mesh mesh = OBJLoader.loadMesh("/models/plane.obj");
		Material mat = new Material(texManager.getTextureFromID(texManager.textures(), 0).loadSTB());
		mat.setNormalMap(texManager.getTextureFromID(texManager.normalmaps(), 0).loadSTB());
		mesh.setMaterial(mat);
		plane = new GameObject(mesh);
		plane.setPos(new Vector3f(0, 0, -1.4f));
		plane.setRot(new Quaternionf(0.7071068f, 0, 0, 0.7071068f));
		plane.setScale(new Vector3f(1.8f,1f,1f));
		
		scene.addGameObjects(plane);
		
		terrain = new Terrain(2, new Vector3f(10), -0.1f, 0.1f, "res/heightMaps/tile.png", "res/terrain.png", 40);
		scene.addGameObjects(terrain.getMeshes());
		
		scene.getSceneLight().getSun().setIntensity(1.0f);
		scene.getSceneLight().getAmbient().set(1.0f);
	}
	
	@Override
	public void input(DisplayManager display) {
		cameraInc.set(0, 0, 0);
		if (board.check(GLFW_KEY_W)) cameraInc.z += -1;
		else if (board.check(GLFW_KEY_S)) cameraInc.z += 1;
		if (board.check(GLFW_KEY_A)) cameraInc.x += -1;
		else if (board.check(GLFW_KEY_D)) cameraInc.x += 1;
		if (board.check(GLFW_KEY_LEFT_SHIFT)) cameraInc.y += -1;
		else if (board.check(GLFW_KEY_SPACE)) cameraInc.y += 1;
		//else cameraInc.y += -1.5f;
		
		if (mouse.check(GLFW_MOUSE_BUTTON_LEFT) == 1) {
			Vector2f rotv = new Vector2f(mouse.getDisplayVec());
			mouse.getDisplayVec().zero();
			camera.rotate(rotv.x * Mouse.MOUSE_SENS, rotv.y * Mouse.MOUSE_SENS, 0);
		}
	}
	
	@Override
	public void update(DisplayManager display) {
		camera.move(cameraInc.x * CAMERA_STEP, cameraInc.y * CAMERA_STEP, cameraInc.z * CAMERA_STEP);
		
		//float height = terrain.getHeight(camera.getPos()) + 1.0f;
//		if (camera.getPos().y <= height) {
//			camera.getPos().lerp(new Vector3f(camera.getPos().x, height, camera.getPos().z), 0.21f);
//			camera.setPos(camera.getPos().x, height, camera.getPos().z);
//		}
		
		
		scene.update();
	}
	
	@Override
	public void render(DisplayManager display) {
		renderer.render();
	}
	
	@Override
	public void destroy() {
		scene.destroy();
		renderer.destroy();
		texManager.destroy();
	}
}
