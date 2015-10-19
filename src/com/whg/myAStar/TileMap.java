package com.whg.myAStar;

import java.awt.Point;

public class TileMap implements Cloneable{
	/*
	public static final int[][] mapIndex = new int[][]{
		{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
		{ 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
		{ 0, 2, 2, 2, 2, 0, 0, 9, 9, 9, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, },
		{ 0, 0, 0, 2, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, },
		{ 0, 2, 0, 9, 9, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, },
		{ 0, 2, 0, 9, 0, 0, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, },
		{ 0, 0, 0, 9, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, },
		{ 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, },
		{ 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 9, 0, 0, 0, 9, 0, 0, },
		{ 0, 0, 9, 0, 0, 0, 0, 0, 9, 0, 0, 0, 9, 0, 0, 0, 9, 0, 0, 0, },
		{ 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 9, 0, 0, 0, 9, 0, 0, 0, },
		{ 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 9, 0, 0, 0, 9, 0, 0, 0, },
		{ 0, 0, 0, 0, 9, 9, 0, 9, 0, 0, 0, 0, 9, 0, 0, 0, 0, 9, 0, 0, },
		{ 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, },
		{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, },
	};
	*/
	
	public static final int[][] mapIndex = new int[][] {
			{ 2, 2, 2, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10 },
			{ 2, 2, 2, 6, 6, 6, 6, 6, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 15, 22, 22, 22, 22, 10 },
			{ 2, 2, 2, 33, 33, 33, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 18, 6, 6, 6, 6, 22, 10 },
			{ 10, 6, 6, 33, 15, 33, 33, 33, 6, 6, 33, 44, 33, 6, 6, 22, 22, 22, 22, 22, 22, 6, 22, 10 },
			{ 10, 6, 6, 6, 6, 18, 6, 6, 6, 33, 33, 44, 33, 33, 6, 6, 6, 6, 6, 6, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 22, 6, 10, 10, 6, 44, 44, 44, 44, 44, 44, 6, 10, 10, 10, 6, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 6, 6, 6, 10, 6, 33, 33, 33, 44, 33, 33, 6, 6, 6, 10, 6, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 6, 10, 6, 10, 6, 6, 6, 6, 6, 6, 33, 6, 10, 6, 10, 6, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 6, 10, 6, 10, 6, 33, 6, 1, 1, 6, 33, 6, 10, 6, 10, 6, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 6, 10, 6, 10, 6, 33, 6, 6, 6, 6, 6, 6, 10, 6, 10, 6, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 6, 10, 6, 6, 6, 33, 33, 44, 33, 33, 33, 6, 10, 6, 6, 6, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 6, 10, 10, 10, 6, 44, 44, 44, 44, 44, 44, 6, 10, 10, 6, 22, 22, 6, 22, 10 },
			{ 10, 22, 6, 22, 6, 6, 6, 6, 6, 6, 33, 33, 44, 33, 33, 6, 6, 6, 18, 6, 6, 6, 6, 10 },
			{ 10, 22, 6, 22, 22, 22, 22, 22, 22, 6, 6, 33, 44, 33, 6, 6, 33, 33, 33, 15, 33, 6, 6, 10 },
			{ 10, 22, 6, 6, 6, 6, 18, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 33, 33, 33, 2, 2, 2 },
			{ 10, 22, 22, 22, 22, 15, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 6, 6, 6, 6, 6, 2, 2, 2 },
			{ 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 2, 2, 2 }, 
		};

	private static final int DEFAUL_WIDTH = 20;
	private static final int DEFAULT_HEIGHT = 20;
	
	private boolean isDiagonal; //是否可以走斜线/对角线
	
	private final int width;
	private final int height;
	private final TileCell[][] cells;

//	public TileMap() {
//		this(DEFAUL_WIDTH, DEFAULT_HEIGHT);
//	}
//	
//	public TileMap(int width, int height){
//		this.width = width;
//		this.height = height;
//		this.cells = new TileCell[width][height];
//		initCells();
//	}
	
	public TileMap(){
		this(mapIndex, false);
	}
	
	public TileMap(boolean isDiagonal){
		this(mapIndex, isDiagonal);
	}
	
	public TileMap(int[][] mapIndex, boolean isDiagonal){
		this.isDiagonal = isDiagonal;
		this.width = mapIndex[0].length;
		this.height = mapIndex.length;
		this.cells = new TileCell[width][height];
		initCells();
	}
	
	private void initCells(){
//		for (int i = 0, y = 0; i < cells.length; i++, y += TileCell.HEIGHT) {
//			for (int j = 0, x = 0; j < cells[0].length; j++, x += TileCell.WIDTH) {
//				cells[j][i] = new TileCell(x, y);
//			}
//		}
		for (int i = 0, y = 0; i < height; i++, y += TileCell.HEIGHT) {
			for (int j = 0, x = 0; j < width; j++, x += TileCell.WIDTH) {
				//这里需要注意mapIndex的二维数组索引和map二维数组索引的x和y正好是互换颠倒的，
				//所以cells用j和i的顺序而不是i和j的顺序，但是判断是否是砖块却还是使用mapIndexi和j的顺序
				//System.out.println("i="+i+", j="+j+" (x="+x+", y="+y+")");
				cells[j][i] =  new TileCell(x, y, TileCellType.valueOf(mapIndex[i][j]));
			}
		}
	}
	
