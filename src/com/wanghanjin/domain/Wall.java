package com.wanghanjin.domain;

import com.wanghanjin.domain.interfaces.Attackable;
import com.wanghanjin.domain.interfaces.Blockable;
import com.wanghanjin.domain.interfaces.Destroyable;
import com.wanghanjin.domain.interfaces.Hitable;
import com.wanghanjin.game.Config;

/**
 * 
 * @author 故事与猫
 *
 */

/**
 * 砖墙对象
 * 
 * @author 故事与猫 19-9-19
 */

public class Wall extends Element implements Blockable, Hitable, Destroyable {

	private int blood;// 血量

	// 构造方法：无参,有参
	public Wall(String imgPath, int x, int y) {
		super(imgPath, x, y);
		this.blood = Config.W_BLOOD;
	}

	@Override
	public Blast showDestroy() {
		// TODO Auto-generated method stub
		return new Blast(this, true);
	}

	@Override
	public boolean isDestroy() {
		// TODO Auto-generated method stub
		return this.blood <= 0 ? true : false;
	}

	@Override
	public Blast showExplosive(Attackable attackable) {
		this.blood -= attackable.getPower();
		return new Blast(this);
	}

}
