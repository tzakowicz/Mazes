package maze.algorithms;

import maze.parent.Cell;
import maze.parent.Maze;

public class BinaryTreeMaze extends Maze {

	public BinaryTreeMaze(int rows, int cols) {
		super(rows, cols);
		this.name = "Binary Tree";
	}

	public void initGrid() {
		for (int i=0; i<rows; i++) {
			for (int j=0; j<cols; j++) {
				Cell cell = grid[i][j];
				Cell north = getCellAt(cell.row+1, cell.col);
				Cell east = getCellAt(cell.row, cell.col+1);
				
				if (north == null && east == null)
					return;
				
				if (north == null) {
					join(cell, east);
					continue;
				} else if (east == null) {
					join(cell, north);
					continue;
				} else if (coinFlip(100, 50)) {
					join(cell, north);
				} else {
					join(cell, east);
				}
			}
		}
	}
}
