package maze.writer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

public class ImageWriter {

	public static void save(BufferedImage image) {
		String type = "bmp";
		save(image, type);
	}
	
	public static void save(BufferedImage image, String type) {
		File loc = buildFile(type);
		save(image, loc, type);
	}
	
	public static void save(BufferedImage image, File loc, String type) {
		try {
			ImageIO.write(image, type, loc);
			System.out.println("Writing file: " + loc.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static File buildFile(String type) {
		String userDir = System.getProperty("user.home");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String dateTime = sdf.format(new Date());
		File loc = new File(userDir + File.separator + String.format("maze_%s.%s", dateTime, type));
		return loc;
	}
}
