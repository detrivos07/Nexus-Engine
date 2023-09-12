package com.detrivos.auto.menu;

import com.detrivos.auto.Game;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.input.Keyboard;
import com.detrivos.auto.input.Mouse;
import com.detrivos.auto.ui.UI;

public class MainMenu extends Menu {

	int timer = 18;
	String[] options = { "Continue Story", "Play Story Mode", "Play Challenge Mode", "Options", "Exit" };
	int selected = 1;
	int cola = 0xFFFFFFFF;
	int colb = 0xFFFFFFFF;
	int colc = 0xFFFFFFFF;
	int cold = 0xFFFFFFFF;
	int cole = 0xFFFFFFFF;

	Mouse m = Game.m;

	public MainMenu(Keyboard key) {
		super(key);
	}

	public void tick() {
		if (timer > 0)
			timer--;
		if (key.down && selected < options.length && timer == 0) {
			selected++;
			timer = 10;
		}
		if (key.up && selected > -1 && timer == 0) {
			selected--;
			timer = 10;
		}

		/*
		 * if (Game.onMenuSelect) { if (m.getY() > 99 * 3 && m.getY() <= 113 *
		 * 3) { selected = 0; } if (m.getY() > 113 * 3 && m.getY() <= 125 * 3) {
		 * selected = 1; } if (m.getY() > 125 * 3 && m.getY() <= 139 * 3) {
		 * selected = 2; } if (m.getY() > 139 * 3 && m.getY() <= 153 * 3) {
		 * selected = 3; } if (m.getY() > 153 * 3 && m.getY() <= 165 * 3) {
		 * selected = 4; } }
		 */

		if (selected < 0)
			selected = 4;
		if (selected > 4)
			selected = 0;

		if (selected == 0) {
			if (!Game.hasSave) {
				cole = 0xFFAF9E52;
			} else {
				cole = 0xFFFFE877;
			}
		} else {
			if (!Game.hasSave) {
				cole = 0xFF404040;
			} else {
				cole = 0xFFFFFFFF;
			}
		}
		if (selected == 1) {
			cola = 0xFFFFE877;
			if (key.select/* || (m.getButton() == 1 && Game.onMenuSelect) */) {
				Game.startStory = true;
			}
		} else {
			cola = 0xFFFFFFFF;
		}

		if (selected == 2) {
			colb = 0xFFFFE877;
			if (key.select/* || (m.getButton() == 1 && Game.onMenuSelect) */) {
				Game.startChallenge = true;
			}
		} else {
			colb = 0xFFFFFFFF;
		}

		if (selected == 3) {
			colc = 0xFFFFE877;
		} else {
			colc = 0xFFFFFFFF;
		}

		if (selected == 4) {
			cold = 0xFFFFE877;
			if (key.select/* || (m.getButton() == 1 && Game.onMenuSelect) */) {
				Game.exit = true;
			}
		} else {
			cold = 0xFFFFFFFF;
		}
	}

	public void render(Screen screen) {
		for (int i = 0; i < options.length; i++) {
			screen.renderText(options[i], (Game.absWidth / 2) + 1, 326 + i * 40 + 1, 28, 0, 0xFF000000);
			if (i == 0) {
				screen.renderText(options[i], (Game.absWidth / 2), 326 + i * 40, 28, 0, cole);
			}
			if (i == 1) {
				screen.renderText(options[i], (Game.absWidth / 2), 326 + i * 40, 28, 0, cola);
			}
			if (i == 2) {
				screen.renderText(options[i], (Game.absWidth / 2), 326 + i * 40, 28, 0, colb);
			}
			if (i == 3) {
				screen.renderText(options[i], (Game.absWidth / 2), 326 + i * 40, 28, 0, colc);
			}
			if (i == 4) {
				screen.renderText(options[i], (Game.absWidth / 2), 326 + i * 40, 28, 0, cold);
			}
		}
	}
}
