package data.bricks;

import tools.HardCodedParameters;
import tools.Position;

public class BasicBrick extends Brick {

	public BasicBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 1, 5);
	}
	
}
