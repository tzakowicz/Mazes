package net.gilmor.service.maze;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import maze.algorithms.RandomLeafWalkMaze;
import maze.display.MazeImage;
import maze.parent.Maze;
import net.gilmor.service.maze.model.MazeMap;

@Path("/")
public class MazeService extends Application {
	
	@GET
	@Path("/map")
	public Response getMazeMap(
			@DefaultValue("20") @QueryParam("width") int width,
			@DefaultValue("20") @QueryParam("height") int height) {
		Maze maze = new RandomLeafWalkMaze(width, height);
		maze.initGrid();
		MazeMap map = MazeMap.consume(maze);
		Gson gson = new Gson();
		String jsonMap = gson.toJson(map);
		return Response.ok(jsonMap).build();
	}

	@GET
	@Path("/image")
	public Response getMazeImage(
			@DefaultValue("10") @QueryParam("ratio") int ratio,
			@DefaultValue("20") @QueryParam("height") int height,
			@DefaultValue("20") @QueryParam("width") int width) {
		Maze maze = new RandomLeafWalkMaze(height,width);
		maze.initGrid();
		BufferedImage img = new MazeImage(maze)
				.setRatio(ratio)
				.buildImage()
				.getImage();
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
			ImageIO.write(img, "bmp", baos);
			return Response.ok(baos.toByteArray())
					.header("Content-Type", "image/bmp")
					.build();
		} catch (IOException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.header("Content-Type", "text/plain")
					.entity(e.getMessage())
					.build();
		}
	}
}
