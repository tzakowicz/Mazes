package net.gilmor.ws;

import java.util.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import net.gilmor.service.maze.MazeService;
import net.gilmor.service.maze.game.MazeGame;

@ApplicationPath("/maze")
public class AppRoot extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> controllers = new HashSet<Class<?>>();

	public AppRoot() {
	}

	@Override
	public Set<Class<?>> getClasses() {
		controllers.add(MazeService.class);
		controllers.add(MazeGame.class);
		return controllers;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
