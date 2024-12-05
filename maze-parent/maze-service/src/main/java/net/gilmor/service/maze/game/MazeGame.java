package net.gilmor.service.maze.game;

import com.google.gson.Gson;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import maze.parent.PlayableMaze;
import net.gilmor.service.maze.beans.MazeBean;
import net.gilmor.service.maze.model.MazeMap;
import net.gilmor.service.maze.model.MazePosition;

@Path("/game")
public class MazeGame {

    @Inject
    private MazeBean mazeBean;

    @OPTIONS
    public Response getPreFlight() {
        return Response.ok().header("Access-Control-Allow-Methods", "GET, POST, OPTIONS").build();
    }

    @GET
    public Response getGameState(@DefaultValue("20") @QueryParam("width") int width,
            @DefaultValue("20") @QueryParam("height") int height) {
        if (mazeBean == null)
            mazeBean = new MazeBean();
        PlayableMaze maze = mazeBean.startGame(height, width);
        MazeMap map = MazeMap.consume(maze);
        Gson gson = new Gson();
        String jsonMap = gson.toJson(map);
        return buildResponse(jsonMap, MediaType.APPLICATION_JSON);
    }

    @GET
    @Path("/new")
    public Response startNewGame(@DefaultValue("20") @QueryParam("width") int width,
            @DefaultValue("20") @QueryParam("height") int height) {
        mazeBean.startGame(width, height);
        return buildResponse();
    }

    @GET
    @Path("/u")
    public Response moveUp() {
        mazeBean.moveUp();
        return playerPosResponse();
    }

    @GET
    @Path("/d")
    public Response moveDown() {
        mazeBean.moveDown();
        return playerPosResponse();
    }

    @GET
    @Path("/l")
    public Response moveLeft() {
        mazeBean.moveLeft();
        return playerPosResponse();
    }

    @GET
    @Path("/r")
    public Response moveRight() {
        mazeBean.moveRight();
        return playerPosResponse();
    }

    private Response playerPosResponse() {
        return buildResponse(getPlayerPosition(), MediaType.APPLICATION_JSON);
    }

    private Response buildResponse() {
        try {
            return buildResponse("OK", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Exception: " + e.getMessage())
                    .header("Content-Type", MediaType.TEXT_PLAIN).build();
        }
    }

    private Response buildResponse(Object entity, String contentType) {
        return Response.ok().entity(entity).header("Content-Type", contentType)
                .header("Access-Control-Allow-Credentials", "true").build();
    }

    private MazePosition getPlayerPosition() {
        MazePosition pos = new MazePosition();
        if (mazeBean.isFinished())
            pos.setTime(mazeBean.getPlayTime());
        pos.setX(mazeBean.getPlayerX());
        pos.setY(mazeBean.getPlayerY());
        return pos;
    }
}
