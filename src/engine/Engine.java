package engine;

import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import data.Ball;
import interfaces.BallService;
import interfaces.BallService.MOVE;
import interfaces.DataService;
import interfaces.EngineService;
import interfaces.RequireDataService;
import tools.FallenBall;
import tools.HardCodedParameters;
import tools.Position;
import tools.User.COMMAND;

public class Engine implements EngineService, RequireDataService {
	private static final double friction = HardCodedParameters.friction;
	private Timer engineClock;
	private DataService data;
	private boolean moveLeft, moveRight, moveUp, moveDown;
	private double playerVX, playerVY;
	private int playerMaxy = HardCodedParameters.playerMaxY;
	private int playerMinY = HardCodedParameters.playerMinY;
	private CollisionManager manager;
	private Random random;

	@Override
	public void bindDataService(DataService service) {
		this.data = service;
		this.manager = new CollisionManager(service);
	}

	@Override
	public void init() {
		this.engineClock = new Timer();
		this.moveLeft = false;
		this.moveRight = false;
		this.moveDown = false;
		this.moveUp = false;
		this.playerVX = 0;
		this.playerVY = 0;
		this.random = new Random();
	}

	@Override
	public void start() {
		data.setInMenu(false);
		data.setStartOfTheGame(false);
		engineClock.schedule(new TimerTask() {
			@Override
			public void run() {
				if (data.gameOver() || data.levelComplete()) {
					stop();
				}

				if (data.getBricks().isEmpty()) {
					data.setLevelComplete(true);
				}
				

				updateSpeedPlayer();
				updateCommandPlayer();
				updatePositionPlayer();

				updateSpeedBall(data.getBall());
				updateCommandBall(data.getBall());
				updatePositionBall(data.getBall());
				
				List<BallService> toRemove = new Vector<BallService>();
				
				for (int i = 0; i < data.getBonusBalls().size(); i++) {
					updateSpeedBall(data.getBonusBalls().get(i));
					updateCommandBall(data.getBonusBalls().get(i));
					updatePositionBall(data.getBonusBalls().get(i));
					
					try {
						manager.manage(data.getBonusBalls().get(i), data.getBricks(), data.getPlayer(), data.getFieldWidth(),
								data.getFieldHeigth());
					}
					catch (FallenBall f) {
						toRemove.add(data.getBonusBalls().get(i));
					}
				}
				
				data.removeBalls(toRemove);

				try {
					manager.manage(data.getBall(), data.getBricks(), data.getPlayer(), data.getFieldWidth(),
							data.getFieldHeigth());
				} catch (FallenBall f) {
					if (! data.getBonusBalls().isEmpty())
						data.setBall((Ball) data.getBonusBalls().remove(0));
					else {
						data.getPlayer().decreaseBalls();
						if (data.getPlayer().getNbBalls() == 0) {
							data.setGameOver(true);
						} else {
							int playerX = (int) ((data.getFieldWidth() - HardCodedParameters.playerWidth) / 2);
							int playerY = data.getFieldHeigth() - HardCodedParameters.playerHeight - 1;
							data.getPlayer().reset(new Position(playerX, playerY));
							moveLeft = false;
							moveRight = false;
							playerVX = 0;
							data.getBall().reset(new Position((int) (playerX + data.getPlayer().getWidth() / 2),
									(int) (playerY - data.getBall().getRadius())));
							data.setStarting(true);
						}
					}
				}

			}
		}, 0, HardCodedParameters.enginePaceMillis);

	}

	@Override
	public void stop() {
		this.engineClock.cancel();
	}

	@Override
	public void setPlayerCommand(COMMAND command) {
		if (data.isStarting())
			return;
		switch (command) {
		case LEFT:
			this.moveLeft = true;
			break;
		case RIGHT:
			this.moveRight = true;
			break;
		case UP:
			this.moveUp = true;
			break;
		case DOWN:
			this.moveDown = true;
			break;
		default:
			break;
		}
	}

