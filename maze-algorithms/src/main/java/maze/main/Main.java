package maze.main;

import maze.algorithms.RandomLeafWalkMaze;
import maze.parent.Maze;

public class Main {

	public static void main(String[] args) {
		Maze maze = new RandomLeafWalkMaze(50, 50);
		maze.initGrid();
		System.out.println(maze.toString());
	}
}
