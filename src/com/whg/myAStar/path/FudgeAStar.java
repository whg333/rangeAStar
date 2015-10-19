package com.whg.myAStar.path;

import java.awt.Point;

import com.whg.myAStar.TileCell;

public class FudgeAStar extends ClassicAStar{

	/** An implementation of Amit Patel's 'fudge' huristic. */
	@Override
	protected double manhattanDistance(TileCell from, TileCell to, double low){
		Point a = from.getCellPosition();
		Point b = to.getCellPosition();
		
		double dx1 = a.x - b.x;
		double dy1 = a.y - b.y;
		double dx2 = start.getCellPosition().x - b.x;
		double dy2 = start.getCellPosition().y - b.y;
		double cross = dx1 * dy2 - dx2 * dy1;
		if (cross < 0)
			cross = -cross; // absolute value

		double result = low * (Math.abs(dx1) + Math.abs(dy1) + cross * 0.0002);
		//System.out.println("Fudge a="+a+",b="+b+"r="+result);
		return result;

		// return low * (Math.abs(a.x-b.x)+Math.abs(a.y-b.y)-1);
	}
	
}