	@Override
	public TileMap clone(){
		//return new TileMap(width, height);
		return new TileMap(mapIndex, false);
	}
	
	public TileCell getStart(Player player){
		return getCell(TileCell.transformCellPointRound(player.getPoint()));
	}
	
	public boolean isStart(Player player, TileCell targetCell){
		return getStart(player).equals(targetCell);
	}
	
	public TileCell getEnd(TileCell targetCell) {
		Point cell = targetCell.getCellPosition();
		return getCell(cell.x, cell.y);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public TileCell[][] getCells() {
		return cells;
	}
	
	public TileCell getCell(Point point){
		return getCell(point.x, point.y);
	}

	public TileCell getCell(int x, int y) {
		if(x < 0 || x >= width || y < 0 || y >= height){
			return null;
		}
		return cells[x][y];
	}
	
	/** 获取相邻的地图板块中距离最短的，即到开始距离最短的（或者说cost最小的） */
	public TileCell getLowestAdjacent(TileCell cell) {
		TileCell[] adjacents = getAdjacents(cell);
		TileCell small = adjacents[0];
		double dist = Double.MAX_VALUE;
		for (int i = 0; i < adjacents.length; i++) {
			if (adjacents[i] == null) {
				continue;
			}
			double nextDist = adjacents[i].getG();
			if (nextDist < dist && nextDist >= 0) {
				small = adjacents[i];
				dist = adjacents[i].getG();
			}
		}
		return small;
	}
	
	/** 获取相邻的地图板块 */
	public TileCell[] getAdjacents(TileCell g) {
		if(isDiagonal){
			return eightAdjacents(g);
		}else{
			return fourAdjacents(g);
		}
	}
	
	private TileCell[] eightAdjacents(TileCell g){
		TileCell adjacents[] = new TileCell[8];
		Point p = g.getCellPosition();
		if (p.y != 0) {
			adjacents[0] = cells[p.x][p.y - 1];		//top
			if(!adjacents[0].isCanPass()) adjacents[0] = null;
		}
		if (p.x != width - 1) {
			adjacents[1] = cells[p.x + 1][p.y];		//right
			if(!adjacents[1].isCanPass()) adjacents[1] = null;
		}
		if (p.y != height - 1) {
			adjacents[2] = cells[p.x][p.y + 1];		//bottom
			if(!adjacents[2].isCanPass()) adjacents[2] = null;
		}
		if (p.x != 0) {
			adjacents[3] = cells[p.x - 1][p.y];		//left
			if(!adjacents[3].isCanPass()) adjacents[3] = null;
		}
		if(p.y != 0 && p.x != 0 && (adjacents[0].isCanPass() || adjacents[3].isCanPass())){
			adjacents[4] = cells[p.x - 1][p.y - 1];	//left-top
			if(adjacents[4].isCanPass()) adjacents[4].setCost(1.41);
			else adjacents[4] = null;
		}
		if(p.y != 0 && p.x != width - 1 && (adjacents[0].isCanPass() || adjacents[1].isCanPass())){
			adjacents[5] = cells[p.x + 1][p.y - 1];	//right-top
			if(adjacents[5].isCanPass()) adjacents[5].setCost(1.41);
			else adjacents[5] = null;
		}
		if(p.x != 0 && p.y != height - 1 && (adjacents[3].isCanPass() || adjacents[2].isCanPass())){
			adjacents[6] = cells[p.x - 1][p.y + 1];	//left-bottom
			if(adjacents[6].isCanPass()) adjacents[6].setCost(1.41);
			else adjacents[6] = null;
		}
		if(p.x != width - 1 && p.y != height - 1 && (adjacents[1].isCanPass() || adjacents[2].isCanPass())){
			adjacents[7] = cells[p.x + 1][p.y + 1];	//right-bottom
			if(adjacents[7].isCanPass()) adjacents[7].setCost(1.41);
			else adjacents[7] = null;
		}
		return adjacents;
	}
	
	private TileCell[] fourAdjacents(TileCell g){
		TileCell adjacents[] = new TileCell[4];
		Point p = g.getCellPosition();
		if (p.y != 0) {
			adjacents[0] = cells[p.x][p.y - 1];		//top
			if(!adjacents[0].isCanPass()) adjacents[0] = null;
		}
		if (p.x != width - 1) {
			adjacents[1] = cells[p.x + 1][p.y];		//right
			if(!adjacents[1].isCanPass()) adjacents[1] = null;
		}
		if (p.y != height - 1) {
			adjacents[2] = cells[p.x][p.y + 1];		//bottom
			if(!adjacents[2].isCanPass()) adjacents[2] = null;
		}
		if (p.x != 0) {
			adjacents[3] = cells[p.x - 1][p.y];		//left
			if(!adjacents[3].isCanPass()) adjacents[3] = null;
		}
		return adjacents;
	}

//	public void resetCells() {
//		for(TileCell[] columns:cells){
//			for(TileCell row:columns){
//				row.reset();
//			}
//		}
//	}
	
	public void notShow(TileCell cell){
		getCell(cell.getCellPosition()).notShow();
	}
	
	public void notPath(TileCell cell) {
		getCell(cell.getCellPosition()).notPath();
	}

}
