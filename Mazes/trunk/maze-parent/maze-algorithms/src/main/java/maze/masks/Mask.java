package maze.masks;

import java.util.ArrayList;
import java.util.List;

import maze.parent.Cell;

public class Mask {
	
	protected List<Cell> maskedCells;
	protected int rows, cols;
	
	protected List<Cell> getMaskedCells() {
		return maskedCells;
	}

	protected void setMaskedCells(List<Cell> maskedCells) {
		this.maskedCells = maskedCells;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	protected Mask addMaskedCell(int row, int col) {
		Cell cell = new Cell(row, col);
		addMaskedCell(cell);
		return this;
	}
	
	protected Mask addMaskedCell(Cell cell) {
		if (maskedCells == null)
			maskedCells = new ArrayList<>();
		if (!maskedCells.contains(cell))
			maskedCells.add(cell);
		return this;
	}

	public boolean isCellMasked(int row, int col) {
		Cell cell = new Cell(row, col);
		return isCellMasked(cell);
	}
	
	public boolean isCellMasked(Cell cell) {
		if (maskedCells == null || maskedCells.isEmpty())
			return false;
		return maskedCells.contains(cell);
	}
}
