package com.detrivos.auto.menu;

import com.detrivos.auto.Game;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.input.Keyboard;
import com.detrivos.auto.input.Mouse;

public class PauseMenu extends Menu {

	int timer = 18;
	String[] options = {"Return To Game", "Options", "Exit To Main Menu", "Exit To Desktop"};
	int selected = 0;
	int cola = 0xFFFFFFFF;
	int colb = 0xFFFFFFFF;
	int colc = 0xFFFFFFFF;
	int cold = 0xFFFFFFFF;
	
	Mouse m = Game.m;
	
	public PauseMenu(Keyboard key) {
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
		
		if (selected < 0) selected = 3;
		if (selected > 3) selected = 0;
		
		/*if (Game.onMenuSelect) {
			if (m.getY() > 99 * 3 && m.getY() <= 113 * 3) {
				selected = 0;
			}
			if (m.getY() > 113 * 3 && m.getY() <= 125 * 3) {
				selected = 1;
			}
			if (m.getY() > 125 * 3 && m.getY() <= 139 * 3) {
				selected = 2;
			}
			if (m.getY() > 139 * 3 && m.getY() <= 153 * 3) {
				selected = 3;
			}
		}*/
		
		if (selected == 0) {
			cola = 0xFFFFE877;
			if (key.select/* || (m.getButton() == 1 && Game.onMenuSelect)*/) {
				Keyboard.changeResult(false);
			}
		} else {
			cola = 0xFFFFFFFF;
		}
		if (selected == 1) {
			colb = 0xFFFFE877;
		} else {
			colb = 0xFFFFFFFF;
		}
		if (selected == 2) {
			colc = 0xFFFFE877;
			if (key.select/* || (m.getButton() == 1 && Game.onMenuSelect)*/) {
				Keyboard.changeResult(false);
				Game.toMainMenu = true;
			}
		} else {
			colc = 0xFFFFFFFF;
		}
		if (selected == 3) {
			cold = 0xFFFFE877;
			if (key.select/* || (m.getButton() == 1 && Game.onMenuSelect)*/) {
				Game.exit = true;
			}
		} else {
			cold = 0xFFFFFFFF;
		}
	}
	
	public void render(Screen screen) {
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (Game.absWidth / 2) + 1, 370 + i * 40 + 1, 28, 0, 0xFF000000);
			if (i == 0) {
				screen.renderText(options[i], (Game.absWidth / 2), 370 + i * 40, 28, 0, cola);
			}
			if (i == 1) {
				screen.renderText(options[i], (Game.absWidth / 2), 370 + i * 40, 28, 0, colb);
			}
			if (i == 2) {
				screen.renderText(options[i], (Game.absWidth / 2), 370 + i * 40, 28, 0, colc);
			}
			if (i == 3) {
				screen.renderText(options[i], (Game.absWidth / 2), 370 + i * 40, 28, 0, cold);
			}
		}
	}
}
