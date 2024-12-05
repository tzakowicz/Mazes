package net.gilmor.service.maze;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import maze.algorithms.RandomLeafWalkMaze;
import maze.display.MazeImage;
import maze.parent.Maze;
import net.gilmor.service.maze.model.MazeMap;

@Path("/")
public class MazeService {

    @GET
    @Path("/map")
    public Response getMazeMap(@DefaultValue("20") @QueryParam("width") int width,
            @DefaultValue("20") @QueryParam("height") int height) {
        try {
            Maze maze = buildMaze(width, height);
            MazeMap map = MazeMap.consume(maze);
            Gson gson = new Gson();
            String jsonMap = gson.toJson(map);
            return Response.ok(jsonMap).build();
        } catch (Exception e) {
            return handleError(e);
        }
    }

    @GET
    @Path("/image")
    public Response getMazeImage(@DefaultValue("10") @QueryParam("cellSize") int cellSize,
            @DefaultValue("20") @QueryParam("height") int height, @DefaultValue("20") @QueryParam("width") int width) {

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            Maze maze = buildMaze(height, width);
            BufferedImage img = buildMazeImage(maze, cellSize);
            ImageIO.write(img, "bmp", baos);
            return Response.ok(baos.toByteArray()).header("Content-Type", "image/bmp").build();
        } catch (Exception e) {
            return handleError(e);
        }
    }

    private Maze buildMaze(int height, int width) throws Exception {
        if (width > 0 && width <= 200 && height > 0 && height <= 200) {
            Maze maze = new RandomLeafWalkMaze(height, width);
            maze.initGrid();
            return maze;
        }
        throw new IndexOutOfBoundsException("Height and Width must be between 1 and 200.");
    }

    private BufferedImage buildMazeImage(Maze maze, int cellSize) throws Exception {
        if (cellSize > 0 && cellSize <= 20)
            return new MazeImage(maze).setCellSize(cellSize).getImage();
        throw new IndexOutOfBoundsException("Ratio must be between 1 and 20");
    }

    private Response handleError(Exception e) {
        if (e instanceof IndexOutOfBoundsException) {
            return Response.status(Status.BAD_REQUEST).header("Content-Type", "text/plain")
                    .entity("Bad Request: " + e.getMessage()).build();
        } else {
            return Response.status(Status.INTERNAL_SERVER_ERROR).header("Content-Type", "text/plain")
                    .entity("Server Error: " + e.getMessage()).build();
        }
    }
}
