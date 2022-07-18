package data;

import tools.HardCodedParameters;
import tools.Position;

public class Player {
	private Position position;
	private int width, height;
	private int nbBalls;
	private double playerStepX;
	private double playerStepY;
	
	public Player(Position position, int width, int height) {
		super();
		this.position = position;
		this.width = width;
		this.height = height;
		this.nbBalls = HardCodedParameters.playerBalls;
		this.playerStepX = HardCodedParameters.playerStepX;
		this.playerStepY = HardCodedParameters.playerStepY;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getNbBalls() {
		return nbBalls;
	}

	public void increaseBalls(int nb) {
		this.nbBalls += nb;
	}
	
	public void decreaseBalls() {
		if (this.nbBalls > 0) this.nbBalls--;
	}
	
	public void reset(Position p) {
		this.position = p;
		this.width = HardCodedParameters.playerWidth;
	}

	public double getPlayerStepX() {
		return playerStepX;
	}
	
	public void setPlayerStepX(double step) {
		this.playerStepX = step;
	}
	
	public double getPlayerStepY() {
		return playerStepY;
	}
	
	public void setPlayerStepY(double step) {
		this.playerStepY = step;
	}
	
}
