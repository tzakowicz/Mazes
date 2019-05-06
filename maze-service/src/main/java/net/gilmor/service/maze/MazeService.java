package net.gilmor.service.maze;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import maze.display.MazeImage;
import maze.parent.Maze;
import net.gilmor.service.maze.model.MazeMap;

@Path("/maze")
public class MazeService {

	@GET
	@Path("/image")
	public Response getMazeImage(
			@DefaultValue("10") @QueryParam("ratio") int ratio,
			@DefaultValue("20") @QueryParam("height") int height,
			@DefaultValue("20") @QueryParam("width") int width) {
		Maze maze = new Maze(height,width);
		maze.initGrid();
		BufferedImage img = new MazeImage(maze)
				.setRatio(ratio)
				.buildImage()
				.getImage();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(img, "bmp", baos);
		} catch (IOException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.header("Content-Type", "text/plain")
					.entity(e.getMessage())
					.build();
		}
		return Response.ok(baos.toByteArray())
				.header("Content-Type", "image/bmp")
				.build();
				
	}
	
	@GET
	@Path("/map")
	public MazeMap getMazeMap(
			@DefaultValue("20") @QueryParam("width") int width,
			@DefaultValue("20") @QueryParam("height") int height) {
		Maze maze = new Maze(width, height);
		maze.initGrid();
		MazeMap map = MazeMap.consume(maze);
		return map;
	}
}
