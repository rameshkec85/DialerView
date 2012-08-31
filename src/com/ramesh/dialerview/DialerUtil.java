package com.ramesh.dialerview;

import android.graphics.Point;

public class DialerUtil {

	static public double degreesToRadians(double angle) {
		return Math.toRadians(angle);
	}

	static public double radiansToDegrees(double angle) {
		return Math.toDegrees(angle);
	}

	static public Point getPointAtCirlce(int radius, Point center, double angle) {
		angle=Math.toRadians(angle);
		int x = center.x + (int) (radius * Math.cos(angle));
		int y = center.y + (int) (radius * Math.sin(angle));
		return new Point(x, y);
	}

}
