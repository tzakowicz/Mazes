package maze.parent;

import java.util.ArrayList;
import java.util.List;

import maze.masks.Mask;

public class Maze implements Grid {

	protected String name;

	protected int rows, cols;
	protected Cell[][] grid;
	protected int startX, startY;
	protected int finishX, finishY;

	public Maze(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		initGrid(null);
		findStart();
		findFinish();
	}

	public Maze(int rows, int cols, Mask mask) {
		this.rows = rows;
		this.cols = cols;
		initGrid(mask);
		findStart();
		findFinish();
	}
	
	public int getStartX() {
		return startX;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public void setStartY(int startY) {
		this.startY = startY;
	}
	
	public int getFinishX() {
		return finishX;
	}
	
	public void setFinishX(int finishX) {
		this.finishX = finishX;
	}
	
	public int getFinishY() {
		return finishY;
	}
	
	public void setFinishY(int finishY) {
		this.finishY = finishY;
	}

	@Override
	public int getRows() {
		return rows;
	}

	@Override
	public int getCols() {
		return cols;
	}

	@Override
	public Cell getCellAt(int x, int y) {
		if (x >= 0 && x < rows) {
			if (y >= 0 && y < cols) {
				return grid[x][y];
			}
		}
		return null;
	}
	
	public void initGrid() {};

	protected void initGrid(Mask mask) {
		grid = new Cell[rows][cols];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (mask == null || !mask.isCellMasked(i, j))
					grid[i][j] = new Cell(i, j);
			}
		}
	}

	private void findStart() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (getCellAt(i, j) != null) {
					startX = i;
					startY = j;
					return;
				}
			}
		}
	}
	
	private void findFinish() {
		for (int i = rows-1; i > 0; i--) {
			for (int j = cols-1; j >= 0; j--) {
				if (getCellAt(i, j) != null) {
					finishX = i;
					finishY = j;
					return;
				}
			}
		}
	}

	protected boolean coinFlip(int tot, int mid) {
		return (Math.round(Math.random() * tot) > mid);
	}

	protected void join(Cell cell, Cell next) {
		if (cell.row < next.row) {
			cell.north = next;
			next.south = cell;
		} else if (cell.row > next.row) {
			cell.south = next;
			next.north = cell;
		} else if (cell.col < next.col) {
			cell.east = next;
			next.west = cell;
		} else {
			cell.west = next;
			next.east = cell;
		}
	}

	protected Cell randomCell(List<Cell> cells) {
		if (cells.size() == 0)
			return null;
		int rand = (int) Math.round(Math.random() * (cells.size() - 1));
		return cells.get(rand);
	}

	protected Cell findEdge() {
		return randomCell(findEdges());
	}

	protected List<Cell> findEdges() {
		List<Cell> edges = new ArrayList<>();

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				Cell cell = grid[i][j];
				if (cell == null || cell.hasBeenVisited())
					continue;
				if (isAnEdge(cell))
					edges.add(cell);
			}
		}

		return edges;
	}

	protected boolean isAnEdge(Cell cell) {
		Cell north = getCellAt(cell.row + 1, cell.col);
		Cell south = getCellAt(cell.row - 1, cell.col);
		Cell east = getCellAt(cell.row, cell.col + 1);
		Cell west = getCellAt(cell.row, cell.col - 1);
		if ((north != null && north.hasBeenVisited()) || (south != null && south.hasBeenVisited())
				|| (east != null && east.hasBeenVisited()) || (west != null && west.hasBeenVisited()))
			return true;
		return false;
	}

	protected Cell findPassage(Cell cell) {
		return randomCell(findPassages(cell));
	}

	protected List<Cell> findPassages(Cell cell) {
		List<Cell> passages = new ArrayList<>();
		Cell north = getCellAt(cell.row + 1, cell.col);
		Cell south = getCellAt(cell.row - 1, cell.col);
		Cell east = getCellAt(cell.row, cell.col + 1);
		Cell west = getCellAt(cell.row, cell.col - 1);
		if (north != null && north.hasBeenVisited())
			passages.add(north);
		if (south != null && south.hasBeenVisited())
			passages.add(south);
		if (east != null && east.hasBeenVisited())
			passages.add(east);
		if (west != null && west.hasBeenVisited())
			passages.add(west);
		return passages;
	}

	@Override
	public String toString() {
		StringBuilder bldr = new StringBuilder();
		bldr.append("Algorithm: " + name).append("\r\n");
		for (int i = rows - 1; i >= 0; i--) {
			bldr.append(prepareTopBoundry(i));
			bldr.append(prepareCellContents(i));
		}
		bldr.append(prepareBottomBoundary());
		return bldr.toString();
	}

	private String prepareTopBoundry(int i) {
		StringBuilder bldr = new StringBuilder();
		for (int j = 0; j < cols; j++) {
			Cell cell = getCellAt(i, j);
			Cell west = getCellAt(i, j - 1);
			Cell north = getCellAt(i + 1, j);
			if (cell == null) {
				if (west != null || north != null)
					bldr.append("+");
				else
					bldr.append(" ");
				if (north != null)
					bldr.append("---");
				else
					bldr.append("   ");
			} else {
				bldr.append("+");
				if (cell.north == null || i == rows - 1)
					bldr.append("---");
				else
					bldr.append("   ");
				if (j == cols - 1)
					bldr.append("+");
			}
		}
		bldr.append("\r\n");
		return bldr.toString();
	}

	private String prepareCellContents(int i) {
		StringBuilder bldr = new StringBuilder();
		for (int j = 0; j < cols; j++) {
			Cell cell = getCellAt(i, j);
			Cell west = getCellAt(i, j - 1);
			Cell north = getCellAt(i + 1, j);
			if (cell == null) {
				if (west != null)
					bldr.append("|");
				else
					bldr.append(" ");
				if (north != null)
					bldr.append("   ");
				else
					bldr.append("   ");
			} else {
				if (cell.west == null)
					bldr.append("|");
				else
					bldr.append(" ");
				if (cell.north != null)
					bldr.append("   ");
				else
					bldr.append("   ");
				if (j == cols - 1)
					bldr.append("|");
			}
		}
		bldr.append("\r\n");
		return bldr.toString();
	}

	private String prepareBottomBoundary() {
		StringBuilder bldr = new StringBuilder();
		int i = 0;
		for (int j = 0; j < cols; j++) {
			Cell cell = getCellAt(i, j);
			Cell west = getCellAt(i, j - 1);
			if (cell == null) {
				if (west != null)
					bldr.append("+");
				else
					bldr.append(" ");
				bldr.append("   ");
			} else {
				bldr.append("+---");
				if (j == cols - 1)
					bldr.append("+");
			}
		}
		bldr.append("\r\n");
		return bldr.toString();
	}
}
