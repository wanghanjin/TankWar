package com.wanghanjin.domain;

import java.io.IOException;

import javax.swing.JFrame;

import com.wanghanjin.utils.DrawUtils;

/**
 * 元素父类 所有元素继承
 * 
 * @author 故事与猫 19-9-19
 */

public class Element {
	// 私有的属性
	protected String imgPath;// 元素父类图片地址
	protected int x;// 元素父类图片x轴
	protected int y;// 元素父类图片Y=y轴
	protected int width;// 元素父类图片的宽
	protected int height;// 元素父类图片的高

	public Element() {
		// TODO Auto-generated constructor stub
	}

	// 构造方法：无参,有参
	public Element(String imgPath, int x, int y) {
		// this。指的就是wall对象this.x就是wall对象x的值
		// =后面的x 值得是构造方法参数x;
		this.x = x;
		this.y = y;
		this.imgPath = imgPath;
		// DrawUtlis
		int[] size = null;
		try {
			size = DrawUtils.getSize(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = size[0];
		this.height = size[1];
	}

	// 构造方法：无参,有参
	public Element(int x, int y, String imgPath) {
		// this。指的就是wall对象this.x就是wall对象x的值
		// =后面的x 值得是构造方法参数x;
		this.x = x;
		this.y = y;
		this.imgPath = imgPath;
		// DrawUtlis
		int[] size = null;
		try {
			size = DrawUtils.getSize(imgPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.width = size[0];
		this.height = size[1];
	}

	// 公有的普通方法
	// 绘制元素父类的方法
	public void draw() {
		try {
			DrawUtils.draw(imgPath, x, y);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getOrder() {
		return 0;
	}

}
