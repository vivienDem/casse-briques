package interfaces;

import java.util.List;

import data.Ball;
import data.bricks.Brick;
import tools.Position;

public interface WriteService {
	void addScore(int score);
	void setBricks(List<Brick> bricks);
	void setBall(Ball ball);
	void addBall(BallService ball);
	void removeBalls(List<BallService> balls);
	void setPlayerPosition(Position position);
	void setBallPosition(Position position);
	void setStarting(boolean isStarting);
	void setGameOver(boolean over);
	void setInMenu(boolean inMenu);
	void setStartOfTheGame(boolean startOfTheGame);
	void reset();
	void setChoosingLevel(boolean choosingLevel);
	void setLevel(int level);
	void setMessage(String toDisplay);
	void setLevelComplete(boolean complete);
}
