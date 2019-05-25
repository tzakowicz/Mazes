package maze.masks;

import java.awt.image.BufferedImage;
import java.io.IOException;

import maze.algorithms.RandomLeafWalkMaze;
import maze.display.HeatMapMazeImage;
import maze.display.MazeImage;
import maze.parent.Maze;

public class MazeMask extends Mask {

	public MazeMask(int rows, int cols) {
		Maze maze = new RandomLeafWalkMaze(rows, cols);
		initMask(maze);
	}
	
	public MazeMask(Mask mask) {
		Maze maze = new RandomLeafWalkMaze(mask.getRows(), mask.getCols(), mask);
		initMask(maze);
	}
	
	private void initMask(Maze maze) {
		maze.initGrid();
		MazeImage disp = new HeatMapMazeImage(maze);
		BufferedImage image = disp.getImage();
		try {
			Mask mask = new BitmapMask(image);
			this.setRows(mask.getRows());
			this.setCols(mask.getCols());
			this.setMaskedCells(mask.getMaskedCells());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
