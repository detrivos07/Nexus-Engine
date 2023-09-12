package com.detrivos.auto.ui;

public class StoryUI {

	private String path;
	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;
	
	public static StoryUI controls = new StoryUI("/textures/storyPages/controls.png", 100, 175);
	public static StoryUI cryo = new StoryUI("/textures/storyPages/proCryo.png", 100, 141);
	
	public static StoryUI shotgunText = new StoryUI("/textures/ui/shotgunText.png", 100, 106);
	
	public StoryUI(String path, int width, int height) {
		this.path = path;
		HEIGHT = height;
		WIDTH = width;
		pixels = new int[width * height];
		ImportUtils.loadImage(path, pixels);
	}
}
