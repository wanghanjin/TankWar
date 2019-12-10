package com.wanghanjin.domain.interfaces;

import com.wanghanjin.domain.Element;

/**
 * 具有攻击能力接口 
 * 
 * 
 */
public interface Attackable {
	// 判断是否击中
	public boolean checkCollsion(Element element);

	public int getPower();

}
