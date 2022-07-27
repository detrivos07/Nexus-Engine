package com.detrivos.eng.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

import com.detrivos.eng.handle.Handler;

public class Texture {

	private static int nID = 0;
	private int id, iID;
	private int width, height;
	private String path;
	private int[] rawPix;
	
	public Texture(int[] raw, int deg) {
		this.iID = nID;
		nID++;
		width = (int) Math.sqrt(raw.length);
		height = width;
		ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
		
		raw = rotate(raw, width, deg);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int pixel = raw[x * width + y];
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
		
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
	}
	
	public Texture(String path) {
		File texFile = new File("res/" + path + ".png");
		BufferedImage image;
		this.path = path;
		try {
			if (!texFile.exists()) image = ImageIO.read(Texture.class.getResource(path + ".png"));
			else image = ImageIO.read(texFile);
			width = image.getWidth();
			height = image.getHeight();
			
			int[] rawPixels = rawPix = new int[width * height * 4];
			rawPixels = rawPix = image.getRGB(0, 0, width, height, null, 0, width);
			ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					int pixel = rawPixels[x * width + y];
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
			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void finalize() throws Throwable {
		if (!Handler.running) glDeleteTextures(id);
		super.finalize();
	}
	
	public void bind(int sampler) {
		if (sampler >= 0 && sampler <= 31) {
			glActiveTexture(GL_TEXTURE0 + sampler);
			glBindTexture(GL_TEXTURE_2D, id);
		}
	}
	
	private int[] rotate(int[] pixels, int w, int deg) {
		int[] result = new int[pixels.length];
		
		switch (deg) { 
		case 270:
			int[] ninety = new int[pixels.length];
			int[] temp = new int[pixels.length];
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					ninety[x + (y * w)] = pixels[(x * w) + y];
				}
			}
			
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					temp[x + (y * w)] = ninety[((w * (y + 1)) - 1) - x];
				}
			}
			
			for (int i = 0; i < pixels.length; i++) {
				result[i] = temp[(pixels.length - 1) - i];
			}
			break;
			
		case 180:
			for (int i = 0; i < pixels.length; i++) {
				result[i] = pixels[(pixels.length - 1) - i];
			}
			break;
			
		case 90:
			int[] ninety1 = new int[pixels.length];
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					ninety1[x + (y * w)] = pixels[(x * w) + y];
				}
			}
			
			for (int y = 0; y < w; y++) {
				for (int x = 0; x < w; x++) {
					result[x + (y * w)] = ninety1[((w * (y + 1)) - 1) - x];
				}
			}
			break;
		default:
			throw new IllegalStateException("Attempting to rotate by " + deg + " degrees.");
		}
		return result;
	}
	
	public String getPath() {
		return path;
	}
	
	public int[] getRaw() {
		return rawPix;
	}
	
	public int getID() {
		return iID;
	}
}
