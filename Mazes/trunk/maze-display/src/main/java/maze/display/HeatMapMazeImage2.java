package maze.display;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import maze.analysis.HeatMapper;
import maze.parent.Cell;
import maze.parent.Maze;

public class HeatMapMazeImage2 extends MazeImage2 {
	
	private Color heat = Color.GREEN;
	private HeatMapper mppr;

	public HeatMapMazeImage2(Maze maze) {
		super(maze);
		mppr = new HeatMapper(maze);
		mppr.findStart();
		mppr.analyze();
	}

	public HeatMapMazeImage2 setHeatStart(int x, int y) {
		mppr.setStart(x, y).analyze();
		return this;
	}
	
	@Override
	public IMazeImage buildImage() {
		if (this.image == null)
			buildImage(Color.BLACK, Color.WHITE);
		addHeat();
		return this;
	}
	
	private void addHeat() {
		Graphics2D g2d = image.createGraphics();
		BasicStroke bs = new BasicStroke(1);
		g2d.setStroke(bs);
		for (int i=0; i<maze.getRows(); i++) {
			for (int j=0; j<maze.getCols(); j++) {
				Cell cell = maze.getCellAt(i, j);
				if (cell == null)
					continue;
				int ulx = (cell.col)*ratio + cell.col + 1;
				int uly = image.getHeight()-((cell.row+1)*ratio + cell.row + 1);
				g2d.setColor(new Color(heat.getRed(), heat.getGreen(), heat.getBlue(), getAlpha(cell)));
				g2d.fillRect(ulx, uly, ratio, ratio);
			}
		}
	}
	
	private int getAlpha(Cell cell) {
		int heat = mppr.getHeat(cell.row, cell.col);
		double heatPct = (double)heat / (double) mppr.getMaxHeat();
		return (int) (heatPct*255);
	}
}
