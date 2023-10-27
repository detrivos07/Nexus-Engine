package artifice.engine.level;

import java.util.*;
import java.util.Random;

import org.joml.*;

import artifice.engine.io.Camera;
import artifice.engine.level.entity.Entity;
import artifice.engine.level.entity.Projectile;
import artifice.engine.level.tile.Tile;
import artifice.engine.level.tile.TileMap;
import artifice.engine.render.model.InstMaterial;
import artifice.engine.render.model.InstMesh;
import artifice.engine.render.texture.TextureAtlas;
import nexus.engine.core.ai.Node;
import nexus.engine.core.io.Window;
import nexus.engine.core.render.shader.Shader;

public abstract class Level {
	protected Random rand = new Random();
	
	protected int viewX, viewY;
	protected Matrix4f level;
	protected InstMesh mesh;
	int pointer = 0;
	
	protected TextureAtlas atlas;
	protected InstMaterial[] types;
	protected int scale;
	
	protected String path;
	protected int width, height;
	protected TileMap tmap, above;
	
	protected List<Entity> ents;
	protected List<Projectile> projs;
	
	protected Vector2d mouseWorldPos = new Vector2d();
	
	Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return 1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};
	
	public Level(TextureAtlas atlas, String path, int scale) {
		this.path = path;
		this.scale = scale;
		this.mesh = new InstMesh();
		this.level = new Matrix4f().translate(new Vector3f()).scale(scale);
		
		this.atlas = atlas;
		types = new InstMaterial[this.atlas.getTextureList().size()];
		for (int i = 0; i < types.length; i++) types[i] = new InstMaterial(atlas, atlas.getOffset(atlas.getTextureList().get(i)));
		
		ents = new ArrayList<Entity>();
		projs = new ArrayList<Projectile>();
		
		generate();
	}
	
	public Level(TextureAtlas atlas, int width, int height, int scale) {
		this.width = width;
		this.height = height;
		this.scale = scale;
		this.mesh = new InstMesh();
		this.level = new Matrix4f().translate(new Vector3f()).scale(scale);
		
		this.atlas = atlas;
		types = new InstMaterial[this.atlas.getTextureList().size()];
		for (int i = 0; i < types.length; i++) types[i] = new InstMaterial(atlas, atlas.getOffset(atlas.getTextureList().get(i)));
		
		ents = new ArrayList<Entity>();
		
		generate();
	}
	
	public abstract void generate();
	public abstract void update();
	
	public void renderLevel(Shader shader, Camera camera, TileMap map) {
		List<TileData> data = new ArrayList<TileData>();
		
		int posX = ((int) camera.getPos().x) / (scale * 2);
		int posY = ((int) camera.getPos().y) / (scale * 2);
		
		for (int y = 0; y < viewY; y++) {
			for (int x = 0; x < viewX; x++) {
				Tile t = map.getTile(x - posX - (viewX / 2) + 1, y + posY - (viewY / 2));
				if (t != null) data.add(new TileData(new Vector2i(x - posX - (viewX / 2) + 1, -y - posY + (viewY / 2)), t.getImat().getTexUnits()));
			}
		}
		//System.out.println(data.size());
		
		shader.bind();
		
		List<Matrix4f> targets = new ArrayList<Matrix4f>();
		List<Matrix4f> tPoss = new ArrayList<Matrix4f>();
		
		atlas.bind(0);
		
		for (int i = 0; i < data.size(); i++) {
			tPoss.add(new Matrix4f().translate(new Vector3f(data.get(i).getPos().x * 2, data.get(i).getPos().y * 2, 0)));
			targets.add(i, new Matrix4f());
			camera.getProjection().mul(level, targets.get(i));
			targets.get(i).mul(tPoss.get(i));
		}
		
		pointer = 0;
		float[] vboData = new float[targets.size() * InstMesh.f_COUNT];
		for (int i = 0; i < targets.size(); i++) {
			pointer = shader.storeData(targets.get(i), vboData, pointer);
			pointer = shader.storeData(data.get(i).getTexUnits(), vboData, pointer);
		}
		
		shader.setUniform("sampler", 0);
		mesh.render(targets.size(), vboData);
		
		targets.clear();
		tPoss.clear();
		data.clear();
		shader.unbind();
	}
	
	public void renderEnts(Shader shader, Camera camera) {
		for (int i = 0; i < projs.size(); i++) projs.get(i).render(shader, camera);
		for (int i = 0; i < ents.size(); i++) ents.get(i).render(shader, camera);
	}
	
	public void remove() {
		for (int i = 0; i < ents.size(); i++) if (ents.get(i).isRemoved()) {
			ents.get(i).destroy();
			ents.remove(i);
		}
		for (int i = 0; i < projs.size(); i++) if (projs.get(i).isRemoved()) {
			projs.get(i).destroy();
			projs.remove(i);
		}
	}
	
	public void destroy() {
		for (Entity e : ents) e.destroy(); 
		for (Projectile p : projs) p.destroy();
		mesh.destroy();
		atlas.destroy();
	}
	
	public void calculateView(Window window) {
		viewX = window.getWidth() / (scale * 2) + 3;
		viewY = window.getHeight() / (scale * 2) + 3;
	}
	
	public TileMap getMap() {
		return tmap;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getScale() {
		return scale;
	}
	
	public Vector2d getMouseWorldPos() {
		return mouseWorldPos;
	}
	
	public Matrix4f getLevelData() {
		return level;
	}
	
	public InstMesh getMesh() {
		return mesh;
	}
	
	public List<Entity> getEnts() {
		return ents;
	}
	
	public List<Projectile> getProjs() {
		return projs;
	}
	
	public void setMouseWorldPos(double x, double y, Camera camera, Window window) {
		float fx = (float) ((2.0f * x) / window.getWidth() - 1.0f);
		float fy = (float) (1.0f - (2.0f * y) / window.getHeight());
		Vector4f clip = new Vector4f(fx, fy, -1f, 1f);
		Matrix4f invert = camera.getProjection().invert();
		Vector4f eye = invert.transform(clip);
		mouseWorldPos.set((eye.x / scale) / 2 + 0.5, -(eye.y / scale) / 2 + 0.5);
	}
}
