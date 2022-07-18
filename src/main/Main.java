package main;

import data.Data;
import engine.Engine;
import interfaces.DataService;
import interfaces.EngineService;
import interfaces.ViewerService;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import tools.User;
import ui.Viewer;

public class Main extends Application {
	private static DataService data;
	private static EngineService engine;
	private static ViewerService viewer;
	private static AnimationTimer timer;

	public static void main(String[] args) {
		data = new Data();
		engine = new Engine();
		viewer = new Viewer();

		((Engine) engine).bindDataService(data);
		((Viewer) viewer).bindReadService(data);
		((Viewer) viewer).bindEngineService(engine);

		data.init("levels/1.txt");
		engine.init();
		viewer.init();
		
		launch(args);

	}

	@Override
	public void start(Stage stage) {
		final Scene scene = new Scene(((Viewer) viewer).getPanel());
		stage.getIcons().add(new Image("file:images/icon.jpg"));
		scene.getStylesheets().add("ui/style.css");
		
		timer = this.createTimer(scene);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (!data.isInMenu()) {
					if (event.getCode() == KeyCode.ENTER)
						engine.startBall();
					if (event.getCode() == KeyCode.LEFT)
						engine.setPlayerCommand(User.COMMAND.LEFT);
					if (event.getCode() == KeyCode.RIGHT)
						engine.setPlayerCommand(User.COMMAND.RIGHT);
					if (event.getCode() == KeyCode.UP)
						engine.setPlayerCommand(User.COMMAND.UP);
					if (event.getCode() == KeyCode.DOWN)
						engine.setPlayerCommand(User.COMMAND.DOWN);
				}
				
				if (!data.isChoosingLevel()) {
					if (event.getCode() == KeyCode.ESCAPE) {
						if (data.isInMenu()) {
							engine.resume();
						}
						else {
							engine.menu();
							scene.setRoot(((Viewer) viewer).getPanel());
						}
					}
				}
				
				if (data.isChoosingLevel()) {
					if (event.getCode() == KeyCode.ESCAPE) 
						engine.menu();
						scene.setRoot(((Viewer) viewer).getPanel());
				}
				
					
				event.consume();
			}
		});
		
		scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.LEFT)
					engine.releasePlayerCommand(User.COMMAND.LEFT);
				if (event.getCode() == KeyCode.RIGHT)
					engine.releasePlayerCommand(User.COMMAND.RIGHT);
				if (event.getCode() == KeyCode.UP)
					engine.releasePlayerCommand(User.COMMAND.UP);
				if (event.getCode() == KeyCode.DOWN)
					engine.releasePlayerCommand(User.COMMAND.DOWN);
				event.consume();
			}
		});
		
		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				viewer.setMainWindowWidth(newSceneWidth.doubleValue());
				scene.setRoot(((Viewer) viewer).getPanel());
			}
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				viewer.setMainWindowHeight(newSceneHeight.doubleValue());
				scene.setRoot(((Viewer) viewer).getPanel());
			}
		});

		stage.setScene(scene);
		
		stage.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				engine.menu();
			}
		});
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				engine.stop();
			}
		});

		stage.show();
		
		timer.start();
	}
	
	private AnimationTimer createTimer(Scene scene) {
		return new AnimationTimer() {
			int i = 0;
			@Override
			public void handle(long l) {
				if (!data.isInMenu() || (data.isChoosingLevel() && i % 80 == 0)) {
					scene.setRoot(((Viewer) viewer).getPanel());
				}
				i++;
			}
		};
	}

}
