package bomberman.Game;
import bomberman.Sprite.*;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;
import java.util.ArrayList;
import java.awt.Graphics;

public class Level {
    private BufferedReader br;
    private ArrayList<Sprite> grid;
    private ArrayList<Bomb> bombs;

    public Level(String levelPath) throws IOException {
        fileToLevel(levelPath);
    }

    public void fileToLevel(String levelPath) throws FileNotFoundException, IOException {
        br = new BufferedReader(new FileReader(levelPath));
        grid = new ArrayList<>();
        bombs = new ArrayList<>();
        int block_width = 50;
        int block_height = 50;
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char blockType : line.toCharArray()) {
                if (blockType == '1') {
                    Image image = new ImageIcon("data/images/wall.jpg").getImage();
                    grid.add(new Wall(x * block_width, y * block_height, block_height, block_width, image));
                } else if (blockType == '2') {
                    Image image = new ImageIcon("data/images/box.png").getImage();
                    grid.add(new Box(x * 50, y * 50, block_width, block_height, image));
                }
                x++;
            }
            y++;
        }
    }


    public void draw(Graphics g) {
        for (Sprite sprite : grid) {
            sprite.draw(g);
        }
        for (Bomb bomb : bombs) {
            bomb.draw(g);
        }
    }
}
