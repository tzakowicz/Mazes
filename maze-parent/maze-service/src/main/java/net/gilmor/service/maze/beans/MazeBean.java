package net.gilmor.service.maze.beans;

import java.io.Serializable;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;

import maze.algorithms.RandomLeafWalkMaze;
import maze.parent.PlayableMaze;

@Stateful
@SessionScoped
public class MazeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PlayableMaze maze;
	private long startTime;
	
	public void MazeGame() {
		startGame(20,20);
	}
	
	public PlayableMaze startGame(int height, int width) {
		maze = new RandomLeafWalkMaze(height, width);
		maze.initGrid();
		startTime = System.currentTimeMillis();
		return maze;
	}
	
	public int getPlayerX() {
		return maze.getPlayerRow();
	}
	
	public int getPlayerY() {
		return maze.getPlayerCol();
	}
	
	public int getFinishX() {
		return maze.getFinishRow();
	}
	
	public int getFinishY() {
		return maze.getFinishCol();
	}
	
	public long getPlayTime() {
		long currentTime = System.currentTimeMillis();
		return currentTime - startTime;
	}
	
	public boolean isFinished() {
		return maze.checkWin();
	}
	
	public void moveUp() {
		maze.moveNorth();
	}
	
	public void moveDown() {
		maze.moveSouth();
	}
	
	public void moveLeft() {
		maze.moveWest();
	}
	
	public void moveRight() {
		maze.moveEast();
	}
}
