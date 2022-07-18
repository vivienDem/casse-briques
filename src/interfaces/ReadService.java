package interfaces;

import java.util.List;

import data.Player;
import data.bricks.Brick;

public interface ReadService {
	int getScore();
	List<Brick> getBricks();
	Player getPlayer();
	BallService getBall();
	List<BallService> getBonusBalls();
	int getFieldWidth();
	int getFieldHeigth();
	boolean isStarting();
	boolean gameOver();
	boolean isInMenu();
	boolean isStartOfTheGame();
	boolean isChoosingLevel();
	int getLevel();
	String getMessage();
	boolean levelComplete();
}
