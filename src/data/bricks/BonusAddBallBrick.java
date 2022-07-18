package data.bricks;

import data.Ball;
import interfaces.BallService;
import interfaces.DataService;
import tools.HardCodedParameters;
import tools.Position;

public class BonusAddBallBrick extends Brick {

	public BonusAddBallBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 1, 5);
	}
	
	@Override
	public String specialEffect(DataService data) {
		assert lp == 0;
		double radius = HardCodedParameters.radius;
		double x = position.getX() + radius;
		double y = position.getY() + radius;
		BallService bonus =  new Ball(new Position((int)x, (int) y), radius, HardCodedParameters.ballSpeed);
		bonus.setVx(data.getBall().getVx());
		bonus.setVy(data.getBall().getVy());
		bonus.setSpeed(data.getBall().getSpeed());
		for (BallService.MOVE motion : data.getBall().getMotions()) {
			bonus.addMotion(motion);
		}
		data.addBall(bonus);
		return "BONUS : NEW BALL";
	}

}
