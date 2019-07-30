package maze.algorithms;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Cell;
import maze.parent.Maze;

public class BrokenWalkMaze extends Maze {

	public BrokenWalkMaze(int rows, int cols) {
		super(rows, cols);
		this.name = "Broken Walk";
	}

	public void initGrid() {
		List<Cell> walk = new ArrayList<>();
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				Cell cell = grid[i][j];
				while (!cell.hasBeenVisited()) {
					cell.setVisited();
					List<Cell> valid = findValid(cell, walk);
					if (valid.isEmpty())
						break;
					Cell next = valid.get((int)Math.floor(Math.random() * valid.size()));
					join(cell, next);
					walk.add(cell);
					cell = next;
				}
				walk.clear();
			}
		}
	}

	private List<Cell> findValid(Cell cell, List<Cell> walk) {
		List<Cell> valid = new ArrayList<>();
		Cell north = getCellAt(cell.row+1, cell.col);
		Cell south = getCellAt(cell.row-1, cell.col);
		Cell east = getCellAt(cell.row, cell.col+1);
		Cell west = getCellAt(cell.row, cell.col-1);
		if (north != null && !walk.contains(north)) {
			valid.add(north);
		}
		if (south != null && !walk.contains(south)) {
			valid.add(south);
		}
		if (east != null && !walk.contains(east)) {
			valid.add(east);
		}
		if (west != null && !walk.contains(west)) {
			valid.add(west);
		}
		return valid;
	}
	
}
