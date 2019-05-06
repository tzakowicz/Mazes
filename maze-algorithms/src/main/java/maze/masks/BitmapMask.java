package maze.masks;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class BitmapMask extends Mask {
	
	private int hdrSize = 14;
	private int bite = 3;

	public BitmapMask(String fileName) throws IOException {
		try {
			URI fileUri = getClass().getResource(fileName).toURI();
			Path filePath = Paths.get(fileUri);
			readFile(filePath.toFile());
		} catch (URISyntaxException e) {
			throw new IOException("Unable to read URI for file: " + e.getMessage());
		}
	}

	public BitmapMask(File file) throws IOException {
		readFile(file);
	}
	
	public BitmapMask(BufferedImage image) throws IOException {
		readImage(image);
	}
	
	private void readFile(File file) throws IOException {
		BufferedImage image = ImageIO.read(file);
		readImage(image);
	}
	
	private void readImage(BufferedImage image) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "bmp", baos);
		byte[] bytes = baos.toByteArray();
		readSize(bytes);
		readMask(bytes);
	}

	private void readSize(byte[] image) throws IOException {
		hdrSize = unsignedByteToInt(image[10]);
		byte[] header = Arrays.copyOf(image, hdrSize);
//		System.out.println(image.length + " - " + printBytes(header));
		setRows(unsignedByteToInt(header[18]));
		setCols(unsignedByteToInt(header[22]));
	}

	private void readMask(byte[] image) throws IOException {
		int rowCount = 0;
		int colCount = 0;
		for (int i=hdrSize; i<image.length; i+=bite) {
			byte[] b = Arrays.copyOfRange(image, i, i+bite);
			int red = unsignedByteToInt(b[0]);
			int grn = unsignedByteToInt(b[1]);
			int blu = unsignedByteToInt(b[2]);
			if (red == 0 && grn == 0 && blu == 0) {
				addMaskedCell(rowCount, colCount);
			}
			if (colCount++>=this.cols) {
				colCount = 0;
				rowCount++;
			}
		}
	}
	
//	private String printBytes(byte[] b) {
//		StringBuilder bldr = new StringBuilder();
//		for (int i=0; i<b.length; i++) {
//			if (i!=0)
//				bldr.append(", ");
//			bldr.append(unsignedByteToInt(b[i]));
//		}
//		return bldr.toString();
//	}

	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
	}
}
