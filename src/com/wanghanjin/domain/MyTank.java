package com.wanghanjin.domain;

import com.wanghanjin.game.Config;
import com.wanghanjin.utils.Direction;

public class MyTank extends Tank {

	public MyTank(String imgPath, int x, int y) {
		super(imgPath, x, y);
		// TODO Auto-generated constructor stub

		power = Config.POWER;
		blood = Config.M_BLOOD;
	}

	public int getBlood() {
		return blood;
	}

	public void move(Direction direction) {

		if (!this.direction.equals(direction)) {
			this.direction = direction;
			return;
		}

		// 如果当前移动方向是不可移动方向，，则直接退出
		if (direction.equals(unmoveDirection)) {
			return;
		}

		this.direction = direction;

		switch (direction) {
		case UP:
			y -= speed;
			break;

		case DOWN:
			y += speed;
			break;

		case LEFT:
			x -= speed;
			break;

		case RIGHT:
			x += speed;
			break;

		case RESET:
			// 复位
			x = Config.WIDTH / 2 - Config.PX / 2;
			y = Config.HEIGHT - Config.PX;

			break;
		default:
			break;
		}

		/**
		 * 越界检测
		 */
		if (x <= 0) {
			x = 0;
		} else if (x >= Config.WIDTH - this.width) {
			x = Config.WIDTH - this.width;
		}

		if (y <= 0) {
			y = 0;
		} else if (y >= Config.HEIGHT - this.height) {
			y = Config.HEIGHT - this.height;
		}

	}

	/**
	 * 重写父类draw 2019-10-17 下午2:48:31
	 */

	String tank_u = "res\\img\\tank_u.gif";
	String tank_d = "res\\img\\tank_d.gif";
	String tank_l = "res\\img\\tank_l.gif";
	String tank_r = "res\\img\\tank_r.gif";

	@Override
	public void draw() {
		this.drawTank();
	}

	/**
	 * 设置坦克皮肤
	 */
	String img_root = "res\\img\\";

	public void skin(int i) {

		switch (i) {
		case 1:
			tank_u = img_root + "tank_2_u.gif";
			tank_d = img_root + "tank_2_d.gif";
			tank_l = img_root + "tank_2_l.gif";
			tank_r = img_root + "tank_2_r.gif";
			break;
		case 2:

			break;
		default:
			break;
		}
	}

	public Bullet shot() {

		long nowTime = System.currentTimeMillis();// 获得当前时间戳

		if ((nowTime - lastFire) < bullettime) {
			return null;
		}
		lastFire = nowTime;
		return new Bullet(this);
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setBullettime(int bullettime) {
		this.bullettime = bullettime;
	}

	@Override
	public void drawTank() {
		switch (direction) {
		case UP:
			this.imgPath = tank_u;
			break;
		case DOWN:
			this.imgPath = tank_d;
			break;
		case LEFT:
			this.imgPath = tank_l;
			break;
		case RIGHT:
			this.imgPath = tank_r;
			break;
		default:
			break;
		}
		super.draw();
	}

}
