package data.bricks;

import java.io.File;

import interfaces.DataService;
import javafx.scene.media.AudioClip;
import tools.HardCodedParameters;
import tools.Position;

public class BonusSpeedBallBrick extends Brick {

	public BonusSpeedBallBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 1, 5);
	}
	
	@Override
	public String specialEffect(DataService data) {
		assert lp == 0; // pr�condition : la brique va etre detruite
		
		data.getBall().setSpeed(Math.max(data.getBall().getSpeed()*0.8, 3));// Ralentissement de 20% (cap�e a 3)
		
		AudioClip sound = new AudioClip(new File("sounds/bonus-brick.waw").toURI().toString());
		sound.play();
		
		return "BONUS : BALL SPEED DECREASE\n";
	}

}
