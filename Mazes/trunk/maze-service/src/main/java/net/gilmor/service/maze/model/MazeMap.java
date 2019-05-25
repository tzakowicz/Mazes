package net.gilmor.service.maze.model;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Maze;

public class MazeMap {
	
	private List<MazeCell> cells;
	
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
		return map;
	}
}
