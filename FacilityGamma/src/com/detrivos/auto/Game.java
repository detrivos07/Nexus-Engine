package com.detrivos.auto;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import com.detrivos.auto.entity.assets.Player;
import com.detrivos.auto.entity.utils.BulletBar;
import com.detrivos.auto.entity.utils.BulletBar.Bullet;
import com.detrivos.auto.entity.utils.PlayerStatsBar;
import com.detrivos.auto.entity.utils.PlayerStatsBar.Type;
import com.detrivos.auto.experience.Experience;
import com.detrivos.auto.graphics.Screen;
import com.detrivos.auto.graphics.VignetteHelper;
import com.detrivos.auto.input.Keyboard;
import com.detrivos.auto.input.Mouse;
import com.detrivos.auto.level.Level;
import com.detrivos.auto.level.assets.ChallengeLevel;
import com.detrivos.auto.level.assets.CryoRoom;
import com.detrivos.auto.level.assets.LeecherTunnel1;
import com.detrivos.auto.level.assets.MenuLevel;
import com.detrivos.auto.level.assets.TurretHall;
import com.detrivos.auto.level.assets.TurretHall2;
import com.detrivos.auto.level.assets.TurretHall3;
import com.detrivos.auto.menu.ExpMenu;
import com.detrivos.auto.menu.MainMenu;
import com.detrivos.auto.menu.Menu;
import com.detrivos.auto.menu.PauseMenu;
import com.detrivos.auto.menu.RespawnMenu;
import com.detrivos.auto.modes.Challenge;
import com.detrivos.auto.ui.StoryUI;
import com.detrivos.auto.ui.UI;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static int width = 400;
	public static int height = width / 16 * 9;
	public static int scale = 3;
	public static int res = 1;
	public static int absWidth = (width * scale) * res;
	public static int absHeight = (height * scale) * res;

	public static boolean hasSave = false;

	public static boolean onMainMenu = true;
	public static boolean onExpMenu = false;
	public static boolean onPauseMenu = false;
	
	public static boolean startStory = false;
	public static boolean startChallenge = false;
	public static boolean onchal = false;
	public static boolean paused = false;
	public static boolean exit = false;
	public static boolean toMainMenu = false;

	public static boolean onMenuSelect = false;

	public static boolean doRespawn = false;
	private boolean hideGUI = false;

	private Screen screen;
	private Keyboard key = new Keyboard();
	public static Mouse m;
	private Player player;
	private Player storyPlayer = new Player(16 * 4 - 8, 16 * 3 - 9, key, true);
	private PlayerStatsBar health;
	private PlayerStatsBar stamina;
	private static StoryUI ui;
	public static Challenge chal;

	private BulletBar pistol;
	private BulletBar scatter;
	private BulletBar machine;
	private BulletBar rockets;
	//private BulletBar lm;

	private UI bg;

	private Level level;
	private Level menuLevel = new MenuLevel("/levels/menuLevel.png");
	private Level cryo = new CryoRoom("/levels/cryo.png");
	private Level turHall1 = new TurretHall("/levels/turretHall.png");
	private Level turHall2 = new TurretHall2("/levels/turretHall2.png");
	private Level turHall3 = new TurretHall3("/levels/turretHall3.png");
	private Level leech1 = new LeecherTunnel1("/levels/leecherTun1.png");

	private Font font = new Font("Terminal", 2, 20);
	private Font bulFont = new Font("Terminal", 1, 15);
	private FontMetrics fm;
	private String thought;

	private int coolDown = 0;

	private Menu menu;
	private Menu expMenu = new ExpMenu(key);
	private Menu pauseMenu = new PauseMenu(key);
	private UI menuSelect = UI.menuSelect;
	private UI pauseSelect = UI.pauseSelect;
	private boolean rMShown = false;

	private Thread thread;
	private JFrame frame;
	private String title = "Facility Gamma";
	private boolean running = true;
	public static boolean focus = false;

	public static boolean toCryo = false;
	public static boolean toTurHall = false;
	public static boolean turhall2 = false;
	public static boolean toturhall3 = false;
	public static boolean toleech1 = false;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game() {
		Dimension d = new Dimension((width * scale) * res, (height * scale) * res);
		setPreferredSize(d);

		frame = new JFrame();
		screen = new Screen(width, height);
		m = new Mouse();

		menu = new MainMenu(key);
		expMenu = new ExpMenu(key);

		level = menuLevel;

		addKeyListener(key);
		addMouseListener(m);
		addMouseMotionListener(m);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Practice");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void pause(boolean bool) {
		paused = bool;
	}

	private void init() {
		frame.setResizable(false);
		frame.setTitle(title);
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/textures/iconImage.png")));
		frame.setVisible(true);
		frame.toFront();
		frame.setAutoRequestFocus(true);
		frame.requestFocus();
	}

	public void run() {
		init();
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				tick();
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + frames + " FPS");
				frames = 0;
			}
		}
		stop();
	}

	@SuppressWarnings("static-access")
	private void tick() {
		coolDown--;
		if (coolDown < 0) coolDown = 0;
		focus = frame.isFocused();
		key.tick();

		if (exit) {
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
			stop();
		}

		if (startStory) {
			level = cryo;
			player = storyPlayer;
			level.add(player);
			player.changeMode(true);
			health = new PlayerStatsBar(player, Type.HEALTH, 0xFFFF0000, 9, 90, 373, 131);
			stamina = new PlayerStatsBar(player, Type.STAMINA, 0xFFFFFF00, 9, 90, 387, 131);

			pistol = new BulletBar(player, Bullet.PISTOL, 5, 21, 29, 136);
			scatter = new BulletBar(player, Bullet.SCATTER, 5, 21, 29, 168);
			machine = new BulletBar(player, Bullet.MACHINE, 5, 21, 29, 200);
			rockets = new BulletBar(player, Bullet.ROCKET, 5, 21, 64, 168);

			bg = UI.bg;
			menu = null;
			startStory = false;
			onMainMenu = false;
		}

		if (startChallenge) {
			chal = new Challenge();
			chal.reset();
			level = new ChallengeLevel("/levels/challengeSmall.png");
			player = new Player(16 * 6 - 8, 16 * 6 - 8, key, false);
			level.add(player);
			player.changeMode(false);
			health = new PlayerStatsBar(player, Type.HEALTH, 0xFFFF0000, 9, 90, 373, 131);
			stamina = new PlayerStatsBar(player, Type.STAMINA, 0xFFFFFF00, 9, 90, 387, 131);

			pistol = new BulletBar(player, Bullet.PISTOL, 5, 21, 29, 136);
			scatter = new BulletBar(player, Bullet.SCATTER, 5, 21, 29, 168);
			machine = new BulletBar(player, Bullet.MACHINE, 5, 21, 29, 200);
			rockets = new BulletBar(player, Bullet.ROCKET, 5, 21, 64, 168);

			bg = UI.bg;
			menu = null;
			startChallenge = false;
			onMainMenu = false;
			onchal = true;
		}

		if (onMainMenu) {
			onchal = false;
			player.kills = 0;
		}
		
		if (key.isPaused(key.escape)) paused = true;
		else paused = false;
		
		if (!onMainMenu) {
			if (!onchal) {
				if (player.dead) {// For respawn
					if (!rMShown) {
						showRespawnMenu(false);
						rMShown = true;
					}
					if (doRespawn) {
						respawn(true);
						doRespawn = false;
					}
				} else {
					rMShown = false;
				}
			} else {
				if (player.dead) {// For respawn
					Experience.resetChalTables();
					if (!rMShown) {
						showRespawnMenu(true);
						rMShown = true;
					}
					if (doRespawn) {
						respawn(false);
						doRespawn = false;
					}
				} else {
					rMShown = false;
				}
			}
			

			if (player != null) {
				if (player.thought() != null && player.hasControl) {
					thought = player.thought();
				}
			}

			if (toCryo) {
				toCryo();
			}
			if (toTurHall) {
				toTurHall();
			}
			if (turhall2) {
				toturhall2();
			}
			if (toturhall3) {
				toturhall3();
			}
			if (toleech1) {
				toleech1();
			}
		}

		// if (onMainMenu || paused) mousePos();

		if (!paused) {
			level.tick();
			if (onchal)
				chal.tick();
			if (!onMainMenu) {
				health.tick();
				stamina.tick();

				pistol.tick();
				scatter.tick();
				machine.tick();
				rockets.tick();
			}
		}
		handleMenus();
	}
	
	private void handleMenus() {
		if (menu != null && coolDown == 0) menu.tick();
		if (!onMainMenu && !player.dead && !paused && !onPauseMenu && !onExpMenu) menu = null;
		
		if (paused) {
			if (onExpMenu) {
				menu = expMenu;
			} else if (!player.dead && !onExpMenu) {
				menu = pauseMenu;
			}
		}
		
		if (toMainMenu) {
			coolDown = 30;
			level.remove(player);
			level.removeAll();
			level = menuLevel;
			player = null;
			menu = new MainMenu(key);
			onMainMenu = true;
			startChallenge = false;
			Keyboard.changeResult(false);
			
			toMainMenu = false;
		}
		
		if (player.dead) Keyboard.changeUnpauseness(true);
		else Keyboard.changeUnpauseness(false);
		
	}

	/** Respawns 'Player' */
	private void respawn(boolean story) {
		if (story) {
			if (level instanceof TurretHall)
				player.changePlayerLevel(16 * 2, 16 * 4);
			if (level instanceof TurretHall2)
				player.changePlayerLevel(16 * 2, 16 * 16);
			if (level instanceof TurretHall3)
				player.changePlayerLevel(16 * 2, 16 * 7);
			resetPlayer(true);
		} else {
			level.remove(player);
			level.removeAll();
			level = null;
			player = null;
			resetPlayer(false);
		}
		hideRespawnMenu();
	}
	
	/** Resets the player after respawning */
	@SuppressWarnings("static-access")
	private void resetPlayer(boolean story) {
		if (!story) {
			chal = new Challenge();
			chal.reset();
			level = new ChallengeLevel("/levels/challengeSmall.png");
			player = new Player(16 * 6 - 8, 16 * 6 - 8, key, false);
			player.kills = 0;
			level.add(player);
			player.changeMode(false);
			health = new PlayerStatsBar(player, Type.HEALTH, 0xFFFF0000, 9, 90, 373, 131);
			stamina = new PlayerStatsBar(player, Type.STAMINA, 0xFFFFFF00, 9, 90, 387, 131);

			pistol = new BulletBar(player, Bullet.PISTOL, 5, 21, 29, 136);
			scatter = new BulletBar(player, Bullet.SCATTER, 5, 21, 29, 168);
			machine = new BulletBar(player, Bullet.MACHINE, 5, 21, 29, 200);
			rockets = new BulletBar(player, Bullet.ROCKET, 5, 21, 64, 168);

			bg = UI.bg;
			menu = null;
		} else {
			player.changePlayerHealth(100);
			health = new PlayerStatsBar(player, Type.HEALTH, 0xFFFF0000, 9, 90, 373, 131);
			stamina = new PlayerStatsBar(player, Type.STAMINA, 0xFFFFFF00, 9, 90, 387, 131);

			pistol = new BulletBar(player, Bullet.PISTOL, 5, 21, 29, 136);
			scatter = new BulletBar(player, Bullet.SCATTER, 5, 21, 29, 168);
			machine = new BulletBar(player, Bullet.MACHINE, 5, 21, 29, 200);
			rockets = new BulletBar(player, Bullet.ROCKET, 5, 21, 64, 168);

			bg = UI.bg;
			menu = null;
		}
	}

	/** Shows the respawn menu */
	private void showRespawnMenu(boolean chal) {
		Keyboard.changeResult(true);
		menu = new RespawnMenu(key, chal);
	}

	/** Hides the respawn menu */
	private void hideRespawnMenu() {
		Keyboard.changeResult(false);
		menu = null;
	}

	@SuppressWarnings({ "unused", "static-access" })
	private void mousePos() {
		if (m.getX() > (144 * 3) && m.getX() < (256 * 3) && m.getY() > 99 * 3 && m.getY() <= 165 * 3) {
			onMenuSelect = true;
		} else {
			onMenuSelect = false;
		}
	}

	/** Sends 'Player' to Cryo Chamber room */
	private void toCryo() {
		player.changePlayerLevel(16 * 21, 16 * 4 + 8);
		level.remove(player);
		level = cryo;
		level.add(player);
		toCryo = false;
	}

	/** Sends 'Player' to first turret hall */
	private void toTurHall() {
		if (level instanceof CryoRoom)
			player.changePlayerLevel(16 * 1, 16 * 4);
		else
			player.changePlayerLevel(16 * 51, 16 * 4);
		level.remove(player);
		level = turHall1;
		level.add(player);
		toTurHall = false;
	}

	/** Sends 'Player' to second turret hall */
	private void toturhall2() {
		if (level instanceof TurretHall)
			player.changePlayerLevel(16 * 1, 16 * 16);
		else
			player.changePlayerLevel(16 * 31, 16 * 7);
		level.remove(player);
		level = turHall2;
		level.add(player);
		turhall2 = false;
	}

	/** Sends 'Player' to third turret hall */
	private void toturhall3() {
		if (level instanceof TurretHall2)
			player.changePlayerLevel(16 * 1, 16 * 7);
		else
			player.changePlayerLevel(16 * 27, 16 * 11);
		level.remove(player);
		level = turHall3;
		level.add(player);
		toturhall3 = false;
	}

	private void toleech1() {
		player.changePlayerLevel(16 * 2, 16 * 4);
		level.remove(player);
		level = leech1;
		level.add(player);
		toleech1 = false;
	}

	/** Sets the UI */
	public static void setUI(StoryUI ui) {
		if (ui == null)
			Game.ui = null;
		else
			Game.ui = ui;
	}

	@SuppressWarnings("static-access")
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		screen.graphics(g);
		screen.clear();
		
		if (!onMainMenu) {
			double xScroll = player.getX() - screen.width / 2 + 8;
			double yScroll = player.getY() - screen.height / 2 + 8;
			level.render((int) xScroll, (int) yScroll, screen);
			
			if (!hideGUI) {
				screen.renderUI(0, 0, bg);
				health.render(screen);
				stamina.render(screen);
				pistol.render(screen);
				scatter.render(screen);
				machine.render(screen);
				rockets.render(screen);
			}
		} else {
			level.render(0, 8, screen);
			screen.renderUI((width / 2) - (menuSelect.WIDTH / 2), 93, menuSelect);
		}

		if (paused && !player.dead) {
			screen.renderUI((width / 2) - (menuSelect.WIDTH / 2), 93 + 14, pauseSelect);
		}
		if (player.dead && !onMainMenu) {
			screen.renderUI((width / 2) - (UI.deathBG.WIDTH / 2), 44 - (UI.deathBG.HEIGHT / 2), UI.deathBG);
			screen.renderUI((width / 3) - (UI.respawnSelect.WIDTH / 2) + 1, 170 - (UI.respawnSelect.HEIGHT / 2), UI.respawnSelect);
			screen.renderUI(((width / 3) * 2) - (UI.respawnSelect.WIDTH / 2), 170 - (UI.respawnSelect.HEIGHT / 2), UI.respawnSelect);
			if (onchal) screen.renderUI((width / 2) - (UI.respawnSelect.WIDTH / 2), 158 - (UI.respawnSelect.HEIGHT / 2), UI.respawnSelect);
		}

		if (ui != null)
			screen.renderStoryUI((width / 2) - (ui.WIDTH / 2), (height / 2) - (ui.HEIGHT / 2), ui, false);

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		if (menu != null) {
			menu.render(screen);
			hideGUI = true;
			if (onMainMenu)
				g.drawImage(VignetteHelper.titleBI, (absWidth / 2) - 279, 50, 279 * 2, 86 * 2, null);
			if (paused && !player.dead) 
				g.drawImage(VignetteHelper.pausedBI, (absWidth / 2) - VignetteHelper.titleBI.getWidth(), 50, VignetteHelper.titleBI.getWidth() * 2, (int) (VignetteHelper.titleBI.getHeight() * 1.5), null);
		} else {
			hideGUI = false;
		}

		g.setColor(Color.WHITE);
		g.setFont(font);
		fm = g.getFontMetrics(font);

		if (thought != null) {
			g.setColor(Color.WHITE);
			g.setFont(font);
			fm = g.getFontMetrics(font);
			int sl = fm.stringWidth(thought);
			g.drawString(thought, (absWidth / 2) - (sl / 2), absHeight - 15);
		}

		if (onchal && !hideGUI) {
			g.setColor(Color.WHITE);
			g.setFont(new Font("Terminal", 1, 30));
			g.drawString("" + player.kills, 20, 40);
			g.setColor(Color.GREEN);
			g.drawString("" + player.exp.expAmount, 20, 80);
		}

		if (!onMainMenu && !hideGUI) {
			g.setColor(Color.WHITE);
			g.setFont(bulFont);
			fm = g.getFontMetrics(bulFont);
			int sw = fm.stringWidth("" + player.pistolBullets);
			int sws = fm.stringWidth("" + player.scatterBullets);
			int swm = fm.stringWidth("" + player.machineBullets);
			int swr = fm.stringWidth("" + player.rockets);
			g.drawString("" + player.pistolBullets, 95 - (sw / 2), (absHeight - (25 * 3)) - 198);
			g.drawString("" + player.scatterBullets, 95 - (sws / 2), (absHeight - (25 * 3)) - 102);
			g.drawString("" + player.machineBullets, 95 - (swm / 2), (absHeight - (25 * 3)) - 6);
			g.drawString("" + player.rockets, 200 - (swr / 2), (absHeight - (25 * 3)) - 102);
		}

		g.dispose();
		bs.show();
	}
	
	private void renderMenus() {
		
	}
}
