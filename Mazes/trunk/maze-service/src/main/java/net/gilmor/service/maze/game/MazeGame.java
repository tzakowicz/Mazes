package net.gilmor.service.maze.game;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import maze.parent.PlayableMaze;
import net.gilmor.service.maze.beans.MazeBean;
import net.gilmor.service.maze.model.MazeMap;

@Path("/game")
public class MazeGame {

	@Inject
	private MazeBean mazeBean;
	
	@OPTIONS
	public Response getPreFlight() {
		return buildResponse()
				.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS")
				.build();
	}
	
	@GET
	public Response getGameState() {
		if (mazeBean == null)
			mazeBean = new MazeBean();
		PlayableMaze maze = mazeBean.getMaze();
		MazeMap map = MazeMap.consume(maze);
		Gson gson = new Gson();
		String jsonMap = gson.toJson(map);
		return buildResponse(jsonMap)
				.header("Content-Type", MediaType.APPLICATION_JSON)
				.build();
	}
	
	@GET
	@Path("/new")
	public Response startNewGame(@DefaultValue("20") @QueryParam("width") int width,
			@DefaultValue("20") @QueryParam("height") int height) {
		mazeBean.startGame(width, height);
		return postResponse();
	}

	@GET
	@Path("/u")
	public Response moveUp() {
		mazeBean.moveUp();
		return postResponse();
	}

	@GET
	@Path("/d")
	public Response moveDown() {
		mazeBean.moveDown();
		return postResponse();
	}

	@GET
	@Path("/l")
	public Response moveLeft() {
		mazeBean.moveLeft();
		return postResponse();
	}

	@GET
	@Path("/r")
	public Response moveRight() {
		mazeBean.moveRight();
		return postResponse();
	}
	
	private Response postResponse() {
		try {
			return buildResponse()
					.build();
		} catch (Exception e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR)
					.entity("Exception: " + e.getMessage())
					.header("Content-Type", "text/plain")
					.build();
		}
	}
	
	private ResponseBuilder buildResponse(String content) {
		return buildResponse()
				.entity(content);
	}
	
	private ResponseBuilder buildResponse() {
		return Response.ok()
				.header("Access-Control-Allow-Credentials", "true");
	}
}
