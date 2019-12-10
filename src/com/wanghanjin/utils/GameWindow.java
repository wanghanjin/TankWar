package com.wanghanjin.utils;

import java.io.IOException;
import java.util.Comparator;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.wanghanjin.domain.interfaces.*;
import com.wanghanjin.domain.*;
import com.wanghanjin.game.Config;
import com.wanghanjin.maps.*;

public class GameWindow extends Window {

	// 新建一组砖墙对象
	public List<Element> mElementList = new CopyOnWriteArrayList<Element>();

	private MyTank myTank;
	private MyTank myTank2;

	public GameWindow(String title, int width, int height, int fps) {

		super(title, width, height, fps);


	}

	@Override
	protected void onCreate() {

		int[][] walls = Name.walls;
		int[][] steels = Name.steels;
		int[][] waters = Name.waters;
		int[][] grasss = Name.grasss;
		int[][] home = Name.home;

		// 土墙
		for (int i = 0; i < walls.length; i++) {
			int x1 = walls[i][0];
			int y1 = walls[i][1];
			Wall ele = new Wall("res\\img\\wall.gif", x1 * Config.PX, y1
					* Config.PX);
			this.addElement(ele);
		}
		// 铁墙
		for (int i = 0; i < steels.length; i++) {
			int x1 = steels[i][0];
			int y1 = steels[i][1];
			Steel ele = new Steel("res\\img\\steel.gif", x1 * Config.PX, y1
					* Config.PX);
			this.addElement(ele);
		}

		// 水
		for (int i = 0; i < waters.length; i++) {
			int x1 = waters[i][0];
			int y1 = waters[i][1];
			Water ele = new Water("res\\img\\water.gif", x1 * Config.PX, y1
					* Config.PX);
			this.addElement(ele);
		}

		// 草丛
		for (int i = 0; i < grasss.length; i++) {
			int x1 = grasss[i][0];
			int y1 = grasss[i][1];
			Grass ele = new Grass("res\\img\\grass.gif", x1 * Config.PX, y1
					* Config.PX);
			this.addElement(ele);
		}

		// 老家
		for (int i = 0; i < home.length; i++) {
			int x1 = home[i][0];
			int y1 = home[i][1];
			Home ele = new Home("res\\img\\symbol.gif", x1 * Config.PX, y1
					* Config.PX);
			this.addElement(ele);
		}

		// 坦克 1号
		myTank = new MyTank("res\\img\\tank_u.gif", Config.WIDTH / 2
				- Config.PX / 2 * 5, Config.HEIGHT - Config.PX);

		// 坦克2 号
		myTank2 = new MyTank("res\\img\\tank_u.gif", Config.WIDTH / 2
				+ Config.PX / 2 * 3, Config.HEIGHT - Config.PX);

		myTank2.setBullettime(10);
		// myTank2.setSpeed(64);
		myTank2.skin(1);

		this.addElement(myTank);
		this.addElement(myTank2);

		EnemyTank enemyTank = new EnemyTank("res\\img\\enemy_1_d.gif", 0, 0);
		this.addElement(enemyTank);

		EnemyTank enemyTank2 = new EnemyTank("res\\img\\enemy_1_d.gif",
				Config.WIDTH - Config.PX, 0);
		this.addElement(enemyTank2);

	}

	@Override
	protected void onMouseEvent(int key, int x, int y) {

	}

	@Override
	protected void onKeyEvent(int key) {

		// 上下左右事件 ( //坦克 1号) WASD 空格
		if (key == Keyboard.KEY_W) {
			myTank.move(Direction.UP);
		} else if (key == Keyboard.KEY_S) {
			myTank.move(Direction.DOWN);
		} else if (key == Keyboard.KEY_A) {
			myTank.move(Direction.LEFT);
		} else if (key == Keyboard.KEY_D) {
			myTank.move(Direction.RIGHT);
		} else if (key == Keyboard.KEY_F5) {
			myTank.move(Direction.RESET);
		} else if (key == Keyboard.KEY_SPACE) {
			Bullet bullet = myTank.shot();// 开炮
			// bullet.setSpeed(1);
			if (bullet != null) {
				this.addElement(bullet);
			}
		}

		if (myTank2.getBlood() <= 0) {

			System.out.println("已经死亡  不能发射和移动");

		} else {

			// 上下左右事件( //坦克 2号) 上下左右 回车
			if (key == Keyboard.KEY_UP) {
				myTank2.move(Direction.UP);
			} else if (key == Keyboard.KEY_DOWN) {
				myTank2.move(Direction.DOWN);
			} else if (key == Keyboard.KEY_LEFT) {
				myTank2.move(Direction.LEFT);
			} else if (key == Keyboard.KEY_RIGHT) {
				myTank2.move(Direction.RIGHT);
			} else if (key == Keyboard.KEY_F5) {
				myTank2.move(Direction.RESET);
			} else if (key == Keyboard.KEY_RETURN) {
				Bullet bullet = myTank2.shot();// 开炮
				bullet.setSpeed(50);
				if (bullet != null) {
					this.addElement(bullet);
				}
			}

		}

	}

