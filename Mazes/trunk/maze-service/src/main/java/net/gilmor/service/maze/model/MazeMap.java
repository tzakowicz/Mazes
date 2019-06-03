package net.gilmor.service.maze.model;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Maze;
import maze.parent.PlayableMaze;

public class MazeMap {
	
	private List<MazeCell> cells;
	private int width;
	private int height;
	
	protected List<MazeCell> getCells() {
		return cells;
	}

	protected void setCells(List<MazeCell> cells) {
		this.cells = cells;
	}

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

	public static MazeMap consume(Maze maze) {
		List<MazeCell> cells = new ArrayList<MazeCell>();
		for (int i=0; i<maze.getRows(); i++) {
			for (int j=0; j<maze.getCols(); j++) {
				cells.add(MazeCell.consume(maze.getCellAt(i, j)));
			}
		}
		if (maze instanceof PlayableMaze) {
			int index = cells.stream()
				.filter(c -> c.getX() == ((PlayableMaze)maze).getPlayerX())
				.filter(c -> c.getY() == ((PlayableMaze)maze).getPlayerY())
				.map(c -> cells.indexOf(c))
				.findFirst()
				.get();
			cells.get(index).setPlayer(true);
		}
		MazeMap map = new MazeMap();
		map.setCells(cells);
		map.setWidth(maze.getCols());
		map.setHeight(maze.getRows());
		return map;
	}
}
