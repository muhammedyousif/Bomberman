package bomberman.Game;
import bomberman.Sprite.*;
import bomberman.Sprite.Box;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.Graphics;
public class Level {
    private BufferedReader br;
    public ArrayList<Sprite> grid;
    public ArrayList<Bomb> bombs;
    public ArrayList<Explosion> explosions;
    private ArrayList<ArrayList<Integer>> snap_positions = new ArrayList<>();

    private final int block_width = 60;
    private final int block_height = 60;

    public Level(String levelPath) throws IOException {
        fileToLevel(levelPath);
        getSnapPositions();
        explosions = new ArrayList<Explosion>();
        bombs = new ArrayList<Bomb>();
    }

    private void getSnapPositions(){
        for(int x = 0; x < 15; x++){
            for(int y = 0; y < 13; y++){
                ArrayList<Integer> tmp = new ArrayList<>();
                tmp.add(block_width/2 + (x*block_width));
                tmp.add(block_height/2 + (y*block_height));
                snap_positions.add(tmp);
            }
        }
    }
    private static float distance(float x1, float y1, float x2, float y2) {
        float deltaX = x2 - x1;
        float deltaY = y2 - y1;
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    public Bomb placeBomb(int x, int y){
        int closest_x = 30;
        int closest_y = 30;
        float distance = 500;
        for (ArrayList<Integer> tuple : snap_positions) {
            float currentDistance = distance(tuple.get(0), tuple.get(1), x, y);
            if (currentDistance < distance) {
                distance = currentDistance;
                closest_y = tuple.get(1);
                closest_x = tuple.get(0);
            }
        }
        Image image = new ImageIcon("src/bomberman/Assets/bomb.png").getImage();
        Bomb b = new Bomb(closest_x,closest_y,50,50,image,this);

        for (Bomb bomb : bombs) {
            if (bomb.collides_with_sprite(bomb.getX(),bomb.getY(),bomb.width,bomb.height,b)) {
                return null;
            }
        }
        int index = bombs.size();
        bombs.add(b);
        return bombs.get(index);
    }


    private void fileToLevel(String levelPath) throws FileNotFoundException, IOException {
        br = new BufferedReader(new FileReader(levelPath));
        grid = new ArrayList<>();
        bombs = new ArrayList<>();
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char blockType : line.toCharArray()) {
                if (blockType == '1') {
                    Image image = new ImageIcon("src/bomberman/Assets/wall.png").getImage();
                    grid.add(new Wall(x * block_width, y * block_height, block_width, block_height,image));
                } else if (blockType == '2') {
                    Image image = new ImageIcon("src/bomberman/Assets/box.png").getImage();
                    grid.add(new Box(x * block_width+3, y * block_height+3 , block_width-5, block_height-5, image,this));
                } else if (blockType == '3') {
                    Image image = new ImageIcon("src/bomberman/Assets/monster.png").getImage();
                    Monster monster = new Monster(x * block_width, y * block_height, block_width, block_height, image);
                    monster.setLevel(this);
                    grid.add(monster);

                }
                x++;
            }
            y++;
        }
    }

    public ArrayList<Sprite> getGrid(){
        return this.grid;
    }
    public ArrayList<Monster> getMonsters(){
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Sprite sprite : grid){
            if(sprite instanceof Monster){
                monsters.add((Monster) sprite);
            }
        }
        return monsters;
    }

    public void tickBombs(){
        for(int i = 0; i < bombs.size(); i++){
            bombs.get(i).tick();
        }
        for(int i = 0; i < explosions.size(); i++){
            explosions.get(i).tick();
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i <grid.size(); i++) {
            grid.get(i).draw(g);
        }
        for(int i = 0; i < bombs.size(); i++){
            bombs.get(i).draw(g);
        }
        for(int i = 0; i < explosions.size();i++){
            explosions.get(i).draw(g);
        }
    }
}
