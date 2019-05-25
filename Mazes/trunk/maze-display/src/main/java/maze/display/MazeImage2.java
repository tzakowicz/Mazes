package maze.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import maze.parent.Cell;
import maze.parent.Maze;

public class MazeImage2 extends MazeImage {
	
	public MazeImage2(Maze maze) {
		super(maze);
	}

	@Override
	public IMazeImage buildImage(Color bg, Color fg) {
		int rows = maze.getRows();
		int cols = maze.getCols();
		int dimX = (cols * ratio) + cols + 1;
		int dimY = (rows * ratio) + rows + 1;
		
		Dimension dim = new Dimension(dimX, dimY);
		image = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = image.createGraphics();
	    g2d.setColor(bg);
	    g2d.fillRect(0, 0, dim.width, dim.height);
		BasicStroke bs = new BasicStroke(1);
		g2d.setStroke(bs);
		for (int i=0; i<maze.getRows(); i++) {
			for (int j=0; j<maze.getCols(); j++) {
				Cell cell = maze.getCellAt(i, j);
				if (cell == null)
					continue;
				int ulx = (cell.col)*ratio + cell.col + 1;
				int uly = dim.height-((cell.row+1)*ratio + cell.row + 1);
				g2d.setColor(fg);
				g2d.fillRect(ulx, uly, ratio, ratio);
			}
		}
		return this;
	}

	public Color randomColor() {
		int r = (int) Math.floor(Math.random() * 256);
		int g = (int) Math.floor(Math.random() * 256);
		int b = (int) Math.floor(Math.random() * 256);
		return new Color(r, g, b);
	}
}
