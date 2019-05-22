package net.gilmor.ws;

import java.util.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import net.gilmor.service.maze.MazeService;

@ApplicationPath("/services")
public class AppRoot extends Application {

	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> controllers = new HashSet<Class<?>>();

	public AppRoot() {
	}

	@Override
	public Set<Class<?>> getClasses() {
		controllers.add(MazeService.class);
		return controllers;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
