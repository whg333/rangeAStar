package com.whg.AStar.pathFinder;

import java.awt.Point;

import com.whg.AStar.pathFinder.map.GridCell;

public class Fudge extends HuristicAStar {

	/** An implementation of Amit Patel's 'fudge' huristic. */
	@Override
	protected double cbDist(Point a, Point b, double low) {
		Point start = GridCell.getStartCell().getPosition();
		double dx1 = a.x - b.x;
		double dy1 = a.y - b.y;
		double dx2 = start.x - b.x;
		double dy2 = start.y - b.y;
		double cross = dx1 * dy2 - dx2 * dy1;
		if (cross < 0)
			cross = -cross; // absolute value

		double result = low * (Math.abs(dx1) + Math.abs(dy1) + cross * 0.0002);
		//System.out.println("Fudge a="+a+",b="+b+"r="+result);
		return result;
		// return low * (Math.abs(a.x-b.x)+Math.abs(a.y-b.y)-1);
	}

}