package data.bricks;

import java.io.File;

import interfaces.DataService;
import javafx.scene.media.AudioClip;
import tools.HardCodedParameters;
import tools.Position;

public class BonusLifeBrick extends Brick {

	public BonusLifeBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 1, 5);
	}

	@Override
	public String specialEffect(DataService data) {
		assert lp == 0; // pr�condition : la brique va etre detruite
		
		data.getPlayer().increaseBalls(1);
		
		AudioClip sound = new AudioClip(new File("sounds/bonus-brick.waw").toURI().toString());
		sound.play();
		
		return "BONUS :\n+1 LIFE";
	}
}
