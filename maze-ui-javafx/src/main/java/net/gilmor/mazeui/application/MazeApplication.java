/*
 *  ©2019 Thomas Zakowicz, All rights reserved.
 */

package net.gilmor.mazeui.application;

import java.awt.image.BufferedImage;
import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.converter.*;
import maze.algorithms.RandomLeafWalkMaze;
import maze.display.MazeImage;
import maze.parent.PlayableMaze;

@SuppressWarnings("restriction")
public class MazeApplication extends Application {

	private Stage mainStage;
	private int ratio = 20;
	TextField tfDimX;
	TextField tfDimY;
	private int offset = ratio/5;
	private int size = ratio-offset-offset;
	private double width, height;
	private PlayableMaze maze;
	private Canvas playerCanvas;
	private long startTime = 0;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void setDimX(int dim) {
		if (tfDimX == null)
			tfDimX = new TextField();
		tfDimX.setText(String.valueOf(dim));
	}
	
	public int getDimX() {
		if (tfDimX == null)
			return 20;
		return Integer.parseInt(tfDimX.getText());
	}
	
	public void setDimY(int dim) {
		if (tfDimY == null)
			tfDimY = new TextField();
		tfDimY.setText(String.valueOf(dim));
	}
	
	public int getDimY() {
		if (tfDimY == null)
			return 20;
		return Integer.parseInt(tfDimY.getText());
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		width = 600;
		height = 400;
		
		mainStage = primaryStage;
		mainStage.setTitle("Maze Time FX");
		mainStage.show();
		
		goToMenu();
	}

	// MAIN MENU START //
	
	private void goToMenu() {
		Text titleText = getTitleText();
		Text xText = new Text("X:");
		tfDimX = getNumericTextField(getDimX(), this::setDimX);
		HBox hbx = new HBox(10, xText, tfDimX);
		Text yText = new Text("Y:");
		TextField tfDimY = getNumericTextField(getDimY(), this::setDimY);
		HBox hby = new HBox(10, yText, tfDimY);
		VBox box = new VBox(10, hbx, hby);
		box.setTranslateX((width/3)-tfDimX.getWidth()-30);
		box.setTranslateY((3*height/4)-tfDimX.getHeight()-30);
		Button playButton = getPlayButton();
		StackPane menuPane = new StackPane();
		menuPane.getChildren().addAll(titleText, playButton, box);
		Scene scene = new Scene(menuPane, width, height);
		goTo(scene);
	}

	private Text getTitleText() {
		Text text = new Text("Maze Time FX");
		text.setFont(new Font(30));
		text.setTranslateY(height / 4 * -1);
		return text;
	}

	private TextField getNumericTextField(int currentValue, IntConsumer method) {
		TextField textField = new TextField();
		textField.setAlignment(Pos.CENTER_RIGHT);
		textField.setTextFormatter(new TextFormatter<Integer>(new IntegerStringConverter(), 0, getNumericTextOperator()));
		textField.focusedProperty().addListener((observable, oldvalue, newvalue) -> {
			if (!newvalue) {
				int value = Integer.parseInt(textField.getText());
				if (value < 1)
					value = 10;
				if (value > 60)
					value = 60;
				textField.setText(String.valueOf(value));
				method.accept(value);
			}
		});
		textField.setText(""+currentValue);
		textField.setPrefWidth(40);
		return textField;
	}

	private UnaryOperator<TextFormatter.Change> getNumericTextOperator() {
		return change -> {
		    String newText = change.getControlNewText();
		    if (newText.matches("-?([1-9][0-9]*)?")) {
		        return change;
		    }
		    return null;
		};
	}

	private Button getPlayButton() {
		Button button = new Button("Play");
		button.setTranslateX(width/4);
		button.setTranslateY(height/4);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				goToGame(getDimX(), getDimY());
			}
		});
		return button;
	}
	
	// MAIN MENU END //

	// GAME START //
	
	private void goToGame(int mazeWidth, int mazeHeight) {
		initMaze(mazeWidth, mazeHeight);
		BufferedImage img = new MazeImage(maze)
				.setRatio(ratio)
				.getImage();
		ImageView imgView = new ImageView();
		imgView.setImage(SwingFXUtils.toFXImage(img, null));
		playerCanvas = new Canvas(img.getWidth(), img.getHeight());
		Pane gamePane = new StackPane();
		gamePane.getChildren().addAll(imgView, playerCanvas);
		Scene scene = new Scene(gamePane, img.getWidth(), img.getHeight());
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleEvent);
		drawPlayerPos();
		startTime = System.currentTimeMillis();
		goTo(scene);
	}

	private void initMaze(int x, int y) {
		maze = new RandomLeafWalkMaze(x, y);
		maze.initGrid();
	}

	private void handleEvent(KeyEvent key) {
		switch (key.getCode()) {
		case A:
		case LEFT:
			maze.moveWest();
			break;
		case W:
		case UP:
			maze.moveNorth();
			break;
		case S:
		case DOWN:
			maze.moveSouth();
			break;
		case D:
		case RIGHT:
			maze.moveEast();
			break;
		default:
			break;
		};
		drawPlayerPos();
		processWin();
	}
	
	private void drawPlayerPos() {
		GraphicsContext gc = playerCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, playerCanvas.getWidth(), playerCanvas.getHeight());
		markPos(gc, maze.getFinishX(), maze.getFinishY(), Color.GREEN);
		markPos(gc, maze.getPlayerX(), maze.getPlayerY(), Color.RED);
	}
	
	private void markPos(GraphicsContext gc, int x, int y, Color c) {
		gc.setFill(c);
		int finishX = translateX(x) + offset;
		int finishY = translateY(y) + offset;
        gc.fillOval(finishY, finishX, size, size);
	}
	
	private int translateX(int cellX) {
		return (maze.getRows()-1 - cellX) * ratio;
	}
	
	private int translateY(int cellY) {
		return cellY * ratio;
	}
	
	private void processWin() {
		if (maze.checkWin()) {
			goToFinish();
		}
	}
	
	// GAME END //
	
	// FINISH MENU //

	private void goToFinish() {
		Text finishText = getFinishText();
		Text timeText = getTimeText();
		Button replayButton = getMenuButton();
		StackPane finishPane = new StackPane();
		finishPane.getChildren().addAll(finishText, timeText, replayButton);
		Scene scene = new Scene(finishPane, width, height);
		goTo(scene);
	}

	private Text getFinishText() {
		Text text = new Text("You Won!");
		text.setFill(Color.GREEN);
		text.setFont(new Font(20));
		text.setTranslateY((height / 4) * -1);
		return text;
	}
	
	private Text getTimeText() {
		Text text = new Text(getTimePlayed());
		text.setFill(Color.BLACK);
		text.setFont(new Font(16));
		text.setTranslateY(height / 6 * -1);
		return text;
	}

	private String getTimePlayed() {
		long totalTime = (System.currentTimeMillis() - startTime);
		long millis = totalTime%1000;
		long seconds = (totalTime-millis)/1000%60;
		long minutes = (totalTime-millis)/1000/60%60;
		long hours = (totalTime-millis)/1000/60/60%60;
		return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);
	}
	
	private Button getMenuButton() {
		Button button = new Button("Return to Menu");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				goToMenu();
			}
		});
		button.setTranslateY(height/6);
		return button;
	}
	
	// FINISH MENU END //
	
	private void goTo(Scene scene) {
		mainStage.setScene(scene);
		mainStage.sizeToScene();
	}
}
