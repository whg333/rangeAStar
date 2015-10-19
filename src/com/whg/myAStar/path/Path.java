package com.whg.myAStar.path;

import java.util.List;

import com.whg.myAStar.TileCell;

public class Path {

	private final List<TileCell> nodes;

	public Path(List<TileCell> nodes) {
		this.nodes = nodes;
	}

	public List<TileCell> getNodes() {
		return nodes;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(int i=0;i<nodes.size();i++){
			TileCell node = nodes.get(i);
			sb.append(node.getCellPosition()+","+node.getG());
			if(i != nodes.size()-1){
				sb.append(" --> ");
			}
		}
		return sb.toString();
	}

}
