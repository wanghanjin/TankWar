package com.wanghanjin.domain;

import com.wanghanjin.game.Config;
import com.wanghanjin.utils.Direction;

public class EnemyTank extends Tank {

	private long lastMove = 0l;

	public EnemyTank(String imgPath, int x, int y) {
		super(imgPath, x, y);

		power = Config.POWER;
		blood = Config.E_BLOOD;
	}

	public void move() {
		long nowTime = System.currentTimeMillis();
		if (nowTime - lastMove < 400) {
			return;
		}
		lastMove = nowTime;
		// 如果当前移动方向是不可移动方向
		if (direction.equals(unmoveDirection)) {
			direction = this.getRandomDirection();// 无法移动时获取随机方向
			return;
		}

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
		if (x < 0) {
			direction = this.getRandomDirection();
			x = 0;
		} else if (x > Config.WIDTH - this.width) {
			direction = this.getRandomDirection();
			x = Config.WIDTH - this.width;
		}

		if (y < 0) {
			direction = this.getRandomDirection();
			y = 0;
		} else if (y > Config.HEIGHT - this.height) {
			direction = this.getRandomDirection();
			y = Config.HEIGHT - this.height;

		}
	}

	/**
	 * 重写父类draw 2019-10-17 下午2:48:31
	 */

	String tank_u = "res\\img\\enemy_1_u.gif";
	String tank_d = "res\\img\\enemy_1_d.gif";
	String tank_l = "res\\img\\enemy_1_l.gif";
	String tank_r = "res\\img\\enemy_1_r.gif";

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
			tank_u = img_root + "tank2_u.gif";
			tank_d = img_root + "tank2_d.gif";
			tank_l = img_root + "tank2_l.gif";
			tank_r = img_root + "tank2_r.gif";
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

	private Direction getRandomDirection() {
		int random = (int) (Math.random() * 4);

		switch (random) {
		case 0:
			return Direction.UP;
		case 1:
			return Direction.DOWN;
		case 2:
			return Direction.LEFT;
		case 3:
			return Direction.RIGHT;
		default:
			return Direction.UP;
		}
	}

	@Override
	public void move(Direction direction) {
		// TODO Auto-generated method stub
		this.move();
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
