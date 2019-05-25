package maze.analysis;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Cell;
import maze.parent.Grid;

public class Analyzer {
	
	protected int startX = 0;
	protected int startY = 0;
	
	protected Grid grid;
	
	public Analyzer setStart(int x, int y) {
		this.startX = x;
		this.startY = y;
		return this;
	}
	
	public Analyzer findStart() {
		while (grid.getCellAt(startX, startY) == null) {
			startY++;
			if (startY > grid.getCols()) {
				startX++;
				startY = 0;
			}
		}
		return this;
	}
	
	public Analyzer analyze() {
		return this;
	}
	
	protected List<Cell> getConnectedWithoutPrevious(Cell prev, Cell cell) {
		List<Cell> connectedWithoutPrevious = new ArrayList<>();
		if (cell.north != null && cell.north != prev) {
			connectedWithoutPrevious.add(cell.north);
		}
		if (cell.south != null && cell.south != prev) {
			connectedWithoutPrevious.add(cell.south);
		}
		if (cell.east != null && cell.east != prev) {
			connectedWithoutPrevious.add(cell.east);
		}
		if (cell.west != null && cell.west != prev) {
			connectedWithoutPrevious.add(cell.west);
		}
		return connectedWithoutPrevious;
	}
}
