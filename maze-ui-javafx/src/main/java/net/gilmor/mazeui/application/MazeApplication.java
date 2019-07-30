/*
 *  ©2019 Thomas Zakowicz, All rights reserved.
 */

package net.gilmor.mazeui.application;

import java.awt.image.BufferedImage;
import java.util.function.IntConsumer;
import java.util.function.UnaryOperator;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import maze.algorithms.RandomLeafWalkMaze;
import maze.display.HeatMapMazeImage;
import maze.display.MazeImage;
import maze.display.PathMazeImage;
import maze.parent.PlayableMaze;

@SuppressWarnings("restriction")
public class MazeApplication extends Application {

	private Stage mainStage;
	private int cellSize = 20;
	private TextField tfHeight;
	private TextField tfWidth;
	private int offset = cellSize/5;
	private int size = cellSize-offset-offset;
	private double width, height;
	private PlayableMaze maze;
	private Canvas playerCanvas;
	private long startTime = 0;

	public static void main(String[] args) {
		launch(args);
	}
	
	public void setHeight(int dim) {
		if (tfHeight == null)
			tfHeight = new TextField();
		tfHeight.setText(String.valueOf(dim));
	}
	
	public int getHeight() {
		if (tfHeight == null)
			return 20;
		return Integer.parseInt(tfHeight.getText());
	}
	
	public void setWidth(int dim) {
		if (tfWidth == null)
			tfWidth = new TextField();
		tfWidth.setText(String.valueOf(dim));
	}
	
	public int getWidth() {
		if (tfWidth == null)
			return 20;
		return Integer.parseInt(tfWidth.getText());
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
		Text xText = new Text("Height:");
		tfHeight = getNumericTextField(getHeight(), this::setHeight);
		HBox hbx = new HBox(40, xText, tfHeight);
		Text yText = new Text("Width:");
		TextField tfDimY = getNumericTextField(getWidth(), this::setWidth);
		HBox hby = new HBox(40, yText, tfDimY);
		VBox box = new VBox(10, hbx, hby);
		box.setTranslateX((width/3)-tfHeight.getWidth()-30);
		box.setTranslateY((3*height/4)-tfHeight.getHeight()-30);
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
				goToGame(getHeight(), getWidth());
			}
		});
		return button;
	}
	
	// MAIN MENU END //

	// GAME START //
	
	private void goToGame(int height, int width) {
		initMaze(height, width);
		BufferedImage img = new HeatMapMazeImage(maze)
				.setHeatStart(maze.getFinishRow(), maze.getFinishCol())
				.setCellSize(cellSize)
				.getImage();
		ImageView imgView = new ImageView();
		imgView.setImage(SwingFXUtils.toFXImage(img, null));
		playerCanvas = new Canvas(img.getWidth(), img.getHeight());
		Pane gamePane = new StackPane();
		gamePane.getChildren().addAll(imgView, playerCanvas);
		Scene scene = new Scene(gamePane, img.getWidth(), img.getHeight());
		scene.addEventHandler(KeyEvent.KEY_PRESSED, this::handleEvent);
		drawSpritePositions();
		startTime = System.currentTimeMillis();
		goTo(scene);
	}

	private void initMaze(int height, int width) {
		maze = new RandomLeafWalkMaze(height, width);
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
		drawSpritePositions();
		processWin();
	}
	
	private void drawSpritePositions() {
		GraphicsContext gc = playerCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, playerCanvas.getWidth(), playerCanvas.getHeight());
		markPos(gc, maze.getFinishRow(), maze.getFinishCol(), Color.GREEN);
		markPos(gc, maze.getPlayerRow(), maze.getPlayerCol(), Color.RED);
	}
	
	private void markPos(GraphicsContext gc, int row, int col, Color c) {
		gc.setFill(c);
		int finalRowPos = translateRow(row) + offset;
		int finalColPos = translateCol(col) + offset;
        gc.fillOval(finalColPos, finalRowPos, size, size);
	}
	
	private int translateRow(int cellRow) {
		return (maze.getRows() - 1 - cellRow) * (cellSize) + (maze.getRows() - cellRow);
	}
	
	private int translateCol(int cellCol) {
		return cellCol * cellSize + cellCol + 1;
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
