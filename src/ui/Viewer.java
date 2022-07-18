package ui;

import java.io.File;
import java.util.List;

import data.Ball;
import data.Player;
import data.bricks.BasicBrick;
import data.bricks.BonusAddBallBrick;
import data.bricks.BonusLifeBrick;
import data.bricks.BonusSizeBallBrick;
import data.bricks.BonusSizePlayerBrick;
import data.bricks.BonusSpeedBallBrick;
import data.bricks.Brick;
import data.bricks.MalusSpeedBallBrick;
import data.bricks.SolidBrick;
import interfaces.BallService;
import interfaces.EngineService;
import interfaces.ReadService;
import interfaces.RequireEngineService;
import interfaces.RequireReadService;
import interfaces.ViewerService;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import tools.HardCodedParameters;

public class Viewer implements ViewerService, RequireReadService, RequireEngineService {
	private ReadService data;
	private EngineService engine;
	private static final double defaultMainWidth = HardCodedParameters.defaultWidth;
	private static final double defaultMainHeight = HardCodedParameters.defaultHeight;
	private double xShrink, yShrink, shrink;
	AudioClip sound; 

	@Override
	public void bindReadService(ReadService service) {
		this.data = service;
	}

	@Override
	public void bindEngineService(EngineService service) {
		this.engine = service;
	}

	@Override
	public void init() {
		this.xShrink = 1;
		this.yShrink = 1;
		sound = new AudioClip(new File("sounds/menu-click.waw").toURI().toString());
	}

	@Override
	public Parent getPanel() {
		shrink = Math.min(xShrink, yShrink);
		if (data.isChoosingLevel())
			return levelSelectorWindow();
		if (data.isInMenu())
			return menuWindow();
		return gameWindow();
	}

