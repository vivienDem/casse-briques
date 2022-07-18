package data.bricks;

import java.io.File;

import interfaces.DataService;
import javafx.scene.media.AudioClip;
import tools.HardCodedParameters;
import tools.Position;
/**
 * Brique qui augmente la taille du joueur
 * @author Adan Bougherara et Vivien Demeulenaere
 *
 */
public class BonusSizePlayerBrick extends Brick {

	public BonusSizePlayerBrick(Position position) {
		super(position, HardCodedParameters.BasicBrickHeight, HardCodedParameters.BasicBrickWidth, 2, 5);
	}
	
	@Override
	public String specialEffect(DataService data) {
		assert lp == 0; // pr�condition : la brique va etre detruite
		
		int oldWidth = data.getPlayer().getWidth();
		data.getPlayer().setWidth((int) (oldWidth*1.3)); // augmentation de 30%
		
		//Recalcul de la nouvelle position
		Position oldPosition = data.getPlayer().getPosition();
		int newX = (int) (oldPosition.getX() - oldWidth*1.15);
		Position newPosition = new Position(Math.max(newX, 0), oldPosition.getY());
		data.setPlayerPosition(newPosition);
		
		AudioClip sound = new AudioClip(new File("sounds/bonus-brick.waw").toURI().toString());
		sound.play();
		
		return "BONUS : INCREASE OF THE PLAYER'S SIZE";
	}

}
