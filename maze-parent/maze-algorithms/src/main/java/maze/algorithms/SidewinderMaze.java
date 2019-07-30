package maze.algorithms;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Cell;
import maze.parent.Maze;

public class SidewinderMaze extends Maze {
	
	public SidewinderMaze(int rows, int cols) {
		super(rows, cols);
		this.name = "Sidewinder";
	}

	public void initGrid() {
		for (int i=0; i<rows; i++) {
			List<Cell> run = new ArrayList<Cell>();
			for (int j=0; j<cols; j++) {
				Cell curr = getCellAt(i, j);
				run.add(curr);
				if (i == rows-1 && j == cols-1) {
					// nothing here
				} else if (i == rows-1 || (coinFlip(150, 50) && j < cols-1)) {
					Cell east = getCellAt(curr.row, curr.col+1);
					join(curr, east);
				} else {
					Cell rise = randomCell(run);
					Cell north = getCellAt(rise.row+1, rise.col);
					join (rise, north);
					run.clear();
				}
			}
		}
	}
}
