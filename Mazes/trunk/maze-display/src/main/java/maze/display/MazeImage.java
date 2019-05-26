package maze.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import maze.parent.Cell;
import maze.parent.Maze;

public class MazeImage implements IMazeImage {
	
	protected BufferedImage image;
	protected int ratio = 10;
	
	protected Maze maze;
	
	protected Color background = Color.BLACK;
	protected Color foreground = Color.WHITE;
	
	public MazeImage(Maze maze) {
		this.maze = maze;
	}

	@Override
	public BufferedImage getImage() {
		if (image == null)
			buildImage();
		return image;
	}

	@Override
	public IMazeImage setRatio(int ratio) {
		this.ratio = ratio;
		return this;
	}

	@Override
	public IMazeImage buildImage() {
		Dimension dim = new Dimension(
				(maze.getCols() * ratio) + maze.getCols() + 1,
				(maze.getRows() * ratio) + maze.getRows() + 1);
		image = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
	    g2d.setColor(background);
	    g2d.fillRect(0, 0, dim.width, dim.height);
		BasicStroke bs = new BasicStroke(1);
		g2d.setStroke(bs);
		for (int i=0; i<maze.getRows(); i++) {
			for (int j=0; j<maze.getCols(); j++) {
				Cell cell = maze.getCellAt(i, j);
				if (cell == null)
					continue;
				writeCell(cell);
				writeBorders(cell);
			}
		}
		return this;
	}
	
	private void writeCell(Cell cell) {
		int ulx = (cell.col)*ratio + cell.col + 1;
		int uly = image.getHeight()-((cell.row+1)*ratio + cell.row + 1);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(foreground);
		g2d.fillRect(ulx, uly, ratio, ratio);
	}
	
	private void writeBorders(Cell cell) {
		int ulx = (cell.col)*ratio + cell.col + 1;
		int uly = image.getHeight()-((cell.row+1)*ratio + cell.row + 1);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(foreground);
		if (cell.north != null) {
			g2d.drawLine(ulx, uly-1, ulx+ratio-1, uly-1);
		}
		if (cell.south != null) {
			g2d.drawLine(ulx, uly+ratio, ulx+ratio-1, uly+ratio);
		}
		if (cell.east != null) {
			g2d.drawLine(ulx+ratio+1, uly, ulx+ratio+1, uly+ratio-1);
		}
		if (cell.west != null) {
			g2d.drawLine(ulx-1, uly, ulx-1, uly+ratio-1);
		}
	}
}
