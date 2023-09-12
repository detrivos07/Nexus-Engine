package com.detrivos.auto.menu;

import java.util.Random;

import com.detrivos.auto.Game;
import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.input.Keyboard;

public class RespawnMenu extends Menu {

	Random random = new Random();
	int timer = 18;
	String[] options = { "Respawn", "Main Menu" };
	int selected = 0;

	int cola = 0xFFFFFFFF;
	int colb = 0xFFFFFFFF;
	
	String message = "";
	boolean messageCreated = false;
	String[] deathMessages = { "You've died ... Again", "Not all of us can be heros", "You were slain...", "You are a dead person", 
			"Was that suicide?", "This screen indicates that you are dead", "Death, another great equalizer", "Good job"};
	
	public RespawnMenu(Keyboard key, boolean chal) {
		super(key);
		if (chal)
			options[0] = "Retry";
	}

	public void tick() {
		if (!messageCreated) {
			message = randomString();
			messageCreated = true;
		}
		
		if (timer > 0)
			timer--;
		if ((key.right || key.up) && selected < options.length && timer == 0) {
			selected++;
			timer = 10;
		}
		if ((key.left || key.down) && selected > -1 && timer == 0) {
			selected--;
			timer = 10;
		}

		if (selected < 0)
			selected = 1;
		if (selected > 1)
			selected = 0;

		if (selected == 0) {
			cola = 0xFFFFE877;
			if (key.select) {
				Game.doRespawn = true;
			}
		} else {
			cola = 0xFFFFFFFF;
		}

		if (selected == 1) {
			colb = 0xFFFFE877;
			if (key.select) {
				Game.toMainMenu = true;
			}
		} else {
			colb = 0xFFFFFFFF;
		}
	}
	
	private String randomString() {
		String message = deathMessages[random.nextInt(deathMessages.length)];
		return message;
	}

	public void render(Screen screen) {
		// Be awares of string length, plan accordingly to keep it on the background
		screen.renderText(message, (Game.absWidth / 2) + 2, (Game.absHeight / 2) - 186 + 2, 49, 2, 0xFF000000);
		screen.renderText(message, (Game.absWidth / 2), (Game.absHeight / 2) - 186, 49, 2, 0xFFFFFFFF);
		
		screen.renderText("Kills: " + Player.kills, (Game.absWidth / 2) + 1, 481 + 1, 28, 0, 0xFF000000);
		screen.renderText("Kills: " + Player.kills, (Game.absWidth / 2), 481, 28, 0, 0xFFFFFFFF);
		for (int i = 0; i < options.length; i++) {
			if (i == 0) {
				screen.renderText(options[i], (Game.absWidth / 3) + 1, 517 + 1, 28, 0, 0xFF000000);
				screen.renderText(options[i], (Game.absWidth / 3), 517, 28, 0, cola);
			}
			if (i == 1) {
				screen.renderText(options[i], (Game.absWidth / 3) * 2 + 1, 517 + 1, 28, 0, 0xFF000000);
				screen.renderText(options[i], (Game.absWidth / 3) * 2, 517, 28, 0, colb);
			}
		}
	}
}
