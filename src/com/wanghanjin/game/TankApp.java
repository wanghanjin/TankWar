package com.wanghanjin.game;

import com.wanghanjin.utils.GameWindow;

public class TankApp {

	public static void main(String[] args) {

		System.out.println(TankApp.class + "");

		GameWindow gw = new GameWindow(Config.TITLE, Config.WIDTH,
				Config.HEIGHT, Config.FPS);
		gw.start();

	}

}