	@Override
	protected void onDisplayUpdate() {

		Iterator<Element> it = mElementList.iterator();

		// System.out.println(mElementList.size());

		while (it.hasNext()) {
			Element ele = (Element) it.next();

			if (ele instanceof Bullet) {
				boolean bool = ((Bullet) ele).isDestroy();

				if (bool) {
					removeElement(ele);
				}
			}

			// 判断当前元素是否为 可移动元素
			if (ele instanceof Moveable) {
				Moveable moveable = (Moveable) ele;

				Iterator<Element> it2 = mElementList.iterator();

				while (it2.hasNext()) {
					Element element2 = (Element) it2.next();

					if (element2 instanceof Blockable && ele != element2) {
						// 如果元素属于 不可通过元素
						Blockable block = (Blockable) element2;
						boolean bool = moveable.checkCollsion(block);
						if (bool) {
							break;// 如果发生碰撞就终止循环
						}
					}
				}
			}

			// 判断元素一 是炮弹类 判断是否是 击中目标 如果是则 销毁炮弹 则播放销毁 场景。
			if (ele instanceof Attackable) {
				Attackable attackable = (Attackable) ele;
				Iterator<Element> it3 = mElementList.iterator();
				while (it3.hasNext()) {
					Element element3 = (Element) it3.next();

					if (element3 instanceof Hitable) {

						if (ele instanceof Bullet) {
							Bullet bullet = (Bullet) ele;
							// 判断是不是自己
							if (bullet.getTank().getClass() == element3
									.getClass()) {
								continue;
							}
						}

						boolean bool = attackable.checkCollsion(element3);

						if (bool) {
							// 移除此炮弹
							removeElement(ele);

							Hitable hitable = (Hitable) element3;
							Blast blast = hitable.showExplosive(attackable);
							addElement(blast);

							if (element3 instanceof Home) {
								// 敌军获胜
								Winner winner = new Winner(false,
										"res\\img\\failed.gif");
								this.mElementList.clear();
								this.addElement(winner);
							}

							break;
						}
					}
				}
			}

			if (ele instanceof Destroyable) {
				Destroyable destroyable = (Destroyable) ele;
				boolean bool = destroyable.isDestroy();
				if (bool) {
					Blast blast = destroyable.showDestroy();
					if (blast != null) {
						addElement(blast);
					}
					this.removeElement(ele);
				}
			}

			if (ele instanceof EnemyTank) {
				EnemyTank enemyTank = (EnemyTank) ele;
				enemyTank.move();
				Bullet bullet = enemyTank.shot();
				if (bullet != null) {
					this.addElement(bullet);
				}
			}

			ele.draw();
		}

	}

	/**
	 * 添加元素 到 集合
	 * 
	 * @param element
	 */
	private void addElement(Element element) {
		this.mElementList.add(element);
		this.mElementList.sort(new Comparator<Element>() {

			@Override
			public int compare(Element o1, Element o2) {
				// TODO Auto-generated method stub
				return o1.getOrder() - o2.getOrder();
			}
		});
	}

	@SuppressWarnings("unused")
	private void removeElement(Element element) {
		this.mElementList.remove(element);

		int myTankNum = 0;
		int enemyTankNum = 0;

		Iterator<Element> it = mElementList.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			if (e instanceof MyTank) {
				myTankNum++;
			} else if (e instanceof EnemyTank) {
				enemyTankNum++;
			}
		}

		if (myTankNum == 0) {
			// 敌军获胜
			Winner winner = new Winner(false, "res\\img\\failed.gif");
			this.mElementList.clear();
			this.addElement(winner);

		} else if (enemyTankNum == 0) {
			// 友军获胜
			Winner winner = new Winner(true, "res\\img\\win.gif");
			this.mElementList.clear();
			this.addElement(winner);
		}

	}

}
