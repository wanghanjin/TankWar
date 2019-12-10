package com.wanghanjin.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public abstract class Window {
	private final String title;// 鏍囬
	private final int width;// 灞忓箷瀹藉害
	private final int height;// 灞忓箷鐨勯珮搴�
	private final int fps;// 姣忕閽熷埛鏂扮殑甯х巼

	private boolean running;
	private ScheduledExecutorService pool;
	private Map<Integer, ScheduledFuture<?>> tasks = new LinkedHashMap();

	public Window(String title, int width, int height, int fps) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.fps = fps;
	}

	/**
	 * 寮�娓告垙
	 */
	public final void start() {
		if (running) {
			return;
		}
		running = true;

		pool = Executors.newScheduledThreadPool(16);

		// 鍒濆鍖栫獥浣�
		initGL();

		// 鍒涘缓鏃剁殑鍥炶皟
		onCreate();

		while (running && !Display.isCloseRequested()) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			// 澶勭悊鐢ㄦ埛浜や簰杈撳叆
			processInput();

			// 澶勭悊UI鍒锋柊鏄剧ず
			processDisplay();

			Display.update();
			Display.sync(fps);
		}

		pool.shutdownNow();
		Display.destroy();
	}

	/**
	 * 閫�嚭娓告垙
	 */
	public final void stop() {
		if (!running) {
			return;
		}
		running = false;
	}

	private void initGL() {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Display.setTitle(title);
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	private void processInput() {
		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				onMouseEvent(Mouse.getEventButton(), Mouse.getX(), Mouse.getY());
			}
		}

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				// 鎸変笅
				int key = Keyboard.getEventKey();
				onKeyEvent(key);

				addLongPress(key);
			} else {
				// 閲婃斁
				int key = Keyboard.getEventKey();
				removeLongPress(key);
			}
		}
	}

	private void addLongPress(final int key) {
		ScheduledFuture<?> future = tasks.get(key);
		if (future != null) {
			future.cancel(true);
			future = null;
		}

		future = pool.scheduleAtFixedRate(new Runnable() {
			public void run() {
				onKeyEvent(key);
			}
		}, 100, 100, TimeUnit.MILLISECONDS);

		tasks.put(key, future);
	}

	private void removeLongPress(int key) {
		ScheduledFuture<?> future = tasks.get(key);
		if (future != null) {
			future.cancel(true);
			future = null;
		}
	}

	private void processDisplay() {
		onDisplayUpdate();
	}

	/**
	 * 绐椾綋鍒涘缓鏃剁殑鍥炶皟
	 */
	protected abstract void onCreate();

	/**
	 * 榧犳爣down浜嬩欢
	 * 
	 * @param key
	 *            0涓哄乏閿�1涓哄彸閿�
	 * @param x
	 *            x鍧愭爣
	 * @param y
	 *            y鍧愭爣
	 */
	protected abstract void onMouseEvent(int key, int x, int y);

	/**
	 * 鎸夐敭浜嬩欢
	 * 
	 * @param key
	 *            鎸夐敭锛寋@code Keyboard.key_xx}
	 */
	protected abstract void onKeyEvent(int key);

	/**
	 * 褰撳睆骞曞埛鏂版椂鐨勫洖璋�
	 */
	protected abstract void onDisplayUpdate();
}
