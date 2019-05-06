package net.gilmor.mazeui.main;

import javafx.application.Application;
import net.gilmor.mazeui.application.MazeApplication;

@SuppressWarnings("restriction")
public class Main {

	public static void main(String[] args) throws Exception {
		Application app = new MazeApplication();
		app.launch(args);
	}
}
