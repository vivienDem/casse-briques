package engine;

import java.util.ArrayList;
import java.util.List;

import data.Player;
import data.bricks.Brick;
import interfaces.BallService;
import interfaces.DataService;
import tools.FallenBall;
import tools.Position;

public class CollisionManager {
	private DataService data;

	public CollisionManager(DataService data) {
		super();
		this.data = data;
	}
	
	//Retourne le mouvement qui change suite a la collision et null si pas de collision
	private List<BallService.MOVE> collision(BallService ball, Position position, int width, int height) {
		int ballX = ball.getPosition().getX();
		int ballY = ball.getPosition().getY();
		int rectX = position.getX();
		int rectY = position.getY();
		int testX = ballX, testY = ballY;
		List<BallService.MOVE> result = new ArrayList<BallService.MOVE>();
		
		if (ballX < rectX) {
			testX = rectX;
		}
			
		else if (ballX > rectX + width) {
			testX = rectX + width;
		}
			
		if (ballY < rectY) 
			testY = rectY;
		else if (ballY > rectY + height)
			testY = rectY + height;
		
		int distX = ballX - testX;
		int distY = ballY - testY;
		double distanceSq = distX * distX + distY * distY;
		
		if (distanceSq <= ball.getRadius() * ball.getRadius()) {
			if (testX == rectX) {
				result.add(BallService.MOVE.LEFT);
			}
			if (testX == rectX + width) {
				result.add(BallService.MOVE.RIGHT);
			}
			if (testY == rectY) {
				result.add(BallService.MOVE.UP);
			}
			if (testY == rectY + height) {
				result.add(BallService.MOVE.DOWN);
			}
			return result;
		}
			
		return null;
	}
	
	public void manage(BallService ball, List<Brick> bricks, Player player, int width, int height) throws FallenBall {
		if (data.isStarting()) return;
		List<BallService.MOVE> changedDirections = new ArrayList<BallService.MOVE>();
		List<BallService.MOVE> bounces;
		BallService.MOVE oppositeDirection;
		
		bounces = this.manageBricksCollision(ball, bricks);
		
		if (bounces != null)
			changedDirections.addAll(bounces);
		
		bounces = this.manageEdgesCollision(ball, width, height);
		
		if (bounces != null)
			changedDirections.addAll(bounces);
		
		bounces = this.managePlayerCollision(ball, player);
		
		if (bounces != null)
			changedDirections.addAll(bounces);
		
		if (changedDirections.isEmpty()) return;
		
		
		for (BallService.MOVE motion : changedDirections) {
			oppositeDirection = this.getOppositeDirection(motion);
			ball.getMotions().remove(oppositeDirection);
			if(! ball.getMotions().contains(motion))
				ball.getMotions().add(motion);
		}
	}
	
	private List <BallService.MOVE> manageBricksCollision(BallService ball, List<Brick> bricks) {
		List <BallService.MOVE> toDoMotions = null; 
		Brick toRemove = null;
		String message;
		for (Brick brick : bricks) {
			toDoMotions = this.collision(ball, brick.getPosition(), brick.getWidth(), brick.getHeight());
			if (toDoMotions != null) {
				int brickLife = brick.getLp() - ball.getBallStrength();
				brick.setLp(brickLife);
				if (brickLife == 0) {
					message = brick.specialEffect(data); // Lorsqu'une brique speciale disparait, son effet (bonus ou malus) s'applique
					data.addScore(brick.getScore());
					data.setMessage(message);
					toRemove = brick;
				}
				break;
			}
				
		}
		if (toRemove != null) bricks.remove(toRemove);
		return toDoMotions;
	}
	

	private List <BallService.MOVE> manageEdgesCollision(BallService ball, int width, int heigth) throws FallenBall {
		if (this.collision(ball, new Position(0, heigth - 1), width, 1) != null) throw new FallenBall();
		
		List <BallService.MOVE> collisions = new ArrayList<BallService.MOVE>();
		List <BallService.MOVE> leftCollision = this.collision(ball, new Position(0, 0), 1, heigth);
		List <BallService.MOVE> rightCollision = this.collision(ball, new Position(width - 1, 0), 1, heigth);
		List <BallService.MOVE> topCollision = this.collision(ball, new Position(0, 0), width, 1);
		
		if (leftCollision != null) collisions.addAll(leftCollision);
		if (rightCollision != null) collisions.addAll(rightCollision);
		if (topCollision != null) collisions.addAll(topCollision);
		
		if (collisions.isEmpty()) return null;
		
		return collisions;
	}
	
	private List <BallService.MOVE> managePlayerCollision(BallService ball, Player player) {
		return this.collision(ball, player.getPosition(), player.getWidth(), player.getHeight());
	}
	
	private BallService.MOVE getOppositeDirection(BallService.MOVE motion) {
		switch(motion) {
		case UP:
			return BallService.MOVE.DOWN;
		case DOWN:
			return BallService.MOVE.UP;
		case RIGHT:
			return BallService.MOVE.LEFT;
		case LEFT:
			return BallService.MOVE.RIGHT;
		}
		return null;
	}
	
}
