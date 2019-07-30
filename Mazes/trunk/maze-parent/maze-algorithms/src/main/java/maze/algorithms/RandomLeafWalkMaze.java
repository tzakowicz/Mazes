/*
 *  ©2019 Thomas Zakowicz, All rights reserved.
 */

package maze.algorithms;

import java.util.ArrayList;
import java.util.List;

import maze.masks.Mask;
import maze.model.WeightedCellList;
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
	
	@Override
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
		while (cell != null) {
			cell.setVisited();
			List<Cell> unvisited = findUnvisitedAndAdjacent(cell);
			if (unvisited.isEmpty()) {
				cell = findNewStart(walk);
				continue;
			}
			WeightedCellList weightedCells = new WeightedCellList(cell, unvisited);
			Cell next = weightedCells.getCell();
			join(cell, next);
			walk.add(cell);
			cell = next;
		}
		walk.clear();
	}

	private List<Cell> findUnvisitedAndAdjacent(Cell cell) {
		List<Cell> unvisited = new ArrayList<>();
		Cell north = getCellAt(cell.row+1, cell.col);
		Cell south = getCellAt(cell.row-1, cell.col);
		Cell east = getCellAt(cell.row, cell.col+1);
		Cell west = getCellAt(cell.row, cell.col-1);
		if (north != null && !north.hasBeenVisited()) {
			unvisited.add(north);
		}
		if (south != null && !south.hasBeenVisited()) {
			unvisited.add(south);
		}
		if (east != null && !east.hasBeenVisited()) {
			unvisited.add(east);
		}
		if (west != null && !west.hasBeenVisited()) {
			unvisited.add(west);
		}
		return unvisited;
	}

	private Cell findNewStart(List<Cell> walk) {
		Cell cell;
		do {
			walk.remove(walk.size()-1);
			if (walk.size() == 0) {
				List<Cell> edges = findEdges();
				if (edges.isEmpty())
					return null;
				cell = randomCell(edges);
				Cell newCell = findPassage(cell);
				join(cell, newCell);
				return newCell;
			}
			cell = walk.get(walk.size()-1);
		} while (findUnvisitedAndAdjacent(cell).isEmpty());
		return cell;
	}
}
