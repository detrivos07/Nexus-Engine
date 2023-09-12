package com.detrivos.auto.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.detrivos.auto.Game;

public class Keyboard implements KeyListener {
	
	private boolean pressed = false, expPressed = false;
	private static boolean unpauseness = false;
	private static boolean result = false;
	private boolean[] keys = new boolean[120];
	//movement types
	public boolean up, down, left, right, run;
	//GUI Types
	public boolean debug, escape, select, exp;
	
	public boolean key1, key2, key3, key4;
	
	public void tick() {
		key1 = keys[KeyEvent.VK_1];
		key2 = keys[KeyEvent.VK_2];
		key3 = keys[KeyEvent.VK_3];
		key4 = keys[KeyEvent.VK_4];
		exp = keys[KeyEvent.VK_E];
		
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		run = keys[KeyEvent.VK_CONTROL];
		
		debug = keys[KeyEvent.VK_F3];
		escape = keys[KeyEvent.VK_ESCAPE];
		select = keys[KeyEvent.VK_ENTER] || keys[KeyEvent.VK_SPACE];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		
	}

	public void keyTyped(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		
	}
	
	public boolean isPaused(boolean b) {
		if (b) {
			pressed = true;
		}
		if (!b && pressed && !Game.onMainMenu && !unpauseness) {
			result ^= true;
			pressed = false;
		}
		if (!Game.focus && !Game.onMainMenu) result = true;
		return result;
	}
	
	public void toggleKey(boolean key, boolean edit) {//TODO :: Fix all pausing and how keys are handled
		if (key) {
			expPressed = true;
		}
		if (!key && expPressed && !Game.onMainMenu && !unpauseness) {
			edit ^= true;
			expPressed = false;
		}
		if (!Game.focus && !Game.onMainMenu) edit = true;
	}
	
	public static void changeResult(boolean pause) {
		result = pause;
	}
	
	public static void changeUnpauseness(boolean b) {
		unpauseness = b;
	}
}
