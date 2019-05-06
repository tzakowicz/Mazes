package maze.algorithms;

import maze.parent.Cell;
import maze.parent.Maze;

public class RandomLeafMaze extends Maze {

	public RandomLeafMaze(int rows, int cols) {
		super(rows, cols);
		this.name = "Random Leaf";
	}

	public void initGrid() {
		int randRow = (int) Math.floor(Math.random() * (rows-1));
		int randCol = cols - randRow;
		Cell cell = grid[randRow][randCol];
		cell.setVisited();
		while ((cell = findEdge()) != null) {
			Cell hall = findPassage(cell);
			join(cell, hall);
			cell.setVisited();
		}
	}
}
