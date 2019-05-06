package maze.analysis;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Cell;
import maze.parent.Grid;

public class Pathalyzer extends Analyzer {
	
	private int endX;
	private int endY;
	
	private List<Cell> path;

	public Pathalyzer(Grid grid) {
		this.grid = grid;
		endX = grid.getRows()-1;
		endY = grid.getCols()-1;
	}
	
	public List<Cell> getPath() {
		return path;
	}
	
	public Pathalyzer setStart(int x, int y) {
		this.startX = x;
		this.startY = y;
		return this;
	}
	
	public Pathalyzer setEnd(int x, int y) {
		this.endX = x;
		this.endY = y;
		return this;
	}
	
	@Override
	public Pathalyzer analyze() {
		Cell cell = grid.getCellAt(startX, startY);
		this.path = findEnd(null, cell);
		return this;
	}

	private List<Cell> findEnd(Cell prev, Cell cell) {
		List<Cell> finalPath = new ArrayList<>();
		if (cell.row == endX && cell.col == endY) {
			finalPath.add(cell);
			return finalPath;
		}
		List<Cell> connected = getConnectedWithoutPrevious(prev, cell);
		for (Cell next : connected) {
			finalPath = findEnd(cell, next);
			if (finalPath.size() > 0) {
				finalPath.add(0, cell);
				return finalPath;
			}
		}
		return finalPath;
	}
}
