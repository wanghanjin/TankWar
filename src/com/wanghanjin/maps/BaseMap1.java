package com.wanghanjin.maps;


/**
 * 实现 地图接口   而且必须拥有  这几个元素
 * @author 故事与猫
 *
 */
public class BaseMap1 implements Mapable{

	public static int[][] walls = {
		{1,1},
		{1,2},
		{1,3},

	};
	
	public static int[][] steels = {
		{2,1},
		{2,2},
		{2,3},

	};
	
	public static int[][] waters = {
		{3,1},
		{3,2},
		{3,3},

	};
	
	public static int[][] grasss = {
		{4,1},
		{4,2},
		{4,3},

	};
	
	
	//Home对象
	public static int[][] home = {

		{7,9},

	};
}
