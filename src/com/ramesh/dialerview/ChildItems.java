package com.ramesh.dialerview;

import java.util.Comparator;

public class ChildItems implements Comparable<ChildItems>,
		Comparator<ChildItems> {

	private int height;
	private int width;
	private int currentX;
	private int currentY;
	private double currentAngle;
	private int position;

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public double getCurrentAngle() {
		return currentAngle;
	}

	public void setCurrentAngle(double currentAngle) {
		this.currentAngle = currentAngle;
	}

	@Override
	public int compareTo(ChildItems another) {

		if (this.currentAngle == another.getCurrentAngle())
			return 0;
		else if (this.currentAngle > another.getCurrentAngle())
			return 1;
		else
			return -1;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int compare(ChildItems c1, ChildItems c2) {
		int a1 = (int) c1.getCurrentAngle();
		if (a1 > 180)
			a1 = 360 - a1;
		int a2 = (int) c2.getCurrentAngle();
		if (a2 > 180)
			a2 = 360 - a2;
		return (a1 - a2);
	}

}
