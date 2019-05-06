package maze.analysis;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Cell;
import maze.parent.Grid;

public class LongestPathalyzer {
	
	private List<Cell> longestPath;
	
	public LongestPathalyzer(Grid grid) {
		analyze(grid);
	}
	
	public List<Cell> getLongestPath() {
		return longestPath;
	}

	private void analyze(Grid grid) {
		for (int i=0; i<grid.getRows(); i++) {
			for (int j=0; j<grid.getCols(); j++) {
				List<Cell> analyzedPath = analyze(null, grid.getCellAt(i, j));
				if (longestPath == null || analyzedPath.size() > longestPath.size())
					longestPath = analyzedPath;
			}
		}
	}
	
	private List<Cell> analyze(Cell prev, Cell cell) {
		List<Cell> connected = getConnectedWithoutPrevious(prev, cell);
		List<Cell> longest = new ArrayList<>();
		for (Cell curr : connected) {
			List<Cell> analyzedPath = analyze(cell, curr);
			if (analyzedPath.size() > longest.size())
				longest = analyzedPath;
		}
		longest.add(cell);
		return longest;
	}
	
	private List<Cell> getConnectedWithoutPrevious(Cell prev, Cell cell) {
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
