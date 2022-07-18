package tools;


public class HardCodedParameters {
	// Motion
	public static final double friction = 0.50;
	public static final int playerStepX = 7;
	public static final int playerStepY = 2;
	public static final int ballSpeed = 6;


	// Sizes
	public static final int defaultWidth = 800, defaultHeight = 600;
	public static final int playerWidth = 130;
	public static final int playerHeight = 10;
	public static final double radius = 7;
	public static final int ballStrength = 1;
	public static final int BasicBrickWidth = 30;
	public static final int BasicBrickHeight = 17;
	public static final int nbLevels = 7;

	// Positions
	public static final Position playerDefaultPosition = new Position((int) ((defaultWidth - playerWidth) / 2),
			defaultHeight - playerHeight - 1);
	public static final Position ballDefaultPosition = new Position((int) (playerDefaultPosition.getX() +  playerWidth / 2),
			(int) (playerDefaultPosition.getY() - radius));

	// Engine
	public static final int enginePaceMillis = 17;

	// Player parameters
	public static final int playerBalls = 3;
	public static final int playerMaxY = defaultHeight - 10;
	public static final int playerMinY = defaultHeight - 100;

}
