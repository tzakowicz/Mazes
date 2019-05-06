/*
 *  ©2019 Thomas Zakowicz, All rights reserved.
 */

package maze.algorithms;

import java.util.ArrayList;
import java.util.List;

import maze.masks.Mask;
import maze.parent.Cell;
import maze.parent.PlayableMaze;

public class RandomLeafWalkMaze extends PlayableMaze {

	public RandomLeafWalkMaze(int rows, int cols) {
		super(rows, cols);
		this.name = "Random Leaf Walk";
	}

	public RandomLeafWalkMaze(int rows, int cols, Mask mask) {
		super(rows, cols, mask);
		this.name = "Masked Random Leaf Walk";
	}
	
	public void initGrid() {
		int randRow = (int) Math.floor(Math.random() * rows);
		int randCol = (int) Math.floor(Math.random() * cols);
		Cell startCell = grid[randRow][randCol];
		while (startCell == null) {
			randRow = (int) Math.floor(Math.random() * rows);
			randCol = (int) Math.floor(Math.random() * cols);
			startCell = grid[randRow][randCol];
		}
		walkFrom(grid[randRow][randCol]);
	}
	
	private void walkFrom(Cell start) {
		List<Cell> walk = new ArrayList<>();
		Cell cell = start;
		while (!cell.hasBeenVisited()) {
			cell.setVisited();
			List<Cell> valid = findValid(cell, walk);
			if (valid.isEmpty()) {
				List<Cell> edges = findEdges();
				if (edges.isEmpty())
					break;
				cell = randomCell(edges);
				join(cell, findPassage(cell));
				continue;
			}
			Cell next = valid.get((int)Math.floor(Math.random() * valid.size()));
			join(cell, next);
			walk.add(cell);
			cell = next;
		}
		walk.clear();
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
