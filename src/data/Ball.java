package data;

import java.util.ArrayList;
import java.util.List;

import interfaces.BallService;
import tools.HardCodedParameters;
import tools.Position;

public class Ball implements BallService {
	Position position;
	private double vx, vy;
	private double radius;
	private int strength;
	private List<MOVE> motions;
	private double speed;
	
	public Ball(Position position, double vx, double vy, double radius, int strength, double speed) {
		super();
		this.position = position;
		this.vx = vx;
		this.vy = vy;
		this.radius = radius;
		this.strength = strength;
		this.motions = new ArrayList<MOVE>();
		this.speed = speed;
	}
	
	public Ball(Position position, double radius, double speed) {
		this(position,0, 0, radius, HardCodedParameters.ballStrength, speed);
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	@Override
	public double getVx() {
		return vx;
	}
	
	@Override
	public void setVx(double vx) {
		this.vx = vx;
	}
	
	@Override
	public double getVy() {
		return vy;
	}
	
	@Override
	public void setVy(double vy) {
		this.vy = vy;
	}
	
	@Override
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	

	@Override
	public List<MOVE> getMotions() {
		return motions;
	}

	@Override
	public void addMotion(MOVE motion) {
		this.motions.add(motion);
	}

	@Override
	public void clearMotions() {
		this.motions.clear();
	}

	@Override
	public int getBallStrength() {
		return strength;
	}

	@Override
	public void reset(Position p) {
		this.position = p;
		this.vx = 0;
		this.vy = 0;
		this.strength = HardCodedParameters.ballStrength;
		this.speed = HardCodedParameters.ballSpeed;
		this.radius = HardCodedParameters.radius;
		this.clearMotions();
	}

	@Override
	public double getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
		
	}

	
	
}
