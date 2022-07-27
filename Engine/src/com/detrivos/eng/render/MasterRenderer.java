package com.detrivos.eng.render;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

import static com.detrivos.eng.handle.Handler.*;

import org.lwjgl.opengl.GL;

import com.detrivos.eng.render.texture.TexturePack;

public class MasterRenderer {

	private String defaultLoadPath = "/sprites/";
	private String defaultAnimLoad = "anim/";

	public static TexturePack activeTexPack;
	public static TexturePack defaultTexPack;
	public static TexturePack secondaryTexPack;

	private TileRenderer tr = new TileRenderer();

	public MasterRenderer() {
		defaultTexPack = new TexturePack()
				.setPlayerAnims(
						new Animation[] { new Animation(1, 10, defaultLoadPath + defaultAnimLoad + "playerIdleB"),
								new Animation(1, 10, defaultLoadPath + defaultAnimLoad + "playerIdleR"),
								new Animation(4, 10, defaultLoadPath + defaultAnimLoad + "playerIdleF"),
								new Animation(1, 10, defaultLoadPath + defaultAnimLoad + "playerIdleL"),
								new Animation(4, 7, defaultLoadPath + defaultAnimLoad + "playerWalkB"),
								new Animation(4, 6, defaultLoadPath + defaultAnimLoad + "playerWalkR"),
								new Animation(4, 7, defaultLoadPath + defaultAnimLoad + "playerWalkF"),
								new Animation(4, 6, defaultLoadPath + defaultAnimLoad + "playerWalkL") })
				.setMainAtlas(new TextureAtlas(defaultLoadPath + "tiles/tAtlas"));
		secondaryTexPack = new TexturePack();
		activeTexPack = secondaryTexPack.getMainAtlas() == null ? defaultTexPack : secondaryTexPack;
	}

	public static void prepareWindow() {
		GL.createCapabilities();
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
//		glfwSwapInterval(1);

		glClearColor(0.2f, 0.5f, 0.8f, 1.0f);
	}

	public void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		tr.render();
		currentLevel.handler.render();
		glfwSwapBuffers(window.getWindow());
	}

	public TileRenderer getTileRenderer() {
		return tr;
	}
}
