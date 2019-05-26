package maze.display;

import java.awt.image.BufferedImage;

public interface IMazeImage {
	public BufferedImage getImage();
	public IMazeImage setRatio(int ratio);
	public IMazeImage buildImage();
}