	@Override
	public void releasePlayerCommand(COMMAND command) {
		switch (command) {
		case LEFT:
			this.moveLeft = false;
			break;
		case RIGHT:
			this.moveRight = false;
			break;
		case UP:
			this.moveUp = false;
			break;
		case DOWN:
			this.moveDown = false;
			break;
		default:
			break;
		}
	}

	private void updateSpeedPlayer() {
		playerVX *= friction;
		playerVY *= friction;
	}

	private void updateCommandPlayer() {
		double playerStepX = data.getPlayer().getPlayerStepX();
		double playerStepY = data.getPlayer().getPlayerStepY();
		if (moveLeft)
			playerVX -= playerStepX;
		if (moveRight)
			playerVX += playerStepX;
		if (!moveLeft && !moveRight)
			playerVX = 0;
		if (moveUp)
			playerVY -= playerStepY;
		if (moveDown)
			playerVY += playerStepY;
		if (!moveUp && !moveDown)
			playerVY = 0;
	}

	private void updatePositionPlayer() {
		Position old = data.getPlayer().getPosition();
		double x = old.getX() + this.playerVX;
		double y = old.getY() + this.playerVY;
		
		if (x < 0)
			x = 0;
		if (x > data.getFieldWidth() - data.getPlayer().getWidth())
			x = data.getFieldWidth() - data.getPlayer().getWidth();
		
		if (y < this.playerMinY)
			y = this.playerMinY;
		if (y > this.playerMaxy) {
			y = this.playerMaxy;
		}
		data.setPlayerPosition(new Position((int) x,(int) y));
	}

	private void updateSpeedBall(BallService ball) {
		ball.setVx(ball.getVx() * friction);
		ball.setVy(ball.getVy() * friction);
	}

	private void updateCommandBall(BallService ball) {
		double ballSpeed = ball.getSpeed();
		for (BallService.MOVE motion : ball.getMotions()) {
			switch (motion) {
			case LEFT:
				ball.setVx(-ballSpeed);
				break;
			case RIGHT:
				ball.setVx(ballSpeed);
			default:
				break;
			}

			switch (motion) {
			case UP:
				ball.setVy(-ballSpeed);
				break;
			case DOWN:
				ball.setVy(ballSpeed);
			default:
				break;
			}
		}
	}

	private void updatePositionBall(BallService ball) {
		Position old = ball.getPosition();
		double x = old.getX() + ball.getVx();
		double y = old.getY() + ball.getVy();

		if (x - ball.getRadius() < 0) {
			x = ball.getRadius();
		}
		
		if (x + ball.getRadius() >= data.getFieldWidth()) {
			x = data.getFieldWidth() - ball.getRadius();
		}

		if (y - ball.getRadius() < 0) {
			y = ball.getRadius();
		}
		
		if (y + ball.getRadius() >= data.getFieldHeigth()) {
			y = data.getFieldHeigth() - ball.getRadius();
		}

		ball.setPosition((new Position((int) x, (int) y)));
	}

	private void moveBallLeft(BallService ball) {
		ball.addMotion(MOVE.LEFT);
	}

	private void moveBallRight(BallService ball) {
		ball.addMotion(MOVE.RIGHT);
	}

	private void moveBallUp(BallService ball) {
		ball.addMotion(MOVE.UP);
	}

	@Override
	public void startBall() {
		if (data.isStarting()) {
			this.moveBallUp(data.getBall());
			if (random.nextDouble() < 0.5) {
				this.moveBallLeft(data.getBall());
			} else {
				this.moveBallRight(data.getBall());
			}
		}
		data.setStarting(false);
	}


	@Override
	public void menu() {
		this.engineClock.cancel();
		data.setInMenu(true);
		data.setChoosingLevel(false);
	}

	@Override
	public void resume() {
		this.engineClock = new Timer();
		this.start();
		data.setInMenu(false);
	}

	@Override
	public void restart() {
		data.reset();
	}

	@Override
	public void changeLevel(int level) {
		data.setLevel(level);
		data.setChoosingLevel(false);
		this.resume();
	}

	@Override
	public void changeLevel() {
		data.setChoosingLevel(true);
	}

}
