package net.gilmor.service.maze.model;

import maze.parent.Cell;

public class MazeCell {

	private int
			x = 20,
			y = 20;
	private boolean
			north = false,
			south = false,
			east = false,
			west = false;

	protected int getX() {
		return x;
	}

	protected void setX(int x) {
		this.x = x;
	}

	protected int getY() {
		return y;
	}

	protected void setY(int y) {
		this.y = y;
	}

	protected boolean isNorth() {
		return north;
	}

	protected void setNorth(boolean north) {
		this.north = north;
	}

	protected boolean isSouth() {
		return south;
	}

	protected void setSouth(boolean south) {
		this.south = south;
	}

	protected boolean isEast() {
		return east;
	}

	protected void setEast(boolean east) {
		this.east = east;
	}

	protected boolean isWest() {
		return west;
	}

	protected void setWest(boolean west) {
		this.west = west;
	}
	
	public static MazeCell consume(Cell cell) {
		MazeCell newCell = new MazeCell();
		newCell.x = cell.row;
		newCell.y = cell.col;
		newCell.north = cell.north != null;
		newCell.south = cell.south != null;
		newCell.east = cell.east != null;
		newCell.west = cell.west != null;
		return newCell;
	}
}
