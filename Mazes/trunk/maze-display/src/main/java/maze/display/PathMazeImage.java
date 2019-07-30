package maze.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import maze.analysis.Pathalyzer;
import maze.parent.Cell;
import maze.parent.Maze;

public class PathMazeImage extends MazeImage {
	
	private Pathalyzer pthlzr;
	
	private Color bg = Color.BLACK;
	private Color fg = Color.WHITE;
	private Color path = Color.GREEN;

	public PathMazeImage(Maze maze) {
		super(maze);
		pthlzr = new Pathalyzer(maze);
		pthlzr.analyze();
	}
	
	@Override
	public IMazeImage buildImage() {
		super.buildImage();
		for (Cell cell : pthlzr.getPath()) {
			writeCell(cell);
			writeBorders(cell);
		}
		return this;
	}
	
	private void writeCell(Cell cell) {
		int ulx = (cell.col)*cellSize + cell.col + 1;
		int uly = image.getHeight()-((cell.row+1)*cellSize + cell.row + 1);
		Graphics2D g2d = image.createGraphics();
		if (pthlzr.getPath().contains(cell))
			g2d.setColor(path);
		g2d.fillRect(ulx, uly, cellSize, cellSize);
	}
	
	private void writeBorders(Cell cell) {
		int ulx = (cell.col)*cellSize + cell.col + 1;
		int uly = image.getHeight()-((cell.row+1)*cellSize + cell.row + 1);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(path);
		if (cell.north != null) {
			g2d.drawLine(ulx, uly-1, ulx+cellSize-1, uly-1);
		}
		if (cell.south != null) {
			g2d.drawLine(ulx, uly+cellSize, ulx+cellSize-1, uly+cellSize);
		}
		if (cell.east != null) {
			g2d.drawLine(ulx+cellSize+1, uly, ulx+cellSize+1, uly+cellSize-1);
		}
		if (cell.west != null) {
			g2d.drawLine(ulx-1, uly, ulx-1, uly+cellSize-1);
		}
	}
}
