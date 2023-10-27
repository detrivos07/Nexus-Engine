package demo.game;

import org.joml.*;

import nexus.engine.Engine;
import nexus.engine.IProgram;
import nexus.engine.core.io.*;
import nexus.engine.core.io.camera.Camera;
import nexus.engine.core.io.camera.Camera3D;
import nexus.engine.core.render.Scene3dRenderer;
import nexus.engine.core.render.opengl.*;
import nexus.engine.core.scene.*;
import nexus.engine.utils.loaders.OBJLoader;

public class DemoGame implements IProgram {
	
	public static void main(String[] args) {
		Engine.getInstance().init(new DemoGame());
	}
	
	//Local references to singleton classes
	private Keyboard board;
	private Mouse mouse;
	private DisplayManager display;
	//*/
	
	private TextureManager texManager;//engine
	
	private Scene3dRenderer renderer;//in scene?
	private Camera3D camera;//engine
	private Scene3d scene;
	
	private final Vector3f cameraInc;
	
	private Terrain terrain;
	private GameObject plane;
	
	public DemoGame() {
		cameraInc = new Vector3f();
		scene = new Scene3d();
	}
	
	@Override
	public void init() {
		board = Keyboard.getInstance();
		mouse = Mouse.getInstance();
		display = DisplayManager.getInstance();
		camera = (Camera3D) Camera.getInstance(new Camera3D());
		
		texManager = new TextureManager();
		texManager.initFromFile("/textures/");
		
		scene.init();
		
		renderer = new Scene3dRenderer(display, scene, camera);
		renderer.init();
		
		Mesh mesh = OBJLoader.loadMesh("/models/plane.obj");
		Material mat = new Material(texManager.getTextureFromID(texManager.textures(), 0).load());
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
	public void input() {
		cameraInc.set(0, 0, 0);
		if (board.check(Keyboard.KEY_W)) cameraInc.z += -1;
		else if (board.check(Keyboard.KEY_S)) cameraInc.z += 1;
		if (board.check(Keyboard.KEY_A)) cameraInc.x += -1;
		else if (board.check(Keyboard.KEY_D)) cameraInc.x += 1;
		if (board.check(Keyboard.KEY_LSHIFT)) cameraInc.y += -1;
		else if (board.check(Keyboard.KEY_SPACE)) cameraInc.y += 1;
		//else cameraInc.y += -1.5f;
		
		if (mouse.check(Mouse.BUTTON_1)) {
			Vector2f rotv = new Vector2f(mouse.getDisplayVec());
			mouse.getDisplayVec().zero();
			camera.rotate(rotv.x * Mouse.MOUSE_SENS, rotv.y * Mouse.MOUSE_SENS, 0);
		}
	}
	
	@Override
	public void update() {
		camera.move(cameraInc.x, cameraInc.y, cameraInc.z, 0.07f);
		
		//float height = terrain.getHeight(camera.getPos()) + 1.0f;
//		if (camera.getPos().y <= height) {
//			camera.getPos().lerp(new Vector3f(camera.getPos().x, height, camera.getPos().z), 0.21f);
//			camera.setPos(camera.getPos().x, height, camera.getPos().z);
//		}
		
		
		scene.update();
	}
	
	@Override
	public void render() {
		renderer.render();
	}
	
	@Override
	public void destroy() {
		scene.destroy();
		renderer.destroy();
		texManager.destroy();
	}
}
