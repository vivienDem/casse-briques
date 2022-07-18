package data.bricks;

import java.io.File;

import interfaces.DataService;
import javafx.scene.media.AudioClip;
import tools.HardCodedParameters;
import tools.Position;

public class MalusSpeedBallBrick extends Brick {

	public MalusSpeedBallBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 1, 5);
	}
	
	@Override
	public String specialEffect(DataService data) {
		assert lp == 0; // pr�condition : la brique va etre detruite
		
		data.getBall().setSpeed(Math.min(data.getBall().getSpeed()*1.20, 10));// Vitesse augmentee de 20%	(cap�e � 10)
		
		AudioClip sound = new AudioClip(new File("sounds/malus-brick.waw").toURI().toString());
		sound.play();
		
		return "MALUS : BALL SPEED INCREASE\n";
	}

}
