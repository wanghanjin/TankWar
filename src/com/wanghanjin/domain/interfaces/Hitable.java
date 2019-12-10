package com.wanghanjin.domain.interfaces;

import com.wanghanjin.domain.Blast;

/**
 * 被攻击接口
 * 
 * 
 */
public interface Hitable {

	// 被攻击力击中 返回爆炸物
	public Blast showExplosive(Attackable attackable);

}
