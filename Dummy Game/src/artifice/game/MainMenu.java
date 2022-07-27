package artifice.game;

import static artifice.engine.gui.ui.ColorComponent.rgba;
import static org.lwjgl.glfw.GLFW.glfwSetWindowShouldClose;

import artifice.engine.gui.*;
import artifice.engine.io.Cursor;
import artifice.engine.io.Keyboard;
import artifice.engine.io.Window;
import artifice.engine.render.texture.Texture;

public class MainMenu {

	GUI ui;
	
	boolean startGame = false;
	
	public MainMenu(Window window) {
		ui = new GUI(0, 0, window.getWidth(), window.getHeight());
		ui.init(window);
		
		ColorComponent bg = new ColorComponent(ui, 0, 0, ui.getW(), ui.getH());
		bg.initCols(window, ColorComponent.rgba(0, 0, 0, 255));
		ui.addComponent(bg, 0);
		
		TextureComponent main = new TextureComponent(ui, 400, 50, 777, 777);
		main.initTextures(new Texture("res/guis/mainMenu"));
		ui.addComponent(main, 1);
		
		ButtonComponent ng = new ButtonComponent(ui, 740, 340, 120, 32) {
			@Override
			public void invoke() {
				startGame = true;
			}
		};
		ng.initCols(rgba(0, 0, 0, 0), rgba(0, 80, 255, 40));
		ui.addComponent(ng, 2);
		
		ButtonComponent exit = new ButtonComponent(ui, 740, 410, 80, 32) {
			@Override
			public void invoke() {
				glfwSetWindowShouldClose(window.getWindow(), true);
			}
		};
		exit.initCols(rgba(0,0,0,0), rgba(0,80,255,40));
		ui.addComponent(exit, 0);
	}
	
	public void input(Keyboard board, Cursor cursor) {
		ui.input(board, cursor);
	}
	
	public void update() {
		ui.update();
	}
	
	public void render(Window window) {
		ui.render(window);
	}
	
	public void destroy() {
		ui.destroy();
	}
	
	public boolean start() {
		return startGame;
	}
}
