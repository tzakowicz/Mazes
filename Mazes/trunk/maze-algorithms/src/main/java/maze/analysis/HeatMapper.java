package maze.analysis;

import java.util.List;

import maze.parent.Cell;
import maze.parent.Grid;

public class HeatMapper extends Analyzer {
	
	private int[][] heatMap;
	private int maxHeat;

	public HeatMapper(Grid grid) {
		this.grid = grid;
	}
	
	public int getHeat(int row, int col) {
		return heatMap[row][col];
	}

	public int getMaxHeat() {
		return maxHeat;
	}
	
	@Override
	public HeatMapper analyze() {
		heatMap = new int[grid.getRows()][grid.getCols()];
		maxHeat = 0;
		Cell cell = grid.getCellAt(startX, startY);
		mapHeat(null, cell, 0);
		return this;
	}
	
	private void mapHeat(Cell prev, Cell cell, int heat) {
		if (cell == null)
			return;
		heatMap[cell.row][cell.col] = heat;
		if (heat > maxHeat)
			maxHeat = heat;
		List<Cell> connectedWithoutPrevious = getConnectedWithoutPrevious(prev, cell);
		for (Cell next : connectedWithoutPrevious) {
			if (!next.equals(prev))
				mapHeat(cell, next, heat+1);
		}
	}
}
