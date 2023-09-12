package com.detrivos.auto.menu;

import java.util.Random;

import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.input.Keyboard;

public class Menu {

	protected Keyboard key;
	protected final Random random = new Random();

	public Menu(Keyboard key) {
		this.key = key;
	}

	public void tick() {
	}

	public void render(Screen screen) {
	}
}
