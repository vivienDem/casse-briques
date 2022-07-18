package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import data.bricks.BasicBrick;
import data.bricks.BonusAddBallBrick;
import data.bricks.BonusLifeBrick;
import data.bricks.BonusSizeBallBrick;
import data.bricks.BonusSizePlayerBrick;
import data.bricks.BonusSpeedBallBrick;
import data.bricks.Brick;
import data.bricks.MalusSpeedBallBrick;
import data.bricks.SolidBrick;

public class BrickReader {
	public static List<Brick> read(String filename) {
		List<Brick> bricks = new ArrayList<Brick>();
		Brick brick;
		int x = 0, y = 0;
		try {
			FileReader f = new FileReader(filename);
			BufferedReader r = new BufferedReader(f);
			String line = r.readLine();
			int yMax;

			while (line != null) {
				yMax = 0;
				x = 0;
				String[] split = line.split(" ");
				for (int i = 0; i < split.length; i++) {
					switch (split[i]) {
					case "-1":
						x += HardCodedParameters.BasicBrickWidth;
						brick = null;
						break;
					case "0": // Cas des briques basiques
						brick = new BasicBrick(new Position(x, y));
						bricks.add(brick);
						break;
					case "1": // Cas des briques solides
						brick = new SolidBrick(new Position(x, y));
						bricks.add(brick);
						break;
					case "2": // Cas des briques augmentant la taille du joueur (Bonus)
						brick = new BonusSizePlayerBrick(new Position(x, y));
						bricks.add(brick);
						break;
					case "3": // Cas des briques augmentant la taille de la balle (Bonus)
						brick = new BonusSizeBallBrick(new Position(x, y));
						bricks.add(brick);
						break;
					case "4": // Cas des briques ralentissant la balle (Bonus)
						brick = new BonusSpeedBallBrick(new Position(x, y));
						bricks.add(brick);
						break;
					case "5": // Cas des briques accelerant la balle (Malus)
						brick = new MalusSpeedBallBrick(new Position(x, y));
						bricks.add(brick);
						break;
					case "6":
						brick = new BonusLifeBrick(new Position(x, y));
						bricks.add(brick);
						break;
					case "7":
						brick = new BonusAddBallBrick(new Position(x, y));
						bricks.add(brick);
						break;
					default:
						brick = null;
						break;
					}
					if (brick != null) {
						x += brick.getWidth();
						if (yMax < brick.getHeight()) {
							yMax = brick.getHeight();
						}
					}
				}
				y += yMax + HardCodedParameters.BasicBrickHeight;
				line = r.readLine();
			}
			r.close();
		} catch (Exception e) {
			throw new Error(e);
		}
		return bricks;
	}
}
