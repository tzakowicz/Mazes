package maze.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import maze.parent.PlayableMaze;

public class PlayableMazeImage extends MazeImage {
	
	private PlayableMaze maze;

	public PlayableMazeImage(PlayableMaze maze) {
		super(maze);
		this.maze = maze;
	}

	@Override
	public BufferedImage getImage() {
		BufferedImage img = super.getImage();
		Graphics g = img.getGraphics();
		g.setColor(Color.RED);
		int celBuf = Math.floorDiv(cellSize, 2);
		int xPos = maze.getPlayerRow() * cellSize + celBuf;
		int yPos = maze.getPlayerCol() * cellSize + celBuf;
		int plySize = cellSize - celBuf;
		g.drawRect(xPos, yPos, plySize, plySize);
		return img;
	}
}
