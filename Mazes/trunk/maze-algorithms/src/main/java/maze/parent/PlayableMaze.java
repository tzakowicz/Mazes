package maze.parent;

import maze.masks.Mask;

public class PlayableMaze extends Maze {

	private int playerX, playerY;

	public PlayableMaze(int rows, int cols) {
		super(rows, cols);
	}

	public PlayableMaze(int rows, int cols, Mask mask) {
		super(rows, cols, mask);
	}

	public int getPlayerX() {
		return playerX;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
	}
	
	public void moveNorth() {
		moveTo(getCellAt(playerX, playerY).north);
	}
	
	public void moveSouth() {
		moveTo(getCellAt(playerX, playerY).south);
	}
	
	public void moveEast() {
		moveTo(getCellAt(playerX, playerY).east);
	}
	
	public void moveWest() {
		moveTo(getCellAt(playerX, playerY).west);
	}
	
	private void moveTo(Cell cell) {
		if (cell != null) {
			playerX = cell.row;
			playerY = cell.col;
		}
	}
	
	public boolean checkWin() {
		return (playerX == finishX && playerY == finishY);
	}
}
