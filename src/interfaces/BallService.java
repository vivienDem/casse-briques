package interfaces;

import java.util.List;

import tools.Position;

public interface BallService {
	enum MOVE {
		LEFT, RIGHT, UP, DOWN
	};
	Position getPosition();
	double getRadius();
	List<MOVE> getMotions();
	void addMotion(MOVE motion);
	void clearMotions();
	void setPosition(Position p);
	void setRadius(double radius);
	void reset(Position p);
	int getBallStrength();
	double getVx();
	void setVx(double d);
	double getVy();
	void setVy(double d);
	double getSpeed();
	void setSpeed(double speed);
}
