package data.bricks;

import tools.HardCodedParameters;
import tools.Position;

public class SolidBrick extends Brick {

	public SolidBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 3, 10);
	}

}