	private Parent menuWindow() {
		double buttonMinSize = 150 * xShrink;

		Button play = this.createButton("Play", buttonMinSize, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sound.play();
				if (data.isInMenu()) {
					engine.resume();
				} else {
					engine.menu();
				}
			}
		});
		
		Button level = this.createButton("Select level", buttonMinSize, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				sound.play();
				engine.changeLevel();
			}

		});

		Button restart = this.createButton("Restart", buttonMinSize, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				sound.play();
				engine.restart();
				engine.resume();
			}

		});

		Button quit = this.createButton("Quit", buttonMinSize, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				sound.play();
				Platform.exit();
			}

		});

		if (data.isStartOfTheGame()) {
			restart.setDisable(true);
		}

		VBox box = this.createMenu();
		box.getChildren().addAll(play, level, restart, quit);

		StackPane root = this.createStackPane(box);
		this.resize(root);
		return root;
	}

	private Parent gameWindow() {
		String lives = data.getPlayer().getNbBalls() <= 1 ? "Life: " : "Lives: ";

		Pane panel = this.createGamePane();
		
		Label score = this.createText(panel, "Score: " + data.getScore(), Pos.TOP_RIGHT);

		Label livesDisplay = this.createText(panel, lives + data.getPlayer().getNbBalls(), Pos.TOP_LEFT);

		Circle ball = this.createBall((Ball) data.getBall());
		
		for (int i = 0; i < data.getBonusBalls().size(); i++) {
			panel.getChildren().add(this.createBall((Ball) data.getBonusBalls().get(i)));
		}
		
		Rectangle player = this.createPlayer(data.getPlayer());

		if (data.gameOver()) {
			Label over = this.createText(panel, "GAME OVER !\n   Score : " + data.getScore(), Pos.CENTER);
			panel.getChildren().add(over);
		}
		
		if (data.levelComplete()) {
			Label win = this.createText(panel, "LEVEL COMPLETE !\nScore : " + data.getScore(), Pos.CENTER);
			panel.getChildren().add(win);
		}
		
		String message = data.getMessage();		
		if(! message.equals("")) {
			Label specialEffect = this.createText(panel, message, Pos.TOP_CENTER);
			panel.getChildren().add(specialEffect);
		}

		panel.getChildren().addAll(score, livesDisplay, ball, player);
		this.addBricks(data.getBricks(), panel);
		this.resize(panel);
		return panel;
	}
	
	private Parent levelSelectorWindow() {
		double buttonMinSize = 150 * xShrink;
		VBox box = this.createMenu();
		for (int i = 0; i < HardCodedParameters.nbLevels; i++) {
			int levelIndex = i + 1;
			Button level = this.createButton("Level " + levelIndex, buttonMinSize, 
					new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent arg0) {
							sound.play();
							engine.changeLevel(levelIndex);
						}
			});
			box.getChildren().add(level);
		}
		
		StackPane panel = this.createStackPane(box);
		this.resize(panel);
		return panel;
	}

	@Override
	public void setMainWindowWidth(double width) {
		xShrink = width / defaultMainWidth;

	}

	@Override
	public void setMainWindowHeight(double height) {
		yShrink = height / defaultMainHeight;

	}

	private Rectangle createBrick(Brick brick) {
		int brickX = brick.getPosition().getX();
		int brickY = brick.getPosition().getY();

		Rectangle toAdd = new Rectangle(brickX, brickY, brick.getWidth(), brick.getHeight());
		this.setStyle(toAdd, brick);
		return toAdd;
	}

	private void addBricks(List<Brick> bricks, Pane panel) {
		for (Brick brick : bricks) {
			panel.getChildren().add(this.createBrick(brick));
		}
	}

	private void resize(Pane panel) {
		for (Node node : panel.getChildren()) {
			if (node instanceof Rectangle) {
				this.resize((Rectangle) node);
				continue;
			}
			if (node instanceof Circle) {
				this.resize((Circle) node);
				continue;
			}
			if (node instanceof VBox) {
				this.resize((VBox) node);
				continue;
			}

		}
	}

	private Button createButton(String name, double minSize, EventHandler<ActionEvent> handler) {
		Button button = new Button(name);
		button.setMinWidth(minSize);
		button.setOnAction(handler);
		return button;
	}

	private VBox createMenu() {
		VBox box = new VBox();
		box.setAlignment(Pos.CENTER);
		box.setSpacing(30);
		return box;
	}
	
	private Pane createGamePane() {
		Pane panel = new Pane();
		this.setStyle(panel);
		panel.setMouseTransparent(true);
		return panel;
	}

	private StackPane createStackPane(VBox box) {
		StackPane pane = new StackPane(box);
		pane.setPrefHeight(defaultMainHeight);
		pane.setPrefWidth(defaultMainWidth);
		this.setStyle(pane);
		return pane;
	}

	private Label createText(Pane panel, String text, Pos position) {
		Label result = new Label(text);
		result.setAlignment(position);
		result.setFont(new Font("Brush Script MT", 10));
		result.getStyleClass().add("text-info");
		result.setTextAlignment(TextAlignment.CENTER);
		result.minWidthProperty().bind(panel.widthProperty());
		result.minHeightProperty().bind(panel.heightProperty());
		return result;
	}

	private Circle createBall(Ball ball) {
		Circle result = new Circle(ball.getPosition().getX(), ball.getPosition().getY(), data.getBall().getRadius());
		result.getStyleClass().add("ball");
		return result;
	}

	private Rectangle createPlayer(Player player) {
		Rectangle result = new Rectangle(player.getPosition().getX(), player.getPosition().getY(),
				data.getPlayer().getWidth(), data.getPlayer().getHeight());
		result.getStyleClass().add("player");
		return result;
	}

	private void resize(Rectangle rectangle) {
		rectangle.setX(rectangle.getX() * xShrink);
		rectangle.setY(rectangle.getY() * yShrink);
		rectangle.setWidth(rectangle.getWidth() * xShrink);
		rectangle.setHeight(rectangle.getHeight() * yShrink);
	}

	private void resize(Circle circle) {
		circle.setCenterX(circle.getCenterX() * xShrink);
		circle.setCenterY(circle.getCenterY() * yShrink);
		circle.setRadius(circle.getRadius() * shrink);
	}

	private void resize(VBox box) {
		box.setScaleX(xShrink);
		box.setScaleY(yShrink);
		box.setPrefWidth(box.getWidth() * xShrink);
		box.setPrefHeight(box.getHeight() * yShrink);
	}

	private void setStyle(Pane panel) {
		String map = "map" + data.getLevel();
		panel.getStyleClass().add(map);
		panel.setPrefSize(defaultMainWidth, defaultMainHeight);

	}

	private void setStyle(Rectangle shape, Brick brick) {
		if (brick instanceof BasicBrick) {
			shape.getStyleClass().add("basic-brick");
			return;
		}
		if (brick instanceof SolidBrick) {
			int nbLives = brick.getLp();
			shape.getStyleClass().add("solid-brick" + nbLives);
			return;
		}
		
		if (brick instanceof BonusSizePlayerBrick) {
			int nbLives = brick.getLp();
			shape.getStyleClass().add("bonus-size-player-brick" + nbLives);
			return;
		}
		if (brick instanceof BonusSizeBallBrick) {
			int nbLives = brick.getLp();
			shape.getStyleClass().add("bonus-size-ball-brick" + nbLives);
			return;
		}
		if (brick instanceof BonusSizeBallBrick) {
			int nbLives = brick.getLp();
			shape.getStyleClass().add("bonus-size-ball-brick" + nbLives);
			return;
		}
		if (brick instanceof BonusSpeedBallBrick) {
			shape.getStyleClass().add("bonus-speed-ball-brick");
			return;
		}
		if (brick instanceof MalusSpeedBallBrick) {
			shape.getStyleClass().add("malus-speed-ball-brick");
			return;
		}
		if (brick instanceof BonusLifeBrick) {
			shape.getStyleClass().add("bonus-life-brick");
			return;
		}
		if (brick instanceof BonusAddBallBrick) {
			shape.getStyleClass().add("bonus-add-ball-brick");
			return;
		}
		
	}

}
