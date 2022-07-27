package com.detrivos.eng.render;

import static com.detrivos.eng.handle.Handler.*;

import java.nio.FloatBuffer;
import java.util.*;

import org.joml.*;
import org.lwjgl.BufferUtils;

import com.detrivos.eng.render.shader.TileShader;
import com.detrivos.game.level.tile.*;

public class TileRenderer {

	private TileShader shader;
	private Model model;
	
	private int pointer = 0;
	private FloatBuffer buffer;
	
	public TileRenderer() {
		model = new Model();
		shader = new TileShader("tileShader", model);
		buffer = BufferUtils.createFloatBuffer(TileShader.MAX * TileShader.f_COUNT);
	}
	
	public void render() {
		renderAllTiles(currentLevel.gatherData(), currentLevel.getLevelData());//DO TEXTURE ATLASING YA FOOL
	}
	
	public void renderAllTiles(List<TileData> tp, Matrix4f level) {
		shader.bind();
		
		List<Matrix4f> targets = new ArrayList<Matrix4f>();
		List<Matrix4f> tPoss = new ArrayList<Matrix4f>();
		
		MasterRenderer.activeTexPack.getMainAtlas().bind(0);
		
		for (int i = 0; i < tp.size(); i++) {
			tPoss.add(new Matrix4f().translate(new Vector3f(tp.get(i).getPos().x * 2, tp.get(i).getPos().y * 2, 0)));
			targets.add(i, new Matrix4f());
			camera.getProjection().mul(level, targets.get(i));
			targets.get(i).mul(tPoss.get(i));
		}
		
		pointer = 0;
		float[] vboData = new float[targets.size() * TileShader.f_COUNT];
		for (int i = 0; i < targets.size(); i++) {
			pointer = shader.storeData(targets.get(i), vboData, pointer);
			pointer = shader.storeData(tp.get(i).getTexUnits(), vboData, pointer);
		}
		
		shader.setSampler(0);
		shader.updateVBO(vboData, buffer);
		model.render(targets.size());
		targets.clear();
		tPoss.clear();
		
		tp.clear();
		shader.unbind();
	}
	
	public void renderTiles(List<TilePosition> tp, Matrix4f level) {
		shader.bind();
		
		List<Matrix4f> targets = new ArrayList<Matrix4f>();
		List<Matrix4f> tPoss = new ArrayList<Matrix4f>();
		int[] texValues = new int[32];
		
		if (tp.size() <= 32) for (int i = 0; i < tp.size(); i++) {
			tp.get(i).t.bind(i);
			texValues[i] = i;
		} else throw new IllegalStateException("TOO MANY TEXTURES");
		
		for (int t = 0; t < tp.size(); t++) {
			if (tp.get(t).positions != null) for (int i = 0; i < tp.get(t).positions.size(); i++) {
				tPoss.add(new Matrix4f().translate(new Vector3f(tp.get(t).positions.get(i).x * 2, tp.get(t).positions.get(i).y * 2, 0)));
				targets.add(i, new Matrix4f());
				camera.getProjection().mul(level, targets.get(i));
				targets.get(i).mul(tPoss.get(i));
			}
			pointer = 0;
			float[] vboData = new float[targets.size() * TileShader.f_COUNT];
			for (int i = 0; i < targets.size(); i++) {
				pointer = shader.storeData(targets.get(i), vboData, pointer);
			}
			shader.updateVBO(vboData, buffer);
			shader.setSampler(t);
			model.render(targets.size());
			targets.clear();
			tPoss.clear();
		}
		tp.clear();
		shader.unbind();
	}
}
