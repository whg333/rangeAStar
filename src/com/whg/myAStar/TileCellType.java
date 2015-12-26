package com.whg.myAStar;

import java.awt.Color;

public enum TileCellType {

	/*
	NORMAL(0, 1, null), 
	EASY(1, 0.3, Color.BLUE), 
	TOUGH(2, 2, Color.PINK), 
	VERY_TOUGH(3, 10, Color.ORANGE), 
	BLOCK(9, Double.MAX_VALUE, Color.DARK_GRAY);
	*/
	
	camp(2, 1, Color.WHITE, "大营"),
	resurrection(15, Double.MAX_VALUE, new Color(192, 192, 192), "复活"),
	road(6, 1, Color.YELLOW, "道路"),
	forest(10, 2, new Color(0, 128, 0), "森林"),
	shoal(44, 1, new Color(255, 204, 0), "浅滩"),
	hills(22, Double.MAX_VALUE, new Color(255, 128, 128), "群山"),
	rivers(33, Double.MAX_VALUE, new Color(0, 204, 255), "浅水"),
	commonResource(18, 1, new Color(153, 51, 102), "普通"),
	centerResource(1, 1, Color.BLACK, "中央");
	
	private int index;
	private double cost;
	private Color color;
	private String desc;
	
	TileCellType(int index, double cost, Color color, String desc){
		this.index = index;
		this.cost = cost;
		this.color = color;
		this.desc = desc;
	}
	
	public int index(){
		return index;
	}
	
	public double cost(){
		return cost;
	}
	
	public Color color(){
		return color;
	}
	
	public String desc(){
		return desc;
	}

	public static TileCellType valueOf(int i) {
		for(TileCellType type:values()){
			if(type.index == i){
				return type;
			}
		}
		throw new IllegalArgumentException("Unsupport index:"+i);
	}
	
}
