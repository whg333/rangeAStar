package com.whg.myAStar.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.whg.myAStar.Player;
import com.whg.myAStar.TileCell;
import com.whg.myAStar.TileMap;

public abstract class AStar extends Thread implements PathFinder {

	private static final int MAX_STEP = 1000;

	private volatile boolean isShutDown = true;

	protected TileMap map;
	protected Player player;
	protected Set<Player> actors;

	protected TileCell start;
	protected TileCell end;

	private int maxCellNum;
	protected List<TileCell> open;
	protected List<TileCell> closed;

	public void init(TileMap map, Player player, TileCell targetCell, Set<Player> actors){
		this.map = map.clone();
		this.player = player;
		this.actors = actors;
		initStartAndEnd(targetCell);
	}
	
	public void initStartAndEnd(TileCell targetCell){
		this.start = map.getStart(player);
		this.start.markStart();
		this.end = map.getEnd(targetCell);
		this.end.markEnd();

		this.maxCellNum = map.getWidth() * map.getHeight();
		this.open = new ArrayList<TileCell>(maxCellNum);
		this.closed = new ArrayList<TileCell>(maxCellNum);
	}
	
	public void markRun(){
		isShutDown = false;
	}
	
	@Override
	public void run() {
		markRun();
		Path path = findPath();
		if (path == null) {
			return;
		}
		move(path);
		markShutDown();
	}

	@Override
	public Path findPath() {
		if(start.equals(end)){
			return null;
		}
		
		start.show();
		open.add(start);

		boolean found = false;
		int step = 0;
		while (!isShutDown && !found && step < MAX_STEP) {
			found = stepFind();
			step++;
		}

		if (found) {
			Path path = shortestPath();
			player.markPath2Show(path);
			return path; // 返回最短路径
		} else {
			return null; // 返回可到达的节点中曼哈顿距离最短的节点
		}
	}
	
	/** 每一次寻路查找步骤 */
	protected abstract boolean stepFind();
	
	private void move(Path path){
		for (TileCell cell : path.getNodes()) {
			while(!player.arrivedAt(cell)){
				//System.out.println(player);
				if (isShutDown) {
					return;
				}
				player.closeTo(cell);
				slowShowMove();
			}
			cell.notPath();
			cell.notEnd();
		}
		player.idle();
		actors.remove(player);
	}
	
	private void slowShowMove(){
		try {
			TimeUnit.MILLISECONDS.sleep(player.getFrameDelay());
		} catch (InterruptedException e) {
			System.out.println("slowShowMove Interrupted...");
			shutdown();
		}
	}

	/** 缓慢展示搜索路径，包括G值 */
	protected void slowShowFindPath() {
		try {
			TimeUnit.MILLISECONDS.sleep(player.getFrameDelay()*2);
		} catch (InterruptedException e) {
			System.out.println("slowShowFindPath Interrupted...");
			shutdown();
		}
	}

	private Path shortestPath() {
		//System.out.println("Path Found");

		List<TileCell> nodes = new ArrayList<TileCell>(maxCellNum);
		nodes.add(end);
		if (start.equals(end)) {
			return new Path(nodes);
		}

		boolean finished = false;
		TileCell next;
		TileCell now = end;
		TileCell stop = start;
		//当代价为0的时候，下面的while会找不到最短（优）路径
		while (!finished) {
			next = map.getLowestAdjacent(now);
			now = next;
			if (now.equals(stop)) {
				finished = true;
			}else{
				now.markPath();
				nodes.add(0, now);
			}
		}

		//System.out.println("Done");
		return new Path(nodes);
	}

	public void shutdown() {
		//player.idle();
		markShutDown();
		interrupt();
	}
	
	public void markShutDown(){
		isShutDown = true;
	}

	public boolean isShutDown() {
		return isShutDown;
	}
	
	public boolean isHasMap(){
		return map != null;
	}

	public TileMap getMap() {
		return map;
	}
	
	public boolean isEnd(TileCell targetCell){
		return end.equals(map.getEnd(targetCell));
	}

}
