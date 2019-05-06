package maze.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import maze.analysis.HeatMapper;
import maze.parent.Cell;
import maze.parent.Maze;

public class HeatMapMazeImage extends MazeImage {
	
	private HeatMapper mppr;

	public HeatMapMazeImage(Maze maze) {
		super(maze);
		mppr = new HeatMapper(maze);
		mppr.findStart();
		mppr.analyze();
	}

	public HeatMapMazeImage setHeatStart(int x, int y) {
		mppr.setStart(x, y).analyze();
		return this;
	}
	
	@Override
	public MazeImage buildImage() {
		return buildImage(Color.BLACK, Color.WHITE, Color.GREEN);
	}
	
	public MazeImage buildImage(Color bg, Color fg, Color heat) {
		Dimension dim = new Dimension(maze.getCols()*ratio, maze.getRows()*ratio);
		image = new BufferedImage(dim.width+1, dim.height+1, BufferedImage.TYPE_INT_RGB);
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
				int llx = (cell.col)*ratio;
				int lly = dim.height-(cell.row)*ratio;
				int ulx = (cell.col)*ratio;
				int uly = dim.height-(cell.row+1)*ratio;
				int lrx = (cell.col+1)*ratio;
				int lry = dim.height-(cell.row)*ratio;
				int urx = (cell.col+1)*ratio;
				int ury = dim.height-(cell.row+1)*ratio;
				g2d.setColor(fg);
				g2d.fillRect(ulx, uly, ratio, ratio);
				g2d.setColor(new Color(heat.getRed(), heat.getGreen(), heat.getBlue(), getAlpha(cell)));
				g2d.fillRect(ulx, uly, ratio, ratio);
		        g2d.setColor(bg);
				if (cell.north == null) {
					g2d.drawLine(ulx, uly, urx, ury);
				}
				if (cell.south == null) {
					g2d.drawLine(llx, lly, lrx, lry);
				}
				if (cell.east == null) {
					g2d.drawLine(urx, ury, lrx, lry);
				}
				if (cell.west == null) {
					g2d.drawLine(ulx, uly, llx, lly);
				}
			}
		}
		return this;
	}
	
	private int getAlpha(Cell cell) {
		int heat = mppr.getHeat(cell.row, cell.col);
		double heatPct = (double)heat / (double) mppr.getMaxHeat();
		return (int) (heatPct*255);
	}
}
