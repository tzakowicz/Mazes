package maze.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import maze.parent.Cell;

public class WeightedCellList {

	private List<Cell> cells = new ArrayList<>();
	private Map<Cell, Integer> cellWeights = new HashMap<>();
	
	private int large = 5;
	private int small = 1;
	
	public WeightedCellList(Cell curr, List<Cell> unvisited) {
		cells = unvisited;
		Cell prev = null;
		if (curr.north != null) {
			prev = curr.north;
		} else if (curr.south != null) {
			prev = curr.south;
		} else if (curr.east != null) {
			prev = curr.east;
		} else if (curr.west != null) {
			prev = curr.west;
		}
		if (prev == null)
			prev = new Cell(0, 0);
		for (Cell cell : unvisited) {
			if (isOpposite(prev, cell))
				addCell(cell, small);
			else
				addCell(cell, large);
		};
	}
	
	private boolean isOpposite(Cell cell1, Cell cell2) {
		if ((cell1.row == cell2.row && Math.abs(cell1.col - cell2.col) == 2) ||
			(cell1.col == cell2.col && Math.abs(cell1.row - cell2.row) == 2))
			return true;
		return false;
	}
	
	public void addCell(Cell cell, Integer weight) {
		cellWeights.put(cell, weight);
	}
	
	public Integer getWeight(Cell cell) {
		return cellWeights.get(cell);
	}
	
	public boolean isEmpty() {
		return cells.isEmpty();
	}
	
	public Cell getCell() {
		List<Cell> weightedCells = new ArrayList<>();
		cells.forEach(cell -> {
			int weight = getWeight(cell);
			for (int i=0; i< weight; i++) {
				weightedCells.add(cell);
			}
		});
		int rand = (int) Math.round(Math.random() * (weightedCells.size() - 1));
		return weightedCells.get(rand);
	}
}
