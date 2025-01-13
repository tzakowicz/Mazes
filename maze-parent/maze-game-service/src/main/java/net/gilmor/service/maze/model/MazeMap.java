package net.gilmor.service.maze.model;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Maze;

public class MazeMap {
	
	private int width, height;
	private int playerX, playerY;
	private int finishX, finishY;
	private List<MazeCell> cells;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	protected int getPlayerX() {
		return playerX;
	}

	protected void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	protected int getPlayerY() {
		return playerY;
	}

	protected void setPlayerY(int playerY) {
		this.playerY = playerY;
	}

	protected int getFinishX() {
		return finishX;
	}

	protected void setFinishX(int finishX) {
		this.finishX = finishX;
	}

	protected int getFinishY() {
		return finishY;
	}

	protected void setFinishY(int finishY) {
		this.finishY = finishY;
	}
	
	protected List<MazeCell> getCells() {
		return cells;
	}

	protected void setCells(List<MazeCell> cells) {
		this.cells = cells;
	}

	public static MazeMap consume(Maze maze) {
		List<MazeCell> cells = new ArrayList<MazeCell>();
		for (int i=0; i<maze.getRows(); i++) {
			for (int j=0; j<maze.getCols(); j++) {
				cells.add(MazeCell.consume(maze.getCellAt(i, j)));
			}
		}
		MazeMap map = new MazeMap();
		map.setCells(cells);
		map.setWidth(maze.getCols());
		map.setHeight(maze.getRows());
		map.setPlayerX(maze.getStartRow());
		map.setPlayerY(maze.getStartCol());
		map.setFinishX(maze.getFinishRow());
		map.setFinishY(maze.getFinishCol());
		return map;
	}
}
