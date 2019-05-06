package net.gilmor.service.maze.model;

import maze.parent.Maze;

public class MazeMap {
	
	private MazeCell[][] cells;
	
	protected MazeCell[][] getCells() {
		return cells;
	}

	protected void setCells(MazeCell[][] cells) {
		this.cells = cells;
	}

	public static MazeMap consume(Maze maze) {
		MazeCell[][] cells = new MazeCell[maze.getRows()][maze.getCols()];
		for (int i=0; i<maze.getRows(); i++) {
			for (int j=0; j<maze.getCols(); j++) {
				cells[j][i] = MazeCell.consume(maze.getCellAt(i, j));
			}
		}
		MazeMap map = new MazeMap();
		map.setCells(cells);
		return map;
	}
}
