package com.detrivos.auto.menu;

import com.detrivos.auto.Game;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.input.Keyboard;

public class ExpMenu extends Menu {

	int timer = 18;
	String[] options = {"Option 1", "Option 2"};
	int selected = 0;
	int[] cols = {0xFFFFFFFF, 0xFFFFFFFF};
	
	public ExpMenu(Keyboard key) {
		super(key);
	}

	public void tick() {
		if (timer > 0) timer--;
		if (key.down && selected < options.length && timer == 0) {
			selected++;
			timer = 10;
		}
		if (key.up && selected > -1 && timer == 0) {
			selected--;
			timer = 10;
		}
		
		if (selected < 0) selected = options.length;
		if (selected > options.length) selected = 0;
		
		for (int i = 0; i < options.length; i++) {
			if (selected == i) {
				cols[i] = 0xFFFFE877;
			} else {
				cols[i] = 0xFFFFFFFF;
			}
		}
	}
	
	public void render(Screen screen) {
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (Game.absWidth / 2) + 1, 370 + i * 40 + 1, 28, 0, 0xFF000000);
		}
	}
}
