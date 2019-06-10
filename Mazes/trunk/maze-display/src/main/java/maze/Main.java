package maze;

import java.awt.image.BufferedImage;

import maze.algorithms.RandomLeafWalkMaze;
import maze.display.HeatMapMazeImage;
import maze.display.MazeImage;
import maze.parent.Maze;
import maze.writer.ImageWriter;

public class Main {

	public static void main(String[] args) {
		int rows = 100;
		int cols = 100;
//		Maze btm = new BinaryTreeMaze(rows, cols);
//		makeImage(btm);
//		Maze swm = new SidewinderMaze(rows, cols);
//		makeImage(swm);
//		Maze bwm = new BrokenWalkMaze(rows, cols);
//		makeImage(bwm);
//		Maze rlm = new RandomLeafMaze(rows, cols);
//		makeImage(rlm);
		Maze rlwm = new RandomLeafWalkMaze(rows, cols);
		makeImage(rlwm);
//		for (int i=0; i<10; i++)
		
//		int size = 10;
//		Mask mask = null;
//		try {
//			mask = new TextMask("FileMask2.txt");
//			mask = new BitmapMask("BitmapMask4.bmp");
//			Mask submask = new MazeMask(size, size);
//			mask = new MazeMask(submask);
//			mask = new MazeMask(size, size);
//		} catch (Exception e) {
//			System.err.println("Unable to create Mask: " + e.getMessage());
//		}
//		Maze maze = new RandomLeafWalkMaze(mask.getRows(), mask.getCols(), mask);
//		makeImage(maze);
	}
	
	private static void makeImage(Maze maze) {
		maze.initGrid();
		MazeImage disp;
//		disp = new MazeImage2(maze);
		disp = new HeatMapMazeImage(maze).setHeatStart(maze.getRows()/2, maze.getCols()/2);
//		disp = new PathMazeImage(maze);
//		disp = new HeatPathMazeImage(maze).setHeatStart(maze.getRows()/2, maze.getCols()/2);
		disp.setRatio(5);
		BufferedImage image = disp.getImage();
		ImageWriter.save(image);
	}
	
}
