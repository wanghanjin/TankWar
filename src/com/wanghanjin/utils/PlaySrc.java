package com.wanghanjin.utils;

import java.io.IOException;

public class PlaySrc {

	/**
	 * 媒体资源的调用
	 * 
	 * @param res
	 */
	public static void playSound(String res) {
		try {
			SoundUtils.play(res);
		} catch (IOException e) {
			System.out.println("出了问题:" + e.getMessage());
		}
	}

	public static void drawImg(String res, int x, int y) {
		try {
			DrawUtils.draw(res, x, y);
		} catch (IOException e) {
			System.out.println("出了问题:" + e.getMessage());
		}
	}

}
