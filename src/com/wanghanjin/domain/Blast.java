package com.wanghanjin.domain;

import java.io.IOException;

import com.wanghanjin.domain.interfaces.Destroyable;
import com.wanghanjin.utils.DrawUtils;
import com.wanghanjin.utils.PlaySrc;

public class Blast extends Element implements Destroyable {

	boolean isDestroy;
	int index = 0;

	private String[] imgArray = { "res\\img\\blast_1.gif",
			"res\\img\\blast_2.gif", "res\\img\\blast_3.gif",
			"res\\img\\blast_4.gif", "res\\img\\blast_5.gif" };

	public Blast(Element element) {
		int x1 = element.x;
		int y1 = element.y;

		int w1 = element.width;
		int h1 = element.height;

		try {
			int[] size = DrawUtils.getSize("res\\img\\blast_4.gif");
			this.width = size[0];
			this.height = size[1];

		} catch (IOException e) {
			System.out.println(e);
		}

		this.x = x1 + (w1 - this.width) / 2;
		this.y = y1 + (h1 - this.height) / 2;
	}

	public Blast(Element element, boolean isDie) {
		this(element);
		if (isDie) {
			imgArray = new String[] { "res\\img\\blast_1.gif",
					"res\\img\\blast_2.gif", "res\\img\\blast_3.gif",
					"res\\img\\blast_4.gif", "res\\img\\blast_5.gif",
					"res\\img\\blast_6.gif", "res\\img\\blast_7.gif",
					"res\\img\\blast_8.gif" };
		}
	}

	public void draw() {
		this.imgPath = imgArray[index++];
		if (index >= imgArray.length) {
			index = 0;

			this.isDestroy = true;

		}

		PlaySrc.drawImg(this.imgPath, x, y);
	}

	public boolean isDestroy() {
		return this.isDestroy;
	}

	@Override
	public Blast showDestroy() {
		// TODO Auto-generated method stub
		return null;
	}

}
