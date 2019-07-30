package maze.parent;

import maze.masks.Mask;

public class PlayableMaze extends Maze {

	private int playerRow, playerCol;

	public PlayableMaze(int rows, int cols) {
		super(rows, cols);
	}

	public PlayableMaze(int rows, int cols, Mask mask) {
		super(rows, cols, mask);
	}

	public int getPlayerRow() {
		return playerRow;
	}

	public void setPlayerRow(int playerCol) {
		this.playerRow = playerCol;
	}

	public int getPlayerCol() {
		return playerCol;
	}

	public void setPlayerCol(int playerCol) {
		this.playerCol = playerCol;
	}
	
	public void moveNorth() {
		moveTo(getCellAt(playerRow, playerCol).north);
	}
	
	public void moveSouth() {
		moveTo(getCellAt(playerRow, playerCol).south);
	}
	
	public void moveEast() {
		moveTo(getCellAt(playerRow, playerCol).east);
	}
	
	public void moveWest() {
		moveTo(getCellAt(playerRow, playerCol).west);
	}
	
	private void moveTo(Cell cell) {
		if (cell != null) {
			playerRow = cell.row;
			playerCol = cell.col;
		}
	}
	
	public boolean checkWin() {
		return (playerRow == finishRow && playerCol == finishCol);
	}
}
