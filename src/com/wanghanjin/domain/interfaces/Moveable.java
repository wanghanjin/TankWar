package com.wanghanjin.domain.interfaces;

public interface Moveable {

	/**
	 * 判断 可移动元素是否发生障碍碰撞
	 *
	 */
	public boolean checkCollsion(Blockable blockable);
}
