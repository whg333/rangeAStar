package com.whg.myAStar;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.whg.myAStar.animation.Animation;
import com.whg.myAStar.path.AStar;
import com.whg.myAStar.path.FudgeAStar;
import com.whg.myAStar.path.Path;

public class Player extends Animation{

	private static final int SPEED = 5;
	
	private int power;
	
	/** 
	 * 不能让每个Player有自己单独的A星寻路任务然后再返回，
	 * 因为使用Executor的时候，每次都拿到的是同一个A星Runnable，
	 * cancel的时候，会导致把本来正在寻路或者移动的这个唯一的Runnable给中断了  
	 */
	//private final AStart findPathTask = new FudgeAStart();
	
	private final int id;
	
	public Player(int id, int width, int height, int x, int y, String imgPath) {
		super(width, height, x, y, imgPath);
		this.id = id;
	}
	
	public void setPower(int power){
		this.power = power;
	}
	
	public boolean isHasPower(){
		return power > 0;
	}
	
	public void descPower(){
		power = Math.max(power-1, 0);
	}
	
	public void moveUp(){
		int oldY = y;
		y = Math.max(y - SPEED, 0);
		changeDirection(x, oldY, x, y);
	}
	
	public void moveDown(int height){
		int oldY = y;
		y = Math.min(y + SPEED, height - TileCell.HEIGHT - 1);
		changeDirection(x, oldY, x, y);
	}
	
	public void moveLeft(){
		int oldX = x;
		x = Math.max(x - SPEED, 0);
		changeDirection(oldX, y, x, y);
	}
	
	public void moveRight(int width){
		int oldX = x;
		x = Math.min(x + SPEED, width - TileCell.WIDTH - 1);
		changeDirection(oldX, y, x, y);
	}
	
	public boolean arrivedAt(TileCell cell) {
		return x == cell.getX() && y == cell.getY();
	}
	
	public void closeTo(TileCell cell){
		int oldX = x;
		int oldY = y;
		
		if(x > cell.getX()){
			x--;
		}else if(x < cell.getX()){
			x++;
		}
		
		if(y > cell.getY()){
			y--;
		}else if(y < cell.getY()){
			y++;
		}
		
		changeDirection(oldX, oldY, x, y);
	}
	
	@Override
	public String toString(){
		return "Player ("+x+", "+y+"), id="+id;
	}
	
//	public AStart getFindPathTask() {
//		return findPathTask;
//	}
	
	public AStar newFindPathTask(TileMap map, TileCell targetCell, Set<Player> actors) {
		AStar astart = new FudgeAStar();
		astart.init(map, this, targetCell, actors);
		return astart;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public boolean isCollision(Rectangle drawRouseDraggedRect) {
		return new Rectangle(x, y, width, height).intersects(drawRouseDraggedRect);
	}
	
	private TileMap map;
	
	public boolean isHasPowerPathsMap(){
		return map != null;
	}
	
	public TileMap getPowerPathsMap(){
		return map;
	}
	
	/** 把查找出来的最短路径标记出来，便于显示 */
	public void markPath2Show(Path path){
		for(TileCell cell:path.getNodes()){
			TileCell pathCell = map.getCell(cell.getCellPosition());
			if(pathCell == null){
				continue;
			}
			pathCell.markPath();
		}
	}

	public void collectTargetPaths(TileMap map, Set<Player> actors) {
		this.map = map.clone();
		collectTargetPaths(actors);
	}
	
	private List<TileCell> open;
	
	public boolean isCanMove(TileCell targetCell){
		if(open == null || open.isEmpty()){
			return false;
		}
		return open.contains(targetCell);
	}
	
	private List<TileCell> getOpen(){
		if(open == null){
			open = new ArrayList<TileCell>();
		}
		open.clear();
		return open;
	}
	
	private void collectTargetPaths(Set<Player> actors){
		long begin = System.currentTimeMillis();
		List<TileCell> open = getOpen();
		List<TileCell> closed = new ArrayList<TileCell>();
		
		TileCell start = map.getStart(this);
		start.markStart();
		start.show();
		open.add(start);
		
		int currPower = 10;
		setPower(currPower);
		while(isHasPower()){
			List<TileCell> cloneOpen = new ArrayList<TileCell>(open);
			Iterator<TileCell> openIt = cloneOpen.iterator();
			while(openIt.hasNext()){
				TileCell cell = openIt.next();
				TileCell[] adjacents = map.getAdjacents(cell);
				for(TileCell adjacent:adjacents){
					if(adjacent == null){
						continue;
					}
					
					adjacent.addG(cell);
					if(adjacent.getG() > currPower){
						continue;
					}
					if(!closed.contains(adjacent) && !open.contains(adjacent)){
						adjacent.show();
						open.add(adjacent);
					}
					
					//这里不拷贝地图是因为，其中的G值（即代价）是一直在变化的
					//如果拷贝的话，在未选择最小值的G（最优路径）时就会提前排除对该点邻居的后续查找了
					//AStar star = newFindPathTask(false, map, adjacent, actors);
					//star.markRun();
					//targetPaths.put(adjacent, star.findPath());
				}
				
				closed.add(cell);
				
				//这里不能删除open已经查找过的，
				//否则后边可能有对G值的修改有影响到之前的，
				//但删除后就再也找不到之前已经遍历过的了
				//当初自作聪明删掉open导致不能及时更新G值
				//open.remove(cell);
			}
			
			descPower();
		}
		
		//System.out.println(TimeUnit.MILLISECONDS.toMicros((System.currentTimeMillis() - begin))+" MICROSECONDS");
	}

}
