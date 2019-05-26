package maze.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import maze.analysis.HeatMapper;
import maze.parent.Cell;
import maze.parent.Maze;

public class HeatMapMazeImage extends MazeImage {
	
	private Color heat = Color.GREEN;
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
	public IMazeImage buildImage() {
		super.buildImage();
		Graphics2D g2d = image.createGraphics();
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
		g2d.setColor(getHeat(cell));
		g2d.fillRect(ulx, uly, ratio, ratio);
	}
	
	private void writeBorders(Cell cell) {
		int ulx = (cell.col)*ratio + cell.col + 1;
		int uly = image.getHeight()-((cell.row+1)*ratio + cell.row + 1);
		Graphics2D g2d = image.createGraphics();
		g2d.setColor(getHeat(cell));
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
	
	private Color getHeat(Cell cell) {
		return new Color(heat.getRed(), heat.getGreen(), heat.getBlue(), getAlpha(cell));
	}
	
	private int getAlpha(Cell cell) {
		int heat = mppr.getHeat(cell.row, cell.col);
		double heatPct = (double)heat / (double) mppr.getMaxHeat();
		return (int) (heatPct*255);
	}
}
