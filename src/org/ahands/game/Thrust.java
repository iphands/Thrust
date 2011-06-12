package org.ahands.game;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class Thrust {

	/** Game title */
	public static final String GAME_TITLE = "Thrust";

	/** Desired frame time */
	private static final int FRAMERATE = 65;

	/** Exit the game */
	private static boolean finished;

	/** Angle of rotating square */
	private static float angle;

	private static float xSpeed;
	private static float ySpeed;

	private static float x;
	private static float y;

	private final static int w = 1600;
	private final static int h = 900;

	/**
	 * Application init
	 * 
	 * @param args
	 *            Commandline args
	 */
	public static void main(String[] args) {
		boolean fullscreen = (args.length == 1 && args[0].equals("-fullscreen"));

		try {
			init(fullscreen);

			x = Display.getDisplayMode().getWidth() / 2;
			y = Display.getDisplayMode().getHeight() / 2;

			run();

		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(GAME_TITLE, "An error occured and the game will exit.");
		} finally {
			cleanup();
		}
		System.exit(0);
	}

	/**
	 * Initialise the game
	 * 
	 * @throws Exception
	 *             if init fails
	 */
	private static void init(boolean fullscreen) throws Exception {
		Display.setTitle(GAME_TITLE);
		Display.setFullscreen(false);
		Display.setDisplayMode(new DisplayMode(w, h));
		Display.setVSyncEnabled(false);
		PixelFormat pf = new PixelFormat(8, 16, 0, 4);
		Display.create(pf);
		GL11.glEnable(GL11.GL_POINT_SMOOTH);
		// GL11.glEnable(GL11.GL_BLEND);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		lastFPS = getTime(); // set lastFPS to current Time
	}

	/**
	 * Runs the game (the "main loop")
	 */
	private static void run() {

		while (!finished) {
			Display.update();
			if (Display.isCloseRequested()) {
				finished = true;
			} else if (Display.isActive()) {
				logic();
				render();
				// Display.sync(FRAMERATE);
			} else {
				// The window is not in the foreground, so we can allow other
				// stuff
				// to run and
				// infrequently update
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				logic();

				// Only bother rendering if the window is visible or dirty
				if (Display.isVisible() || Display.isDirty()) {
					render();
				}
			}
		}
	}

	final static int wingHeight = -8;
	final static int wingReach = 20;

	private static void render() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, Display.getDisplayMode().getWidth(), 0, Display.getDisplayMode().getHeight(), -1, 1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		// clear the screen
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);

		// center square according to screen size
		GL11.glPushMatrix();
		if (pewPews.hasPews()) {
			GL11.glColor3d(0x00, 0xff, 0x00);
			GL11.glBegin(GL11.GL_POINTS);
			for (final Pew pew : pewPews.getPews()) {
				GL11.glVertex2f(pew.getX(), pew.getY());
			}
			GL11.glEnd();
			GL11.glColor3d(0xff, 0xff, 0xff);
		}

		// long start = Calendar.getInstance().getTimeInMillis();
		if (exhausts.hasPews()) {
			final List<Pew> pewList = exhausts.getPews();
			final long count = pewList.size();
			int i = 1;
			GL11.glBegin(GL11.GL_POINTS);
			for (final Pew pew : pewList) {
				final float color = (float) (count / (count + (i * 2.1)));
				GL11.glColor3d(1, .9 - color, 0);
				// GL11.glColor3d(1, Math.random(), 0);

				GL11.glVertex2f(pew.getX(), pew.getY());
				i++;
			}
			GL11.glEnd();
			GL11.glColor3d(0xff, 0xff, 0xff);
		}
		// long end = Calendar.getInstance().getTimeInMillis();
		// final long wait = end - start;
		// if (wait > longestWait) {
		// System.out.println(wait);
		// longestWait = wait;
		// }

		GL11.glTranslatef(x, y, 0.0f);
		GL11.glRotatef(angle, 0, 0, 1.0f);

		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2i(0, wingHeight);
		GL11.glVertex2i(0, -20);
		GL11.glVertex2i(speed, -20);
		GL11.glEnd();

		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2i(0, wingHeight);
		GL11.glVertex2i(0, -20);
		GL11.glVertex2i(-speed, -20);
		GL11.glEnd();

		// GL11.glBegin(GL11.GL_TRIANGLES);
		// GL11.glVertex2i(0, 15);
		// GL11.glVertex2i(-10, -20);
		// GL11.glVertex2i(10, -20);
		// GL11.glEnd();
		//
		// GL11.glColor3d(0, 0, 0);
		// GL11.glBegin(GL11.GL_TRIANGLES);
		// GL11.glVertex2i(0, 8);
		// GL11.glVertex2i(-8, -18);
		// GL11.glVertex2i(8, -18);
		// GL11.glEnd();

		GL11.glColor3d(.8, .8, .9);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2i(0, 8);
		GL11.glVertex2i(-6, -10);
		GL11.glVertex2i(6, -10);
		GL11.glEnd();

		GL11.glColor3d(0, 0, 0);
		GL11.glBegin(GL11.GL_TRIANGLES);
		GL11.glVertex2i(0, 5);
		GL11.glVertex2i(-3, -9);
		GL11.glVertex2i(3, -9);
		GL11.glEnd();

		GL11.glColor3d(1, 1, 1);

		if (thrusting) {
			// GL11.glColor3d(.2, .8, 1, 0.5);
			GL11.glColor3d(1, 1, 0);
			GL11.glBegin(GL11.GL_TRIANGLES);
			if (boosting) {
				GL11.glVertex2i(0, boostTri);
			} else {
				GL11.glVertex2i(0, thrustTri);
			}
			GL11.glVertex2i(-4, -10);
			GL11.glVertex2i(4, -10);
			GL11.glEnd();

			//
			GL11.glColor3d(.3, .7, 1);
			GL11.glBegin(GL11.GL_TRIANGLES);
			if (boosting) {
				GL11.glVertex2i(0, boostTri + 5);
			} else {
				GL11.glVertex2i(0, thrustTri + 5);
			}
			GL11.glVertex2i(-2, -10);
			GL11.glVertex2i(2, -10);
			GL11.glEnd();
			GL11.glColor3d(0xff, 0xff, 0xff);
		}
		GL11.glPopMatrix();
	}

	/**
	 * Do any game-specific cleanup
	 */
	private static void cleanup() {
		// Close the window
		Display.destroy();
	}

	final static float speedTick = .02f;
	final static float boostTick = 2f;
	final static float bounceDecay = 1f;
	final static int maxPews = 11;
	static boolean thrusting = false;
	static boolean boosting = false;
	static int speed = 0;
	static int thrustTri = -10;
	static int boostTri = -35;
	static long exhaustColor = 0;
	final static PewPews pewPews = new PewPews();

	final static PewPews exhausts = new PewPews();
	final static PewPews exhausts2 = new PewPews();
	final static PewPews exhausts3 = new PewPews();
	final static PewPews exhausts4 = new PewPews();
	static int ticker = 0;

	static long lastShot = Calendar.getInstance().getTimeInMillis();
	final static float angleSpeed = 3f;
	static List<Pew> pewList = new ArrayList<Pew>(500000);
	final static int edgeFudge = 0;

	final static long s = Calendar.getInstance().getTimeInMillis();
	static int t = 0;
	final static int flSize = 1;

	/**
	 * Do all calculations, handle input, etc.
	 */
	private static void logic() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_X)) {
			angle -= angleSpeed % 360;
		} else if (true) {
			angle += angleSpeed % 360;
		}

		if (angle > 360) {
			angle -= 360;
		} else if (angle < 0) {
			angle += 360;
		}

		// if (Keyboard.isKeyDown(Keyboard.KEY_UP)) {
		if (true) {
			thrusting = true;
			ySpeed -= (speedTick * -Math.cos(Math.toRadians(angle))) / 4;
			xSpeed -= (speedTick * Math.sin(Math.toRadians(angle))) / 4;
			// if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			if ((true)) {
				boosting = true;
				boostTri = (int) (Math.random() * 20) - 55;
				ySpeed -= ((speedTick * boostTick) * -Math.cos(Math.toRadians(angle))) / 2;
				xSpeed -= ((speedTick * boostTick) * Math.sin(Math.toRadians(angle))) / 2;
			} else {
				thrustTri = (int) (Math.random() * 10) - 35;
				boosting = false;
			}
		} else {
			thrusting = false;
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
				xSpeed /= 1.05f;
				ySpeed /= 1.05f;
				speed = (int) (wingReach - ((Math.abs(xSpeed) + Math.abs(ySpeed)) * 2));
				// if (Math.abs(xSpeed) + Math.abs(ySpeed) <= .1) {
				// xSpeed = 0;
				// ySpeed = 0;
				// }
			} else {
				speed = 0;
			}
		}

		// long start = Calendar.getInstance().getTimeInMillis();
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE) && pewPews.pewPewSize() < maxPews) {
			if (Calendar.getInstance().getTimeInMillis() - lastShot > 100) {
				pewPews.addPew(new Pew(x, y, angle, xSpeed, ySpeed, false, 11, 0, 8));
				lastShot = Calendar.getInstance().getTimeInMillis();
			}
		}

		for (Pew pew : pewPews.getPews()) {
			pew.update();
			if (pew.getX() >= w || pew.getX() <= 0 || pew.getY() >= h || pew.getY() <= 0) {
				pewPews.removePew(pew);
			}
		}

		if (thrusting) {
			exhaustColor++;
			Pew pew;
			if (boosting) {
				for (int i = 0; i < MAX_BOOST_TRIS; i++) {
					pew = new Pew(x, y, angle, xSpeed, ySpeed, true, 16, 16, 1);
					exhausts.addPew(pew);
				}
			} else {
				for (int i = 0; i < 100; i++) {
					pew = new Pew(x, y, angle, xSpeed, ySpeed, true, 6, 6, 1);
					exhausts.addPew(pew);
				}
			}
		}

		long start = Calendar.getInstance().getTimeInMillis();
		final List<PewSorter> pewSorters = new ArrayList<PewSorter>(flSize);
		final List<Thread> threads = new ArrayList<Thread>(flSize);
		final List<Pew> tmpLst = exhausts.getPews();
		for (int i = 0; i <= flSize; i++) {
			final PewSorter pewSorter = new PewSorter(tmpLst, i, flSize);
			final Thread thread = new Thread(pewSorter);
			thread.start();
			threads.add(thread);
			pewSorters.add(pewSorter);
		}

		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
			}
		}

		pewList = new ArrayList<Pew>(500000);
		for (int j = 0; j <= flSize; j++) {
			pewList.addAll(pewSorters.get(j).getOnScreenPewList());
		}
		exhausts.setPews(pewList);
		long end = Calendar.getInstance().getTimeInMillis();
		final long wait = end - start;
		// if (wait > longestWait) {
		System.out.println(wait);
		longestWait = wait;
		// }

		if (Keyboard.isKeyDown(Keyboard.KEY_I)) {
			System.out.println(exhausts.pewPewSize());
		}

		if (Math.abs(xSpeed) + Math.abs(ySpeed) <= 0) {
			speed = 0;
		}

		y += ySpeed;
		x += xSpeed;

		if (y <= 0) {
			y = 0.1f;
			ySpeed = (float) ((ySpeed * -1f) - bounceDecay + (Math.random() - .5));
			if (ySpeed < 0) {
				ySpeed = .01f;
			}
		} else if (y >= h) {
			y = h - 1f;
			ySpeed = (float) ((ySpeed * -1f) + bounceDecay + (Math.random() - .5));
			if (ySpeed > 0) {
				ySpeed = -.01f;
			}
		}

		if (x <= 0) {
			x = 0.1f;
			xSpeed = (float) ((xSpeed * -1f) - bounceDecay + (Math.random() - .5));
			if (xSpeed < 0) {
				xSpeed = .01f;
			}
		} else if (x >= w) {
			x = w - .1f;
			xSpeed = (float) ((xSpeed * -1f) + bounceDecay + (Math.random() - .5));
			if (xSpeed > 0) {
				xSpeed = .01f;
			}
		}
		updateFPS();
		ticker++;
		if (ticker >= flSize) {
			ticker = 0;
		}

		if (t > 30) {
			System.out.println(Calendar.getInstance().getTimeInMillis() - s);
			System.exit(0);
		}
		t++;
	}

	private static long lastFPS;
	private static int fps;

	/**
	 * Get the time in milliseconds
	 * 
	 * @return The system time in milliseconds
	 */
	public static long getTime() {
		return System.nanoTime() / 1000000;
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public static void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);

			// if (fps < 30) {
			// System.out.println(fps + " " + exhausts.pewPewSize());
			// exhausts.setPews(new ArrayList<Pew>());
			// MAX_BOOST_TRIS -= 1000;
			// }

			fps = 0; // reset the FPS counter
			lastFPS += 1000; // add one second
		}
		fps++;
	}

	private static long MAX_BOOST_TRIS = 250000;
	private static long longestWait = 0;
}