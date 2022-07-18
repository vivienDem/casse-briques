package data.bricks;

import java.io.File;

import interfaces.DataService;
import javafx.scene.media.AudioClip;
import tools.HardCodedParameters;
import tools.Position;
/**
 * Brique qui augmente la taille de la balle 
 * @author Adan Bougherara et Vivien Demeulenaere
 *
 */
public class BonusSizeBallBrick extends Brick {

	public BonusSizeBallBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 2, 5);
	}
	
	@Override
	public String specialEffect(DataService data) {
		assert lp == 0;
		
		double oldRadius = data.getBall().getRadius();
		data.getBall().setRadius(oldRadius*1.30);; // augmentation de 30%
		
		AudioClip sound = new AudioClip(new File("sounds/bonus-brick.waw").toURI().toString());
		sound.play();
		
		return "BONUS : INCREASE OF THE BALL'S SIZE";
	}

}
