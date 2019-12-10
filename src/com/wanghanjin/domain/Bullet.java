package com.wanghanjin.domain;

import java.io.IOException;

import com.wanghanjin.domain.interfaces.Attackable;
import com.wanghanjin.domain.interfaces.Destroyable;
import com.wanghanjin.game.Config;
import com.wanghanjin.utils.CollsionUtils;
import com.wanghanjin.utils.Direction;
import com.wanghanjin.utils.DrawUtils;
import com.wanghanjin.utils.PlaySrc;

/**
 * 炮弹类
 * 
 * @author 故事与猫 2019-10-21 下午7:54:18
 */
public class Bullet extends Element implements Attackable, Destroyable {

	private Direction direction;// 方向
	private int power;// 取决于坦克
	private int speed = 10;
	private Tank tank;// 记录此炮弹是谁发射

	public Tank getTank() {
		return this.tank;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public Bullet(String imgPath, int x, int y) {
		super(imgPath, x, y);
		// TODO Auto-generated constructor stub
	}

	public Bullet(Tank myTank) {

		super("res\\img\\bullet_u.gif", 10, 20);

		this.direction = myTank.getDirection();
		this.power = myTank.getPower();
		this.imgPath = "res\\img\\bullet_u.gif";

		this.tank = myTank;

		// System.out.println(direction);

		try {
			int[] size = DrawUtils.getSize(this.imgPath);

			this.width = size[0];
			this.height = size[1];
		} catch (IOException e) {
			e.printStackTrace();
		}

		switch (direction) {
		case UP:

			this.x = myTank.x + (myTank.width - this.width) / 2;
			this.y = myTank.y - this.height / 2;
			this.imgPath = "res\\img\\bullet_u.gif";

			break;
		case DOWN:

			this.x = myTank.x + (myTank.width - this.width) / 2;
			this.y = myTank.y + (myTank.height - this.height / 2);
			this.imgPath = "res\\img\\bullet_d.gif";
			break;
		case LEFT:
			this.x = myTank.x - this.width / 2;
			this.y = myTank.y + (myTank.height - this.height) / 2;
			this.imgPath = "res\\img\\bullet_l.gif";
			break;
		case RIGHT:
			this.x = myTank.x + myTank.width - this.width / 2;
			this.y = myTank.y + (myTank.height - this.height) / 2;
			this.imgPath = "res\\img\\bullet_r.gif";
			break;
		default:
			break;
		}

		PlaySrc.playSound("res\\snd\\fire.wav");

	}

	// 重写父类 画的方法，这个子弹 归bullet类来画
	@Override
	public void draw() {

		switch (direction) {
		case UP:
			this.y -= this.speed;
			break;
		case DOWN:
			this.y += this.speed;
			break;
		case LEFT:
			this.x -= this.speed;
			break;
		case RIGHT:
			this.x += this.speed;
			break;

		default:
			break;
		}
		super.draw();
	}

	/**
	 * 检查炮弹是否飞出窗体
	 */
	public boolean isDestroy() {
		if (y < 0 - height || y > Config.HEIGHT || x < 0 - width
				|| x > Config.WIDTH) {
			return true;
		} else {
			return false;
		}
	}

	// 实现接口中的方法
	// 判断炮弹是否击中目标 障碍物是 element
	public boolean checkCollsion(Element element) {
		// 被击中目标的坐标
		int x1 = element.x;
		int y1 = element.y;
		int w1 = element.width;
		int h1 = element.height;

		return CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x, y, width,
				height);
	}

	@Override
	public int getPower() {
		// TODO Auto-generated method stub
		return this.power;
	}

	@Override
	public Blast showDestroy() {
		// TODO Auto-generated method stub
		return null;
	}

}
