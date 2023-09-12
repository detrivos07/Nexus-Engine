package com.detrivos.auto.ui;

public class UI {

	private String path;
	public final int WIDTH;
	public final int HEIGHT;
	public int[] pixels;

	public static UI bg = new UI("/textures/ui/bg.png", 400, 225);
	public static UI menuSelect = new UI("/textures/menuSelect.png", 112, 80);
	public static UI pauseSelect = new UI("/textures/pauseSelect.png", 112, 67);
	public static UI respawnSelect = new UI("/textures/ui/respawnMenu.png", 60, 24);
	public static UI deathBG = new UI("/textures/ui/deathBG.png", 320, 59);

	public UI(String path, int width, int height) {
		this.path = path;
		WIDTH = width;
		HEIGHT = height;
		pixels = new int[width * height];
		ImportUtils.loadImage(path, pixels);
	}
}
