package com.wanghanjin.domain;

import com.wanghanjin.domain.interfaces.Attackable;
import com.wanghanjin.domain.interfaces.Blockable;
import com.wanghanjin.domain.interfaces.Destroyable;
import com.wanghanjin.domain.interfaces.Hitable;
import com.wanghanjin.domain.interfaces.Moveable;
import com.wanghanjin.utils.CollsionUtils;
import com.wanghanjin.utils.Direction;

/**
 * 坦克父类 继承元素父类
 * 
 * 己方敌方都继承此类
 * 
 * 
 * @author 故事与猫
 * 
 */
public abstract class Tank extends Element implements Moveable, Blockable,
		Hitable, Destroyable {
	protected int blood;
	protected int speed = 16;
	protected int power;
	protected Direction direction = Direction.UP;

	protected int bullettime = 1000;
	protected long lastFire = 0l;
	protected Direction unmoveDirection;// 不可移动的方向

	public Tank(String imgPath, int x, int y) {
		super(imgPath, x, y);
	}

	@Override
	public Blast showDestroy() {
		return new Blast(this, true);
	}

	@Override
	public boolean isDestroy() {
		return this.blood <= 0 ? true : false;
	}

	@Override
	public Blast showExplosive(Attackable attackable) {
		this.blood -= attackable.getPower();
		return new Blast(this);
	}

	public boolean checkCollsion(Blockable blockable) {

		Element element = (Element) blockable;

		int x1 = element.x;
		int y1 = element.y;
		int w1 = element.width;
		int h1 = element.height;

		int x2 = this.x;
		int y2 = this.y;

		switch (direction) {
		case UP:
			y2 -= this.speed;
			break;
		case DOWN:
			y2 += this.speed;
			break;
		case LEFT:
			x2 -= this.speed;
			break;
		case RIGHT:
			x2 += this.speed;
			break;

		default:
			break;
		}

		boolean bool = CollsionUtils.isCollsionWithRect(x1, y1, w1, h1, x2, y2,
				width, height);
		if (bool) {
			this.unmoveDirection = direction;
		} else {
			this.unmoveDirection = null;
		}
		return bool;
	}

	public int getPower() {
		return power;
	}

	public Direction getDirection() {
		return direction;
	}

	public abstract void move(Direction direction);

	public abstract Bullet shot();

	public abstract void drawTank();

}
