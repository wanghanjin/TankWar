package com.wanghanjin.domain;

import com.wanghanjin.domain.interfaces.Blockable;

/**
 * 水墙对象
 * 
 * @author 故事与猫 19-9-19
 */

public class Water extends Element implements Blockable {

	// 构造方法：无参,有参
	public Water(String imgPath, int x, int y) {
		// this。指的就是wall对象this.x就是wall对象x的值
		super(imgPath, x, y);
	}
	// 公有的普通方法

}
