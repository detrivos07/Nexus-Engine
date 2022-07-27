package com.detrivos.eng.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class TextureAtlas {

	private static int tSize = 24;
	private int id, size;
	private String path;
	
	public TextureAtlas(String path) {
		File texFile = new File("res/" + path + ".png");
		this.path = path;
		BufferedImage image;
		try {
			if (!texFile.exists()) image = ImageIO.read(Texture.class.getResource(path + ".png"));
			else image = ImageIO.read(texFile);
			size = image.getWidth();
			
			int[] rawPixels = new int[size * size * 4];
			rawPixels = image.getRGB(0, 0, size, size, null, 0, size);
			ByteBuffer pixels = BufferUtils.createByteBuffer(size * size * 4);
			
			for (int y = 0; y < size; y++) {
				for (int x = 0; x < size; x++) {
					int pixel = rawPixels[x + y * size];
					pixels.put((byte) ((pixel >> 16) & 0xFF)); //R
					pixels.put((byte) ((pixel >> 8) & 0xFF));  //G
					pixels.put((byte) ((pixel >> 0) & 0xFF));  //B
					pixels.put((byte) ((pixel >> 24) & 0xFF)); //A
				}
			}
			
			pixels.flip();
			
			id = glGenTextures();
			glBindTexture(GL_TEXTURE_2D, id);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, size, size, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void finalize() throws Throwable {
		glDeleteTextures(id);
		super.finalize();
	}
	
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}

	public String getPath() {
		return path;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getTexSize() {
		return tSize;
	}
}
