package bomberman.Game;
import bomberman.Sprite.*;
import bomberman.Sprite.Box;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

public class Level {
    private BufferedReader br;
    private ArrayList<Sprite> grid;
    private ArrayList<Bomb> bombs;
    private List<Monster> monsters;
    private ArrayList<ArrayList<Integer>> snap_positions = new ArrayList<>();

    private final int block_width = 60;
    private final int block_height = 60;

    public Level(String levelPath) throws IOException {
        fileToLevel(levelPath);
        getSnapPositions();
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
        int closes_x = 30;
        int closes_y = 30;
        float distance = 500;
        for(ArrayList<Integer> touple : snap_positions){
            System.out.println(touple.get(0));
            System.out.println(touple.get(1));
            if(distance > distance(touple.get(0), touple.get(1),x,y)){
                distance = distance(touple.get(0), touple.get(1),x,y);
                closes_y = touple.get(1);
                closes_x = touple.get(0);
                System.out.println(distance);
            }
        }
        Image image = new ImageIcon("src/bomberman/Assets/bomb.png").getImage();
        Bomb b = new Bomb(closes_x,closes_y,50,50,image);
        int index = bombs.size();
        bombs.add(b);
        //timer kiegészítés
        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blowUp(b);
            }
        });
        timer.setRepeats(false);
        timer.start();
        //
        System.out.println(bombs.get(index).x);
        return bombs.get(index);
    }
    //Spagetti kód mint a LOL meg a CS innentől
    private void blowUp(Bomb bomb) {
        bombs.remove(bomb);

        List<PowerUp> powerUpsToAdd = new ArrayList<>();
        List<Monster> monstersToRemove = new ArrayList<>();

        Iterator<Sprite> iterator = grid.iterator();
        while (iterator.hasNext()) {
            Sprite sprite = iterator.next();
            if (sprite instanceof Box && isWithinExplosionRange(bomb, sprite)) {
                iterator.remove();
                if (shouldDropPowerUp()) {
                    Image image = new ImageIcon("src/bomberman/Assets/powerup.png").getImage();
                    PowerUp powerUp = new PowerUp(sprite.getX(), sprite.getY(), 50, 50, image);
                    powerUpsToAdd.add(powerUp);
                }

            }else if (sprite instanceof Monster && isWithinExplosionRange(bomb, sprite)) {
                iterator.remove();
                monstersToRemove.add((Monster) sprite);
            }
        }
        monsters.removeAll(monstersToRemove);
        grid.addAll(powerUpsToAdd);
    }

    private boolean shouldDropPowerUp() {
        int randomNumber = (int) (Math.random() * 100);
        return randomNumber < 50;
    }
    //Felrobbantja jelenleg a keresztbe lévő dolgokat is(nem működik helyesen)
    private boolean isWithinExplosionRange(Bomb bomb, Sprite sprite) {

        int bombX = bomb.x;
        int bombY = bomb.y;
        int spriteX = sprite.x;
        int spriteY = sprite.y;

        int distanceX = Math.abs(bombX - spriteX);
        int distanceY = Math.abs(bombY - spriteY);


        int combinedWidth = bomb.width + sprite.width;
        int combinedHeight = bomb.height + sprite.height;

        System.out.println("Bomb Position: (" + bombX + ", " + bombY + ")");
        System.out.println("Sprite Position: (" + spriteX + ", " + spriteY + ")");
        System.out.println("Horizontally adjacent: " + combinedWidth);
        System.out.println("Vertically adjacent: " + combinedHeight);

        return distanceX < combinedWidth && distanceY < combinedHeight;
    }

    public List<Monster> getMonsters() {
        return this.monsters;
    }
    //idáig
    private void fileToLevel(String levelPath) throws FileNotFoundException, IOException {
        br = new BufferedReader(new FileReader(levelPath));
        grid = new ArrayList<>();
        bombs = new ArrayList<>();
        monsters = new ArrayList<>();
        int y = 0;
        String line;
        while ((line = br.readLine()) != null) {
            int x = 0;
            for (char blockType : line.toCharArray()) {
                if (blockType == '1') {
                    Image image = new ImageIcon("src/bomberman/Assets/wall.png").getImage();
                    grid.add(new Wall(x * block_width, y * block_height, block_width, block_height, image));
                } else if (blockType == '2') {
                    Image image = new ImageIcon("src/bomberman/Assets/box.png").getImage();
                    grid.add(new Box(x * block_width, y * block_height, block_width, block_height, image));
                } else if (blockType == '3') {
                    Image image = new ImageIcon("src/bomberman/Assets/monster.png").getImage();
                    Monster monster = new Monster(x * block_width, y * block_height, block_width, block_height, image);
                    monsters.add(monster);
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

    public void draw(Graphics g) {
        for (Sprite sprite : grid) {
            sprite.draw(g);
        }
        for (Bomb bomb : bombs) {
            bomb.draw(g);
        }
    }
}
