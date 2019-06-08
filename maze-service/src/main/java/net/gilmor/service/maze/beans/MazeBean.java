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
	
	public void MazeGame() {
		startGame(20,20);
	}
	
	public void startGame(int x, int y) {
		maze = new RandomLeafWalkMaze(x, y);
		maze.initGrid();
	}
	
	public PlayableMaze getMaze() {
		if (maze == null)
			startGame(20,20);
		return maze;
	}
	
	public int getPlayerX() {
		return maze.getPlayerX();
	}
	
	public int getPlayerY() {
		return maze.getPlayerY();
	}
	
	public int getFinishX() {
		return maze.getFinishX();
	}
	
	public int getFinishY() {
		return maze.getFinishY();
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
