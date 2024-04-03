package Game;

import java.awt.image.BufferedImage;

public class Level {
    private BufferedReader br = new BufferedReader(new FileReader(levelPath));
    private ArrayList<Sprite> grid;
    private ArrayList<Bomb> bombs;
}
