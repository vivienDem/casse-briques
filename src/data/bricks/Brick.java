package data.bricks;

import interfaces.DataService;
import tools.Position;

public abstract class Brick {
	protected Position position;
	protected int height, width;
	protected int lp;
	protected int score;
	
	public Brick(Position position, int height, int width, int lp, int score) {
		super();
		this.position = position;
		this.height = height;
		this.width = width;
		this.lp = lp;
		this.score = score;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLp() {
		return lp;
	}

	public void setLp(int lp) {
		this.lp = lp;
	}

	public int getScore() {
		return score;
	}
	
	public String specialEffect(DataService data) {	
		return "";
	}
	
}
