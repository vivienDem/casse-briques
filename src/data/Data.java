package data;

import java.io.File;
import java.util.List;
import java.util.Vector;

import data.bricks.Brick;
import interfaces.BallService;
import interfaces.DataService;
import javafx.scene.media.AudioClip;
import tools.BrickReader;
import tools.HardCodedParameters;
import tools.Position;

public class Data implements DataService {
	private Ball ball;
	private List<BallService> bonusBalls;
	private Player player;
	private List<Brick> bricks;
	private int score;
	private int windowWidth, windowHeigth;
	private boolean isStarting;
	private boolean isOver;
	private boolean isComplete;
	private boolean isInMenu;
	private boolean startOfTheGame;
	private boolean choosingLevel;
	private String filename;
	private int level = 1;
	private String message;
	private int displayTime;

	@Override
	public void init(String filename) {
		this.score = 0;
		this.ball = new Ball(HardCodedParameters.ballDefaultPosition, HardCodedParameters.radius,
				HardCodedParameters.ballSpeed);
		this.bonusBalls = new Vector<BallService>();
		this.player = new Player(HardCodedParameters.playerDefaultPosition, HardCodedParameters.playerWidth,
				HardCodedParameters.playerHeight);
		this.bricks = BrickReader.read(filename);
		this.filename = filename;
		this.windowWidth = HardCodedParameters.defaultWidth;
		this.windowHeigth = HardCodedParameters.defaultHeight;
		this.isStarting = true;
		this.isOver = false;
		this.isComplete = false;
		this.isInMenu = true;
		this.startOfTheGame = true;
		this.choosingLevel = false;
		this.message = "";
		this.displayTime = 0;
	}

	public boolean isInMenu() {
		return isInMenu;
	}

	public void setInMenu(boolean isInMenu) {
		this.isInMenu = isInMenu;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public List<Brick> getBricks() {
		return bricks;
	}

	@Override
	public void addScore(int score) {
		this.score += score;
	}

	@Override
	public void setBricks(List<Brick> bricks) {
		this.bricks = bricks;
	}

	@Override
	public void setPlayerPosition(Position position) {
		player.setPosition(position);
	}

	@Override
	public void setBallPosition(Position position) {
		ball.setPosition(position);
	}

	@Override
	public BallService getBall() {
		return ball;
	}

	@Override
	public Player getPlayer() {
		return player;
	}

	@Override
	public int getFieldWidth() {
		return this.windowWidth;
	}

	@Override
	public int getFieldHeigth() {
		return this.windowHeigth;
	}
	
	@Override
	public boolean isStarting() {
		return isStarting;
	}
	
	@Override
	public void setStarting(boolean isStarting) {
		this.isStarting = isStarting;
	}

	@Override
	public boolean gameOver() {
		return isOver;
	}
	
	@Override
	public boolean levelComplete() {
		return isComplete;
	}
	
	@Override
	public void setLevelComplete(boolean complete) {
		this.isComplete = complete;
		if (complete) {
			AudioClip sound = new AudioClip(new File("sounds/ucl-anthem_Trim.mp4").toURI().toString());
			sound.play();
		}
	}

	@Override
	public void setGameOver(boolean over) {
		this.isOver = over;
		if (over) {
			AudioClip sound = new AudioClip(new File("sounds/game-over.waw").toURI().toString());
			sound.play();
		}
	}

	@Override
	public void reset() {
		this.init(filename);
	}

	@Override
	public boolean isStartOfTheGame() {
		return this.startOfTheGame;
	}

	@Override
	public void setStartOfTheGame(boolean startOfTheGame) {
		this.startOfTheGame = startOfTheGame;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
		this.init("levels/" + level + ".txt");
	}

	@Override
	public boolean isChoosingLevel() {
		return choosingLevel;
	}

	@Override
	public void setChoosingLevel(boolean choosingLevel) {
		this.choosingLevel = choosingLevel;
	}

	@Override
	public String getMessage() {
		if (! message.equals("")) displayTime = (displayTime + 1) % 250;
		if (displayTime > 0) return message; // On continue d'afficher le message tant que le temps n'est pas ecoul�
		
		String tmp = message; // Une fois le temps d'affichage pass�, on enleve le message
		message = "";
		return tmp;
	}

	@Override
	public void setMessage(String toDisplay) {
		if (!toDisplay.equals("")) message = toDisplay;
		
	}

	@Override
	public List<BallService> getBonusBalls() {
		return bonusBalls;
	}

	@Override
	public void addBall(BallService ball) {
		this.bonusBalls.add(ball);
	}

	@Override
	public void removeBalls(List<BallService> balls) {
		this.bonusBalls.removeAll(balls);
	}

	@Override
	public void setBall(Ball ball) {
		this.ball = ball;
	}
	

}
