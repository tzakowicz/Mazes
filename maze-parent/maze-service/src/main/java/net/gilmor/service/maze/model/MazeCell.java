package net.gilmor.service.maze.model;

import maze.parent.Cell;

public class MazeCell {

	private int x, y;
	private int cellType;

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

	public int getCellType() {
		return cellType;
	}

	public void setCellType(int cellType) {
		this.cellType = cellType;
	}

	public static MazeCell consume(Cell cell) {
		MazeCell newCell = new MazeCell();
		newCell.x = cell.row;
		newCell.y = cell.col;
		newCell.cellType = 0;
		if (cell.north != null) newCell.cellType += 1;
		if (cell.south != null) newCell.cellType += 2;
		if (cell.east != null) newCell.cellType += 4;
		if (cell.west != null) newCell.cellType += 8;
		return newCell;
	}
}
