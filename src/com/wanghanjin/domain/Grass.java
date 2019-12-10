package com.wanghanjin.domain;

/**
 * 草墙对象
 * 
 * @author 故事与猫 19-9-19
 */

public class Grass extends Element {

	// 构造方法：无参,有参
	public Grass(String imgPath, int x, int y) {
		super(imgPath, x, y);
	}

	// 公有的普通方法

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}
