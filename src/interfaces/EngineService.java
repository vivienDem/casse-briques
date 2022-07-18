package interfaces;

import tools.User;

public interface EngineService {
	void init();
	void start();
	void stop();
	void setPlayerCommand(User.COMMAND command);
	void releasePlayerCommand(User.COMMAND command);
	void startBall();
	void menu();
	void resume();
	void restart();
	void changeLevel(int level);
	void changeLevel();
}
